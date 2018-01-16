package de.jambonna.lolpapers.core.model.text;

import de.jambonna.lolpapers.core.model.TemplatePlaceholder;
import de.jambonna.lolpapers.core.model.lang.Language;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Content processing code (input articles analysis, output article generation)
 * without any language specific code.
 */
public class ContentProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ContentProcessor.class);

    
    /**
     * Inline elements from the "relaxed" white list that won't break a sentence
     */
    protected static final String[] NON_BREAKING_INLINE_ELTS = { 
        "a", "b", "cite", "code", "em", "i", "q", "small", "span", "strike", "strong", "sub", "sup",  "u" 
    };
    
    protected static final String WRAPPED_WORD_TAG = "a";
    protected static final String WRAPPED_WORD_ID_ATTR = "wid";
    protected static final String WRAPPED_WORD_SID_ATTR = "wsid";
    protected static final String BLOCK_ELT_ID_ATTR = "blkid";
    

    
    private final Language language;
    private StringBuilder extractDirectTextPartsSb;

    
    public ContentProcessor(Language language) {
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }
        
    
    public Element getArticleMainContentContainer(Document inputDoc) {
        // Get a pre-cleaned version of the doc
        Document doc = getCleanedArticleDoc(inputDoc);
                
        // Prepare a set of elements that may be the content container
        // Prepare the sentence data for each block level element, to be able
        // to use it through the whole process
        Set<Element> candidateContainers = new HashSet<>();
        Map<Element, TextContainer> textContainers = new HashMap<>();
        Elements blockElts = getBlockSubElementsThatMayContainText(doc);
        for (Element e : blockElts) {
            TextContainer tc = getTextContainer(e);
            if (tc.getSentences().size() > 0) {
                textContainers.put(e, tc);
                // Add or re-add the parent
                candidateContainers.add(e.parent());
                
                // Also, numberize the block elements
                e.dataset().put(BLOCK_ELT_ID_ATTR, 
                    String.valueOf(textContainers.size()));
            }
        }
        
        // Find the best container
        Element bestContainer = findBestArticleContentContainer(
                candidateContainers, textContainers);
        if (bestContainer != null) {
            // Clean sub elements that doesn't constitue article text
            cleanArticleContentContainer(bestContainer, textContainers);
        } else {
            // Returns an empty element
            bestContainer = doc.createElement("div");
        }
        return bestContainer;
    }
    
    protected Document getCleanedArticleDoc(Document doc) {
        Whitelist wl = Whitelist.relaxed();
        wl.addTags("blockquote");
        Cleaner c = new Cleaner(wl);
        return c.clean(doc);
    }
    
    public Elements getBlockElementsThatMayContainText(Element cleanedElt) {
        String blockEltsSelector = "div,p,li,td,dd,blockquote,h1,h2,h3,h4,h5,h6";
        return cleanedElt.select(blockEltsSelector);
    }
    
    public Elements getBlockSubElementsThatMayContainText(Element cleanedElt) {
        Elements elts = new Elements();
        for (Element subE : cleanedElt.children()) {
            elts.addAll(getBlockElementsThatMayContainText(subE));
        }
        return elts;
    }
    
    protected Element findBestArticleContentContainer(
            Set<Element> candidateContainers, 
            Map<Element, TextContainer> textContainers) {
        
        Element bestContainer = null;
        int bestContainerTocalWc = 0;
        for (Element container : candidateContainers) {
            int totalWc = 0;
            for (Element tce : container.children()) {
                TextContainer tc = textContainers.get(tce);
                if (tc != null) {
                    for (Sentence st : tc.getSentences()) {
                        if (mayBeTextParagraphSentence(st)) {
                            totalWc += st.getWordCount();
                        }
                    }
                }
            }
            
            if (totalWc > bestContainerTocalWc) {
                bestContainer = container;
                bestContainerTocalWc = totalWc;
            }
        }
        
        return bestContainer;
    }
    
    protected boolean isWellWrittenSentence(Sentence st) {
        return st.hasInitialUC() && st.isWithFinalPunct();
    }
    
    protected boolean mayBeTextParagraphSentence(Sentence st) {
        return isWellWrittenSentence(st) && st.getLcWordCount() >= 5;
    }

    protected void cleanArticleContentContainer(Element container, 
            Map<Element, TextContainer> textContainers) {
        for (Element tce : container.children()) {
            // Remove children of this text container recursively if doesn't 
            // contain article text
            // Keep well written sentences even if short text
            cleanArticleElementRecurs(tce, textContainers);
        }
        
        postArticleContentCleaning(container);
    }
    
    protected void postArticleContentCleaning(Element container) {
        for (Element a : container.select("a")) {
            Element replacement = new Element("span");
            replacement.addClass("orig-link").html(a.html());
            a.replaceWith(replacement);
        }
        container.select("img").remove();
    }
    
    protected void cleanArticleElementRecurs(Element container, 
            Map<Element, TextContainer> textContainers) {
//        System.out.println("ok " + container.cssSelector());
        Elements blockSubElts = getBlockSubElementsThatMayContainText(container);
        for (Element subE : blockSubElts) {
            cleanArticleElementRecurs(subE, textContainers);
        }

        // Now sub block elements may have been removed, 
        // remove this container if doesn't contain article text
        blockSubElts = getBlockSubElementsThatMayContainText(container);
//        System.out.println("content " + container.html());

        if (blockSubElts.isEmpty()) {
//            System.out.println("empty");

            TextContainer tc = textContainers.get(container);
            boolean toRemove = true;
            if (tc != null) {
                for (Sentence sd : tc.getSentences()) {
                    if (isWellWrittenSentence(sd)) {
                        toRemove = false;
                    }
                }
            }
            if (toRemove) {
//                System.out.println("removed");

                if (container.parentNode() != null) {
                    container.remove();
                }
                textContainers.remove(container);
            }
        }
    }

    public TextContainer getTextContainer(Element container) {
        TextContainer tc = new TextContainer(container);
        
        List<String> parts = extractDirectTextParts(container);
        for (String part : parts) {
            List<Sentence> ls = getLanguage().extractSentences(part);
            tc.getSentences().addAll(ls);
        }
        return tc;
    }

    
    public List<String> extractDirectTextParts(Element container) {
        List<String> parts = new ArrayList<>();
        extractDirectTextPartsSb = new StringBuilder();
        extractDirectTextPartsIn(container, parts);
        if (extractDirectTextPartsSb.length() > 0) {
            parts.add(extractDirectTextPartsSb.toString());
        }
        return parts;
    }
    
    protected void extractDirectTextPartsIn(Element e, List<String> parts) {
        
        for (Node child : e.childNodes()) {
            if (child instanceof TextNode) {
                // Note : Jsoup normalizes spaces sequence as 1 space
                extractDirectTextPartsSb.append(((TextNode)child).text());
            } else if (child instanceof Element) {
                Element childEl = (Element)child;
                String tag = childEl.tagName().toLowerCase();

                if (Arrays.binarySearch(NON_BREAKING_INLINE_ELTS, tag) >= 0) {
                    extractDirectTextPartsIn(childEl, parts);
                } else {
                    if (extractDirectTextPartsSb.length() > 0) {
                        parts.add(extractDirectTextPartsSb.toString());
                        extractDirectTextPartsSb = new StringBuilder();
                    }
                }
            }
        }
    }
    

    
    
    

    public int getArticleTotalWc(Element articleContainer) {
        String text = articleContainer.text();
        List<TextRange> words = getLanguage().extractWords(text);
        return words.size();
    }
    
    public String getIntroText(Element contentContainer, int minWc) {
        // This is an heuristic that may be sufficient in most cases.
        String text = contentContainer.text();
        List<Sentence> sentences = getLanguage().extractSentences(text);
        
        int wc = 0;
        StringBuilder sb = new StringBuilder();
        for (Sentence s : sentences) {
            sb.append(s.getSentenceSeqence());
            wc += s.getWordCount();
            if (wc >= minWc) {
                break;
            }
        }
        return sb.toString();
    }
    

    
    public Element wrapWords(Element container, WrapWordsContext ctx) {
        ctx.setText(container.text());
        ctx.incCurSentenceId();
        
        logger.debug("Wrapping words for:\n{}\nText :\"{}\"", container.html(), ctx.getText());
        
        Element output = Document.createShell("").body();
        wrapWordsIn(output, container, ctx);
        return output;
    }
    
    protected void wrapWordsIn(Element output, Element input, WrapWordsContext ctx) {
        for (Node child : input.childNodes()) {
            if (child instanceof TextNode) {
                // Note : text() normalizes space sequences but keeps a leading/trailing space if any
                CharSequence nodeText = ((TextNode)child).text();
                int nodeTextLg = nodeText.length();
                
                logger.debug("TextNode : \"{}\"", nodeText);
                
                // Special processing of leading space of this node
                // Merge it with previous ones as it is done with a global Element.text() call
                if (nodeTextLg > 0 && nodeText.charAt(0) == ' ') {
                    // If not merged with a previous space, adds it to the output
                    if (ctx.consumeSpace(true)) {
                        output.append(" ");
                    }
                    nodeText = nodeText.subSequence(1, nodeText.length());
                    nodeTextLg = nodeText.length();
                }
                int nodeTextStartPos = ctx.getCurTextPos();
                int nodeTextEndPos = nodeTextStartPos + nodeTextLg;
                
                logger.debug("Final TextNode : \"{}\" ({}), pos {}-{}", 
                        nodeText, nodeTextLg, nodeTextStartPos, nodeTextEndPos);
                ctx.logStatus();
                
                int nodeTextPos = 0;
                TextRange word = ctx.getCurWord();
                while (word != null && word.getStart() < nodeTextEndPos) {
                    if (word.getStart() >= nodeTextStartPos) {
                        logger.debug("Inserting cur word...");
                        
                        // Add the text before the word if any
                        CharSequence beforeText = nodeText.subSequence(
                                nodeTextPos, word.getStart() - nodeTextStartPos);
                        ctx.consumeText(beforeText);
                        output.appendText(beforeText.toString());

                        nodeTextPos += beforeText.length();

                        // Create the word element and add it
                        Element wordEl = output.ownerDocument().createElement(WRAPPED_WORD_TAG);
                        wordEl.text(word.getText());
                        if (ctx.isWithIds()) {
                            wordEl.dataset().put(WRAPPED_WORD_ID_ATTR, 
                                    String.valueOf(ctx.getCurWordId()));
                            wordEl.dataset().put(WRAPPED_WORD_SID_ATTR, 
                                    String.valueOf(ctx.getCurSentenceId()));
                        }

                        CharSequence wordNodeText = nodeText.subSequence(nodeTextPos, 
                                Math.min(nodeTextPos + word.length(), nodeTextLg));
                        ctx.consumeText(wordNodeText);
                        output.appendChild(wordEl);
                        
                        nodeTextPos += wordNodeText.length();


                        ctx.incCurWordId();
                        if (getLanguage().getSentenceEndMarkFollowingWord(word) != null) {
                            ctx.incCurSentenceId();
                        }                        
                    } else {
                        logger.debug("Skipping cur word part...");

                        // This word should have been inserted already, 
                        // but consume the part of this word present in this text node
                        int partLg = word.getEnd() - ctx.getCurTextPos();
                        CharSequence wordNodeText = nodeText.subSequence(nodeTextPos, 
                                Math.min(nodeTextPos + partLg, nodeTextLg));
                        ctx.consumeText(wordNodeText);

                        nodeTextPos += wordNodeText.length();
                    }

                    if (word.getEnd() <= nodeTextEndPos) {
                        logger.debug("Moving to next word");
                        word = ctx.nextWord();
                    } else {
                        logger.debug("Exiting word check loop");
                        word = null;
                    }
                    
                    logger.debug("TextNode pos : {}", nodeTextPos);
                }
                
                // Add the ending text if any
                if (nodeTextPos < nodeTextLg) {
                    CharSequence endingText = nodeText.subSequence(nodeTextPos, nodeTextLg);
                    ctx.consumeText(endingText);
                    output.appendText(endingText.toString());
                }
                
            } else if (child instanceof Element) {
                Element childEl = (Element)child;
                String tagName = childEl.tagName().toLowerCase();
                
                boolean isBreakingTag = 
                        Arrays.binarySearch(NON_BREAKING_INLINE_ELTS, tagName) < 0;

                Element outChildEl = output.ownerDocument().createElement(tagName);
                outChildEl.attributes().addAll(childEl.attributes());
                output.appendChild(outChildEl);
                
                wrapWordsIn(outChildEl, childEl, ctx);
                
                if (isBreakingTag) {
                    ctx.incCurSentenceId();
                    // Element.html() adds a space for each block level element change
                    // .. except if followed by text, so this is not an error if not followed by space
                    ctx.consumeSpace(false);
                }
            }
        }
    }

    public Element getWordElement(Element wrappedContent, int wordId) {
        return wrappedContent.select(
                    WRAPPED_WORD_TAG + "[data-" + WRAPPED_WORD_ID_ATTR + "=" + wordId + "]")
                .first();
    }
    
    public Element getWordElement(List<Element> wrappedContentList, int wordId) {
        for (Element container : wrappedContentList) {
            Element e = getWordElement(container, wordId);
            if (e != null) {
                return e;
            }
        }
        return null;
    }
    
    public int getWrappedWordCount(List<Element> wrappedContentList) {
        int count = 0;
        for (Element container : wrappedContentList) {
            Elements words = container.select(
                    WRAPPED_WORD_TAG + "[data-" + WRAPPED_WORD_ID_ATTR + "]");
            count += words.size();
        }
        return count;
    }
    
    public Integer getWordSentenceId(List<Element> wrappedContentList, int wordId) {
        Element word = getWordElement(wrappedContentList, wordId);
        if (word != null) {
            String attr = word.dataset().get(WRAPPED_WORD_SID_ATTR);
            if (attr != null) {
                return Integer.valueOf(attr);
            }
        }
        return null;
    }
    
    public String getWordRangeText(List<Element> wrappedContentList, int fromId, int nbWords) {
        Element e = getWordElement(wrappedContentList, fromId);
        if (e != null) {
            StringBuilder sb = new StringBuilder();
            if (nbWords > 0) {
                browseElementsForGetWordRangeText(e, e, sb, fromId + nbWords - 1);
            }
            return sb.toString();
        }
        return null;
    }
    
    protected void browseElementsForGetWordRangeText(Node node, 
            Element baseContainer, StringBuilder text, int untilId) {
        boolean gotoNext = true;
        
        if (node instanceof Element) {
            Element elt = (Element)node;
            String tag = elt.tagName().toLowerCase();
            
            logger.debug("Element {}", tag);
            
            Integer wordId = null; 
            if (tag.equals(WRAPPED_WORD_TAG)
                    && elt.dataset().containsKey(WRAPPED_WORD_ID_ATTR)) {
                wordId = Integer.valueOf(elt.dataset().get(WRAPPED_WORD_ID_ATTR));
            }
            
            if (wordId != null) {
                logger.debug("Adding word text {}", elt.text());
                text.append(elt.text());
                if (wordId >= untilId) {
                    logger.debug("Last word found, not going further");
                    gotoNext = false;
                }
            } else {
                if (node.childNodes().size() > 0) {
                    logger.debug("Moving to first child");
                    gotoNext = false;
                    browseElementsForGetWordRangeText(node.childNodes().get(0),
                            baseContainer, text, untilId);
                }
            }

        } else if (node instanceof TextNode) {
            TextNode tn = (TextNode)node;
            logger.debug("Text node \"{}\"", tn.getWholeText());
            text.append(tn.getWholeText());
        }
        
        if (gotoNext) {
            Node next = node.nextSibling();
            while (next == null) {
                logger.debug("No next sibling, moving up");
                node = node.parentNode();
                if (node == null || node == baseContainer) {
                    break;
                }
                next = node.nextSibling();
            }
            if (next != null) {
                browseElementsForGetWordRangeText(next, baseContainer, text, untilId);
            }
        }
    }

    public Element removeBlockElements(Element container, int[] removedBlockIds) {
        Element result = container.clone();
        for (int blockId : removedBlockIds) {
            result.select("[data-" + BLOCK_ELT_ID_ATTR + "=" + blockId + "]").remove();
        }
        return result;
    }
    
    public Element getFinalArticleContent(Element wrappedContent, List<TemplatePlaceholder> placeholders) {
        FinalArticleCreationContext ctx = new FinalArticleCreationContext(placeholders);
        Element output = Document.createShell("").body();
        unwrapWordsIn(output, wrappedContent, ctx);
        return output;
    }

    protected void unwrapWordsIn(Element output, Element input, FinalArticleCreationContext ctx) {
        for (Node child : input.childNodes()) {
            if (child instanceof TextNode) {
                String text = ((TextNode)child).text();
                
                logger.debug("Text: \"{}\"", text);
                if (!ctx.isContentIgnored()) {
                    unwrapWordsAddText(output, text, ctx);
                }
                
            } else if (child instanceof Element) {
                
                Element childEl = (Element)child;
                String tag = childEl.tagName().toLowerCase();
                logger.debug("Tag {}", tag);

                boolean isWordEl = tag.equals(WRAPPED_WORD_TAG)
                        && childEl.dataset().containsKey(WRAPPED_WORD_ID_ATTR);
                Integer curWordId = null; 
                if (isWordEl) {
                    curWordId = Integer.valueOf(childEl.dataset().get(WRAPPED_WORD_ID_ATTR));
                    logger.debug("Word {}", curWordId);

                                        
                    // Start replacing ?
                    TemplatePlaceholder tp = ctx.getPlaceholder(curWordId);
                    if (tp != null) {
                        ctx.setRemoveUntilWord(curWordId + tp.getNbWords() - 1);
                        logger.debug("Replacement found, removing until {}", ctx.getRemoveUntilWord());
                        unwrapWordsDoReplacement(output, tp, ctx);
                    }
                }
                
                if (isWordEl) {
                    // Add the word content directly without its tag
                    unwrapWordsIn(output, childEl, ctx);
                } else {
                    if (Arrays.binarySearch(NON_BREAKING_INLINE_ELTS, tag) < 0) {
                        // Breaking tag, resets last text
                        ctx.getLastTextNodes().clear();
                        logger.debug("Last text nodes reset");

                    }

                    // Recreate the element and go into it
                    Element outChildEl = output.ownerDocument().createElement(tag);
                    outChildEl.attributes().addAll(childEl.attributes());
                    outChildEl.dataset().clear();
                    output.appendChild(outChildEl);

                    unwrapWordsIn(outChildEl, childEl, ctx);
                }
                
                // Stop replacing content from now
                if (ctx.getRemoveUntilWord() != null && curWordId != null && ctx.getRemoveUntilWord() <= curWordId) {
                    ctx.setRemoveUntilWord(null);
                    logger.debug("End of word removal");
                }
            }
        }

    }
    
    protected void unwrapWordsAddText(Element output, String text,
            FinalArticleCreationContext ctx) {
        if (text.length() > 0) {
            logger.debug("Adding text: \"{}\"", text);
            TextNode newNode = new TextNode(text, "");
            output.appendChild(newNode);
            ctx.getLastTextNodes().add(newNode);
            ctx.logLastTextNodes();
        }
    }
    
    protected String getUnwrapWordsLastText(FinalArticleCreationContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (TextNode tn : ctx.getLastTextNodes()) {
            sb.append(tn.text());
        }
        return sb.toString();
    }
    
    protected void unwrapWordsDoReplacement(Element output, TemplatePlaceholder tp, 
            FinalArticleCreationContext ctx) {
        String lastText = getUnwrapWordsLastText(ctx);
        List<TextRange> lastWords = Collections.emptyList();
        List<Sentence> sentences = getLanguage().extractSentences(lastText);
        if (sentences.size() > 0) {
            Sentence lastSentence = sentences.get(sentences.size() - 1);
            if (!lastSentence.isWithFinalPunct()) {
                lastWords = lastSentence.getWordList();
            }
        }
        
        TemplatePlaceholder actualTp = ctx.getPlaceholderFromRef(tp.getUsePlaceholder());
        if (actualTp == null) {
            actualTp = tp;
        }
        
        logger.debug("Doing replacement. Last words: {}, replacement: \"{}\"", 
                lastWords, actualTp.getReplacementSD().getText());
        
        ReplaceContext rctx = new ReplaceContext(actualTp.getReplacementSD(), lastWords);
        getLanguage().alterReplacementText(rctx);
        replaceUnwrapWordsLastText(ctx, rctx);
        unwrapWordsAddText(output, rctx.getFinalReplacementText(), ctx);
    }
    
    protected void replaceUnwrapWordsLastText(FinalArticleCreationContext ctx,
            ReplaceContext rctx) {
        String replacement = rctx.getNewPrecedingText();
        if (replacement == null) {
            return;
        }
        TextRange firstWordToReplace = rctx.getPrecedingWordToReplaceFrom();
        CharSequence toReplace = firstWordToReplace.getTextFrom();
        logger.debug("Replacing last text: \"{}\" => \"{}\"", toReplace, replacement);
        
        int toReplaceIdx = toReplace.length() - 1;
        List<TextNode> tnList = ctx.getLastTextNodes();
        for (ListIterator<TextNode> it = tnList.listIterator(tnList.size()); 
                it.hasPrevious() && toReplaceIdx >= 0; ) {
            TextNode tn = it.previous();
            String text = tn.text();
            logger.debug("Last tn: \"{}\"", text);
            int tnTextIdx = text.length() - 1;
            while (tnTextIdx >= 0  && toReplaceIdx >= 0) {
                // Should not happen : text in text nodes is to be the same than toReplace
                if (text.charAt(tnTextIdx) != toReplace.charAt(toReplaceIdx)) {
                    throw new IllegalStateException(
                            "Unable to replace \"" + toReplace + "\" by \"" + replacement + "\" in text nodes");
                }
                tnTextIdx--;
                toReplaceIdx--;
            }
            String newText = "";
            if (tnTextIdx > 0) {
                newText = text.substring(0, tnTextIdx);
            }
            
            // Add replacement in last text node where occurred deletion
            if (toReplaceIdx < 0) {
                newText = newText + replacement;
            }
            tn.text(newText);
            logger.debug("Replaced by: \"{}\"", newText);
        }
        
        if (toReplaceIdx > 0) {
            // Should not happen : text in text nodes is to be the same than toReplace
            throw new IllegalStateException(
                    "Unable to fully replace \"" + toReplace + "\" by \"" + replacement + "\" in text nodes");
        }
    }

    protected static class TextContainer {

        private final Element element;
        private final List<Sentence> sentences;

        public TextContainer(Element e) {
            element = e;
            sentences = new ArrayList<>();
        }
        
        public Element getElement() {
            return element;
        }
        
        public List<Sentence> getSentences() {
            return sentences;
        }
        
        @Override
        public String toString() {
            StringBuilder output = new StringBuilder();
            for (Sentence st : sentences) {
                if (output.length() > 0) {
                    output.append(" - ");
                }
                output.append(st);
            }
            return output.toString();
        }
    }
    
    public static class WrapWordsContext {
        private final ContentProcessor processor;
        private int curWordId;
        private int curSentenceId;
        private String text;
        private int curTextPos;
        private List<TextRange> words;
        private int curWord;
        private boolean withIds;
        
        public WrapWordsContext(ContentProcessor processor) {
            this.processor = processor;
            curWordId = 1;
            curSentenceId = 0;
            withIds = true;
        }

        public ContentProcessor getProcessor() {
            return processor;
        }

        public boolean isWithIds() {
            return withIds;
        }

        public void setWithIds(boolean withIds) {
            this.withIds = withIds;
        }
        
        public int getCurWordId() {
            return curWordId;
        }
        
        public void incCurWordId() {
            curWordId++;
        }

        public int getCurSentenceId() {
            return curSentenceId;
        }
        
        public void incCurSentenceId() {
            curSentenceId++;
        }
        
        public void setText(String text) {
            this.text = text;
            curTextPos = 0;
            words = processor.getLanguage().extractWords(text);
            curWord = 0;
        }

        public String getText() {
            return text;
        }
        
        public int getCurTextPos() {
            return curTextPos;
        }
                
        public boolean consumeSpace(boolean checkSpace) {
            boolean consumed = false;
            logger.debug("Consuming space");
            if (curTextPos < text.length()) {
                if (text.charAt(curTextPos) == ' ') {
                    curTextPos++;
                    consumed = true;
                } else if (checkSpace && curTextPos > 0 && text.charAt(curTextPos - 1) != ' ') {
                    throw new IllegalStateException("Invalid space consumption");
                }
            }
            logStatus();
            return consumed;
        }
        
        
        public void consumeText(CharSequence t) {
            logger.debug("Consuming text \"{}\"", t);
            if (t != null && t.length() > 0) {
                
                // Ignore trailing space if just beyond end of text
                if (t.charAt(t.length() - 1) == ' '
                        && curTextPos + t.length() == text.length() + 1) {
                    t = t.subSequence(0, t.length() - 1);
                }
                
                if (t.length() > 0) {
                    // Check the text is the one at current position
                    if (curTextPos >= text.length() || curTextPos + t.length() > text.length() 
                            || !t.equals(text.subSequence(curTextPos, curTextPos + t.length()))) {
                        throw new IllegalArgumentException(
                                "Invalid text to consume : \"" + t + "\"");
                    }
                    curTextPos += t.length();
                }
            }
            logStatus();
        }
        
        public TextRange getCurWord() {
            if (curWord < words.size()) {
                return words.get(curWord);
            }
            return null;
        }
        
        public TextRange nextWord() {
            curWord++;
            return getCurWord();
        }
        
        public void logStatus() {
            TextRange cw = getCurWord();
            logger.debug("Position {} ({}), word {} (\"{}\" {}-{})",
                curTextPos, curTextPos < text.length() ? text.charAt(curTextPos) : "",
                curWord, cw, cw != null ? cw.getStart() : "", cw != null ? cw.getEnd() : "");
        }
    }
    
    public static class FinalArticleCreationContext {
        private final Map<Integer, TemplatePlaceholder> placeholders;
        private Integer removeUntilWord;
        private final List<TextNode> lastTextNodes;

        public FinalArticleCreationContext(List<TemplatePlaceholder> placeholders) {
            this.placeholders = new HashMap<>(placeholders.size());
            for (TemplatePlaceholder p : placeholders) {
                this.placeholders.put(p.getFromWord(), p);
            }
            lastTextNodes = new ArrayList<>();
        }

        public TemplatePlaceholder getPlaceholder(int fromWord) {
            return placeholders.get(fromWord);
        }
        
        public TemplatePlaceholder getPlaceholderFromRef(String reference) {
            if (reference == null) {
                return null;
            }
            for (TemplatePlaceholder tp : placeholders.values()) {
                if (reference.equals(tp.getReference())) {
                    return tp;
                }
            }
            return null;
        }
        
        public Integer getRemoveUntilWord() {
            return removeUntilWord;
        }

        public void setRemoveUntilWord(Integer removeUntilWord) {
            this.removeUntilWord = removeUntilWord;
        }
        
        public boolean isContentIgnored() {
            return removeUntilWord != null;
        }
        
        public List<TextNode> getLastTextNodes() {
            return lastTextNodes;
        }
        
        public void logLastTextNodes() {
            StringBuilder sb = new StringBuilder();
            for (TextNode tn : lastTextNodes) {
                sb.append(" \"");
                sb.append(tn.text());
                sb.append("\"");
            }
            logger.debug("Last text nodes:{}", sb);
        }
    }

}
