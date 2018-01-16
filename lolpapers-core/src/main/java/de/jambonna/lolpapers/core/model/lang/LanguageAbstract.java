package de.jambonna.lolpapers.core.model.lang;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.jambonna.lolpapers.core.model.text.ContentProcessor;
import de.jambonna.lolpapers.core.model.text.ReplaceContext;
import de.jambonna.lolpapers.core.model.text.Sentence;
import de.jambonna.lolpapers.core.model.text.Text;
import de.jambonna.lolpapers.core.model.text.TextRange;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Language class that provide default implementations 
 * (except SyntagmeType creations) and other tools for most languages
 */
public abstract class LanguageAbstract implements Language {
    
    private final Locale locale;
    private Map<String, SyntagmeType> syntagmeTypes;
//    private transient SyntagmeDefinitionCondition singleWordSDefCondition;
//    private transient SyntagmeDefinitionCondition multipleWordsSDefCondition;
    private transient ContentProcessor contentProcessor;
    private transient Pattern extractWordsPattern;
    private transient Pattern sentenceEndMarkFollowingWordPattern;
    private transient Pattern hasTextPattern;

    
    public LanguageAbstract(Locale locale) {
        this.locale = locale;
    }
    
    @Override
    public Locale getLocale() {
        return locale;
    }
    
    @Override
    public Map<String, SyntagmeType> getSyntagmeTypes() {
        if (syntagmeTypes == null) {
            List<SyntagmeType> stypeList = createSyntagmeTypes();
            Map<String, SyntagmeType> stypes = new LinkedHashMap<>(stypeList.size());
            for (SyntagmeType st: stypeList) {
                stypes.put(st.getCode(), st);
            }
            syntagmeTypes = Collections.unmodifiableMap(stypes);
        }
        return syntagmeTypes;
    }
    
    protected abstract List<SyntagmeType> createSyntagmeTypes();
    
    public void addCommonDefAttributes(SyntagmeType.Builder builder) {
        builder
            .attribute(ATTR_COMMON_PHASE)
                .setDefinitionOnly(true).setVirtual(true)
                .flag(FLAG_COMMON_PHASE_DEF).end()
                .flag(FLAG_COMMON_PHASE_REPL).end()
                .end()
            .attribute(ATTR_COMMON_REUSABLE)
                .setDefinitionOnly(true)
                .flag(FLAG_COMMON_REUSABLE_OFF).end()
                .flag(FLAG_COMMON_REUSABLE_ON).end()
                .end()
            .update()
                .setAttributeFlagsTo(FLAG_COMMON_PHASE_DEF, FLAG_COMMON_REUSABLE_ON)
                .end()
            ;
    }

    
    @Override
    public SyntagmeType getSyntagmeType(String code) {
        SyntagmeType st = getSyntagmeTypes().get(code);
        if (st == null) {
            throw new IllegalArgumentException("Invalid syntagme type code");
        }
        return st;
    }
    
    @Override
    public String toJson(boolean pretty) {
        // Initiates the field if not done yet
        getSyntagmeTypes();
        
        
        GsonBuilder gsonb = new GsonBuilder();
        if (pretty) {
            gsonb.setPrettyPrinting();
        }
        Gson gson = gsonb.create();
        return gson.toJson(this);
    }
    
    @Override
    public ContentProcessor getContentProcessor() {
        if (contentProcessor == null) {
            contentProcessor = createContentProcessor();
        }
        return contentProcessor;
    }
    
    protected ContentProcessor createContentProcessor() {
        return new ContentProcessor(this);
    }
    
//    @Override
    public String getSentenceEndingChars() {
        return ".?!";
    }
    
//    @Override
    public String getWordREChars() {
        return "\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}\\d";
    }
    
//    @Override
    public String getWordRE() {
        return "[" + getWordREChars() + "][" + getWordREChars() + "-·]*";
    }
    
    public String getNonWordSequenceRE() {
        return "[^" + getWordREChars() + "]+";
    }
    
    public String getMultipleWordsRE() {
        return getWordRE() + getNonWordSequenceRE() + getWordRE();
    }
    
//    protected SyntagmeDefinitionCondition getSingleWordSDefCondition() {
//        if (singleWordSDefCondition == null) {
//            singleWordSDefCondition = new SyntagmeDefinitionCondition.TextMatch("^" + getWordRE() + "$");
//        }
//        return singleWordSDefCondition;
//    }
//    
//    protected SyntagmeDefinitionCondition getMultipleWordsSDefCondition() {
//        if (multipleWordsSDefCondition == null) {
//            multipleWordsSDefCondition = 
//                    new SyntagmeDefinitionCondition.TextMatch("^" + getMultipleWordsRE());
//        }
//        return multipleWordsSDefCondition;
//    }

    
    @Override
    public List<TextRange> extractWords(CharSequence text) {
        List<TextRange> words = new LinkedList<>();
        
        if (extractWordsPattern == null) {
            extractWordsPattern = Pattern.compile(getExtractWordsPattern());
        }
        
        Matcher matcher = extractWordsPattern.matcher(text);
        while (matcher.find()) {
            TextRange tr = new TextRange(this, text, 
                    matcher.start(), matcher.end());
            words.add(tr);
        }
        return words;
    }
    
    protected String getExtractWordsPattern() {
        return getWordRE();
    }
    
    
    @Override
    public CharSequence getSentenceEndMarkFollowingWord(TextRange word) {
        if (sentenceEndMarkFollowingWordPattern == null) {
            sentenceEndMarkFollowingWordPattern = 
                    Pattern.compile(getSentenceEndMarkFollowingWordRE());
        }
        CharSequence textAfter = word.getTextAfter();
        Matcher matcher = sentenceEndMarkFollowingWordPattern.matcher(textAfter);
        if (matcher.find()) {
            return textAfter.subSequence(matcher.start(), matcher.end());
        }
        return null;
    }
    
    protected String getSentenceEndMarkFollowingWordRE() {
        // A direct punct not followed by word char 
        // or a punct preceded by non word chars (spaces)
        // or other sentence end mark that may be preceded by non word chars
        return "^(?:"
                + "\\.(?![" + getWordREChars() + "])"
                + "|[^" + getWordREChars() + "\\.]+\\."
                + "|[^" + getWordREChars() + "]*[\\?\\!…]"
                + ")";
    }

    @Override
    public List<Sentence> extractSentences(CharSequence text) {
        List<Sentence> ls = new LinkedList<>();
        
        List<TextRange> words = extractWords(text);
       
        Sentence s = new Sentence(text);
        s.setFromPos(0);
        for (TextRange word : words) {
            s.addWord(word);
            
            CharSequence sentenceEnd = getSentenceEndMarkFollowingWord(word);
            if (sentenceEnd != null) {
                s.setWithFinalPunct(true);
                
                int endPos = word.getEnd() + sentenceEnd.length();
                s.setToPos(endPos);
                ls.add(s);
                
                s = new Sentence(text);
                s.setFromPos(endPos);
            }
        }
        
        if (s.getWordCount() > 0) {
            s.setToPos(text.length());
            ls.add(s);
        }

        return ls;
    }

    @Override
    public boolean hasText(CharSequence text) {
        if (text == null) {
            return false;
        }
        
        if (hasTextPattern == null) {
            hasTextPattern = Pattern.compile(getWordRE());
        }
        return hasTextPattern.matcher(text).find();
    }

    @Override
    public String removeTextDiacritricalMarks(CharSequence text) {
        String result = java.text.Normalizer.normalize(
                text, java.text.Normalizer.Form.NFD);
        result = result.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return result;
    }
    
    @Override
    public String normalizeSyntagmeText(SyntagmeDefinition sd) {
        String text = sd.getText();
        if (text == null) {
            text = "";
        }
        String normalized = text.replaceAll("\\s+", " ").trim();
        return normalized;
    }
    
    
    @Override
    public void alterReplacementText(ReplaceContext ctx) {
    }
    
    protected String getNormalizedPrecedingWords(
            ReplaceContext ctx, int wordNumFromEnd) {
        TextRange firstWord = ctx.getNthLastPrecedingWord(wordNumFromEnd);
        TextRange lastWord = ctx.getNthLastPrecedingWord(1);
        String text = firstWord.getOrigSequence()
                .subSequence(firstWord.getStart(), lastWord.getEnd()).toString();
        text = text.replaceAll("\\s+", " ").toLowerCase(getLocale());
        return text;
    }
    
    protected void doReplacePrecedingText(ReplaceContext ctx, 
            int wordNumFromEnd, String replacement) {
        TextRange firstWordToReplace = ctx.getNthLastPrecedingWord(wordNumFromEnd);
        
        // Reproduce case type
        if (firstWordToReplace.isFirstUc()) {
            Text replacementText = new Text(this, replacement);
            StringBuilder sb = new StringBuilder(replacementText.length());
            sb.append(replacementText.getUcFirstLetter());
            if (firstWordToReplace.length() == 1 || firstWordToReplace.hasRemainingLc()) {
                sb.append(replacementText.getLcRemainingText());
            } else {
                sb.append(replacementText.getUcRemainingText());
            }
            replacement = sb.toString();
        }
        
        ctx.setNewPrecedingText(replacement, firstWordToReplace);
    }


//    @Override
//    public Element getHTMLSyntagmeMainDescription(SyntagmeDefinitionAbstract sd) {
//        Element root = Document.createShell("").body();
//        
//        SyntagmeDescriptionCreationCtx ctx = new SyntagmeDescriptionCreationCtx(this, sd);
//        ctx.setContentElement(root);
//        
//        addSyntagmeMainDescription(ctx);
//        
//        return root;
//    }
//
//    protected abstract void addSyntagmeMainDescription(SyntagmeDescriptionCreationCtx ctx);
//    
//    protected void addSyntagmeDescriptionAttrFlags(SyntagmeDescriptionCreationCtx ctx, 
//            String attrGroup, String beforeText, Collection<SyntagmeFlag> flags) {
//        if (flags.isEmpty()) {
//            return;
//        }
//        
//        addSyntagmeDescriptionAttrBeginText(ctx, attrGroup, beforeText);
//        
//        boolean first = true;
//        for (SyntagmeFlag flag : flags) {
//            if (!first) {
//                addSyntagmeDescriptionText(ctx, ctx.getFlagTextSeparator());
//            }
//            String flagTextParts[] = ctx.getFlagLabelParts(flag);
//            addSyntagmeDescriptionText(ctx, flagTextParts[0]);
//            if (ctx.getContentElement() != null) {
//                Element el = ctx.newElement("em").attr("class", "flag flag-" + flag.getCode());
//                el.text(flagTextParts[1]);
//                ctx.getContentElement().appendChild(el);
//            }
//            addSyntagmeDescriptionText(ctx, flagTextParts[2]);
//            first = false;
//        }
//    }
//    
//    protected void addSyntagmeDescriptionAttrCustom(SyntagmeDescriptionCreationCtx ctx,
//            String attrGroup, String beforeText, String text, String help) {
//        addSyntagmeDescriptionAttrBeginText(ctx, attrGroup, beforeText);
//
//        if (ctx.getContentElement() != null) {
//            Element el = ctx.newElement("em").attr("class", "attr-custom");
//            el.dataset().put("help", help);
//            el.text(text);
//            ctx.getContentElement().appendChild(el);
//        }
//    }
//    
//    protected void addSyntagmeDescriptionAttrBeginText(SyntagmeDescriptionCreationCtx ctx, String attrGroup, String text) {
//        if (ctx.getLastAttrGroup() == null || ctx.getLastAttrGroup().equals(attrGroup)) {
//            addSyntagmeDescriptionText(ctx, " ");
//        } else {
//            addSyntagmeDescriptionText(ctx, ctx.getAttrTextSeparator());
//        }
//        ctx.setLastAttrGroup(attrGroup);
//        
//        if (text != null) {
//            addSyntagmeDescriptionText(ctx, text);
//        }
//    }
//
//    protected void addSyntagmeDescriptionText(SyntagmeDescriptionCreationCtx ctx, String text) {
//        if (ctx.getContentElement() != null) {
//            ctx.getContentElement().appendText(text);
//        }
//    }
//    
//    protected void addSyntagmeDescriptionType(SyntagmeDescriptionCreationCtx ctx, String label) {
//        if (ctx.getContentElement() != null) {
//            Element el = ctx.newElement("em").attr("class", "st st-" + ctx.getSd().getType().getCode());
//            el.text(label);
//            ctx.getContentElement().appendChild(el);
//        }
//    }
//
//    
//    protected static class SyntagmeDescriptionCreationCtx {
//        private final LanguageAbstract language;
//        private final SyntagmeDefinitionAbstract sd;
//        private final Map<String, String[]> flagLabelsParts;
//        private Element contentElement;
////        private final Map<String, Set<SyntagmeFlag>> specificFlags;
////        private final Set<String> outputAttrs;
////        private final Set<String> customInfo;
////        private boolean firstInAttr;
//        private boolean attrSepNeeded;
//        private String lastAttrGroup;
//        private String attrTextSeparator;
//        private String flagTextSeparator;
//
////        private final Pattern aze;
//
//        public SyntagmeDescriptionCreationCtx(LanguageAbstract language, SyntagmeDefinitionAbstract sd) {
//            this.language = language;
//            this.sd = sd;
////            this.root = root;
////            specificFlags = new HashMap<>();
////            outputAttrs = new HashSet<>();
////            customInfo = new HashSet<>();
//            flagLabelsParts = new HashMap<>();
//            
////            for (SyntagmeAttribute attr : sd.getType().getAttributes().values()) {
////                Set<SyntagmeFlag> specificFlagsOn = sd.getSpecificAttributeFlagsOn(attr);
////                specificFlags.put(attr.getCode(), Collections.unmodifiableSet(specificFlagsOn));
////            }
//            attrTextSeparator = ", ";
//            flagTextSeparator = " / ";
//        }
//        
//        public SyntagmeDefinitionAbstract getSd() {
//            return sd;
//        }
//
//        public Set<SyntagmeFlag> getSpecificFlags(String attrCode) {
//            try {
//                SyntagmeAttribute attr = getSd().getAttribute(attrCode);
//                return getSd().getSpecificAttributeFlagsOn(attr);
//            } catch (IllegalArgumentException ex) {
//            }
//            return Collections.emptySet();
//        }
//        public SyntagmeFlag getSpecificFlag(String attrCode) {
//            Set<SyntagmeFlag> flags = getSpecificFlags(attrCode);
//            return flags.size() == 1 ? flags.iterator().next() : null;
//        }
//        
//        public void setContentElement(Element contentElement) {
//            this.contentElement = contentElement;
//        }
//
//        public Element getContentElement() {
//            return contentElement;
//        }
//        public Element newElement(String tag) {
//            Element baseEl = getContentElement();
//            if (baseEl == null) {
//                throw new IllegalStateException("No content element");
//            }
//            return baseEl.ownerDocument().createElement(tag);
//        }
//        
////        public void setFlagLabel(String flagCode, String useCase, String flagText) {
////            String key = flagCode;
////            if (useCase != null) {
////                key += "-" + useCase;
////            }
////            flagLabels.put(key, flagText);
////        }
////        
////        public void setFlagLabel(String flagCode, String flagText) {
////            setFlagLabel(flagCode, null, flagText);
////        }
////        
////        public String getFlagLabel(String flagCode, String useCase) {
////            String key = flagCode;
////            if (useCase != null) {
////                key += "-" + useCase;
////            }
////            return flagLabels.get(key);
////        }
//        
//        public void setFlagLabel(String flagCode, String before, String label, String after) {
//            flagLabelsParts.put(flagCode, new String[] { before, label, after });
//        }
//        public void setFlagLabel(String flagCode, String before, String label) {
//            setFlagLabel(flagCode, before, label, "");
//        }
//        public void setFlagLabel(String flagCode, String label) {
//            setFlagLabel(flagCode, "", label);
//        }
//        
//        public String[] getFlagLabelParts(SyntagmeFlag flag) {
//            String[] labelParts = flagLabelsParts.get(flag.getCode());
//            if (labelParts == null) {
//                String label = flag.getStandaloneGeneralLabel().toLowerCase(language.getLocale());
//                labelParts = new String[] { "", label, "" };
//            }
//            if (labelParts.length != 3 || labelParts[0] == null 
//                    || labelParts[1] == null || labelParts[2] == null) {
//                throw new IllegalStateException("Invalid label parts");
//            }
//            return labelParts;
//        }
//        
//
//        public String getLastAttrGroup() {        
//            return lastAttrGroup;
//        }
//
////        public Set<SyntagmeFlag> getSpecificFlags(String attrCode) {
////            return specificFlags.get(attrCode);
////        }
////        public SyntagmeFlag getSpecificFlag(String attrCode) {
////            Set<SyntagmeFlag> flags = getSpecificFlags(attrCode);
////            if (flags.size() == 1) {
////                return flags.iterator().next();
////            }
////            return null;
////        }
////        public boolean isAttrSepNeeded() {
////            return attrSepNeeded;
////        }
////
////        public void setAttrSepNeeded(boolean attrSepNeeded) {
////            this.attrSepNeeded = attrSepNeeded;
////        }
//        public void setLastAttrGroup(String lastAttrGroup) {        
//            this.lastAttrGroup = lastAttrGroup;
//        }
//
//        public String getAttrTextSeparator() {
//            return attrTextSeparator;
//        }
//
//        public void setAttrTextSeparator(String attrTextSeparator) {
//            this.attrTextSeparator = attrTextSeparator;
//        }
//
//        public String getFlagTextSeparator() {
//            return flagTextSeparator;
//        }
//
//        public void setFlagTextSeparator(String flagTextSeparator) {
//            this.flagTextSeparator = flagTextSeparator;
//        }
//        
//        
//    }
//    

    @Override
    public Element getSDefReplacementHTMLInfo(SyntagmeReplacementDefinition sdr) {
        Element listEl = new Element("ul");
        SDefHTMLInfoCtx ctx = new SDefHTMLInfoCtx(sdr, listEl);
        addSDefReplacementHTMLInfo(ctx);
        return listEl;
    }
    
    protected abstract void addSDefReplacementHTMLInfo(SDefHTMLInfoCtx ctx);

    @Override
    public Element getSDefContextHTMLInfo(SyntagmeDefinition sd) {
        Element listEl = new Element("ul");
        SDefHTMLInfoCtx ctx = new SDefHTMLInfoCtx(sd, listEl);
        addSDefContextHTMLInfo(ctx);
        return listEl;
    }
    
    protected abstract void addSDefContextHTMLInfo(SDefHTMLInfoCtx ctx);
    
    protected Element getSDefHTMLInfoCurItem(SDefHTMLInfoCtx ctx) {
        Element item = ctx.getCurItemEl();
        if (item == null) {
            item = new Element("li");
            ctx.getListEl().appendChild(item);
            ctx.setCurItemEl(item);
        }
        return item;
    }
    
    protected void appendSDefHTMLInfoCurItemChild(SDefHTMLInfoCtx ctx, Node child) {
        String sep = ctx.getNextSeparator();
        if (sep != null) {
            getSDefHTMLInfoCurItem(ctx).appendText(sep);
            ctx.setNextSeparator(null);
        }

        getSDefHTMLInfoCurItem(ctx).appendChild(child);
        ctx.setNewSentence(false);
    }
    protected void appendSDefHTMLInfoCurItemText(SDefHTMLInfoCtx ctx, String text) {
        if (text.length() > 0) {
            appendSDefHTMLInfoCurItemChild(ctx, 
                    new TextNode(capitalizeHTMLInfoText(ctx, text), ""));
        }
    }
    protected void closeSDefHTMLInfoCurItem(SDefHTMLInfoCtx ctx) {
        ctx.setCurItemEl(null);
        ctx.setNextSeparator(null);
        ctx.setNewSentence(true);
    }

//    protected void addSDefAttrHTMLInfo(Element container, String attrCode, String text) {
//        Element el = new Element("em").attr("class", "attr attr-" + attrCode);
//        el.text(text);
//        container.appendChild(el);
//    }
    
    protected String capitalizeHTMLInfoText(SDefHTMLInfoCtx ctx, String text) {
        if (ctx.isNewSentence()) {
            text = text.substring(0, 1).toUpperCase(getLocale()) + text.substring(1);
        }
        return text;
    }
    
    protected Element addSDefAttrHTMLInfo(SDefHTMLInfoCtx ctx, String attrCode, String innerText) {
        Element el = new Element("em").attr("class", "attr attr-" + attrCode);
        el.text(capitalizeHTMLInfoText(ctx, innerText));
        appendSDefHTMLInfoCurItemChild(ctx, el);
        return el;
    }
    
    protected Element addSDefFlagHTMLInfo(SDefHTMLInfoCtx ctx, String flagCode, String innerText) {
        Element el = new Element("em").attr("class", "flag flag-" + flagCode);
        el.text(capitalizeHTMLInfoText(ctx, innerText));
        appendSDefHTMLInfoCurItemChild(ctx, el);
        return el;
    }
    
    protected Element addSDefFlagHTMLInfo(SDefHTMLInfoCtx ctx, String flagCode) {
        String flagLabelParts[] = ctx.getFlagLabel(flagCode);
        if (flagLabelParts[0] != null) {
            appendSDefHTMLInfoCurItemText(ctx, flagLabelParts[0]);
        }
        Element el = addSDefFlagHTMLInfo(ctx, flagCode, flagLabelParts[1]);
        if (flagLabelParts[2] != null) {
            appendSDefHTMLInfoCurItemText(ctx, flagLabelParts[2]);
        }
        return el;
    }
    
    protected void addSDefSelectedAttrFlagsHTMLInfo(SDefHTMLInfoCtx ctx, 
            String attrCode, String introText, String nextSep) {
        Set<SyntagmeFlag> attrFlags = ctx.getAttribute(attrCode).getFlags();
        String[] flagCodes = new String[attrFlags.size()];
        int count = 0;
        for (SyntagmeFlag flag : attrFlags) {
            flagCodes[count++] = flag.getCode();
        }
        addSDefSelectedFlagsHTMLInfo(ctx, introText, nextSep, flagCodes);
    }
    
    protected void addSDefSelectedFlagsHTMLInfo(SDefHTMLInfoCtx ctx, 
            String introText, String nextSep, String... orderedFlags) {            
        Set<String> flagsOn = new LinkedHashSet<>(orderedFlags.length);
        for (String flagCode : orderedFlags) {
            if (ctx.getSdef().isFlagOn(flagCode)) {
                flagsOn.add(flagCode);
            }
        }
        int count = 0;
        for (String flagCode : flagsOn) {
            if (count > 0) {
                if (count == flagsOn.size() - 1) {
                    appendSDefHTMLInfoCurItemText(ctx, ctx.getFlagOptionSeparatorLast());
                } else {
                    appendSDefHTMLInfoCurItemText(ctx, ctx.getFlagOptionSeparator());
                }
            } else if (introText != null) {
                appendSDefHTMLInfoCurItemText(ctx, introText);
            }
            
            addSDefFlagHTMLInfo(ctx, flagCode);
            count++;
        }
        if (count > 0) {
            ctx.setNextSeparator(nextSep);
        }
    }
    
    protected static class SDefHTMLInfoCtx {
        private static final Logger logger = LoggerFactory.getLogger(SDefHTMLInfoCtx.class);

//        private final LanguageAbstract language;
        private final SyntagmeDefinition sd;
        private final SyntagmeReplacementDefinition sdr;
        private final Element listEl;
        private final Map<String, String[]> flagLabels;
        private String flagOptionSeparator;
        private String flagOptionSeparatorLast;
        private Element curItemEl;
        private boolean newSentence;
        private String nextSeparator;
        private boolean newSubItem;

//        public SDefHTMLInfoCtx(LanguageAbstract language, 
//                SyntagmeDefinition sd, SyntagmeReplacementDefinition sdr, Element listEl) {
//            this.language = language;
//            this.sd = sd;
//            this.sdr = sdr;
//            this.listEl = listEl;
//            flagLabels = new HashMap<>();
//            
//            flagOptionSeparator = ", ";
//            flagOptionSeparatorLast = " or ";
//        }
//        
//        public SDefHTMLInfoCtx(LanguageAbstract language, SyntagmeDefinition sd, Element listEl) {
//            this(language, sd, null, listEl);
//        }
//        
//        public SDefHTMLInfoCtx(LanguageAbstract language, SyntagmeReplacementDefinition sdr, Element listEl) {
//            this(language, null, sdr, listEl);
//        }
//
//        public LanguageAbstract getLanguage() {
//            return language;
//        }
        public SDefHTMLInfoCtx(SyntagmeDefinition sd, SyntagmeReplacementDefinition sdr, Element listEl) {
            this.sd = sd;
            this.sdr = sdr;
            this.listEl = listEl;
            flagLabels = new HashMap<>();
            newSentence = true;
            
            flagOptionSeparator = ", ";
            flagOptionSeparatorLast = " or ";
        }
        
        public SDefHTMLInfoCtx(SyntagmeDefinition sd, Element listEl) {
            this(sd, null, listEl);
        }
        
        public SDefHTMLInfoCtx(SyntagmeReplacementDefinition sdr, Element listEl) {
            this(null, sdr, listEl);
        }

        
        public SyntagmeDefinition getSd() {
            return sd;
        }

        public SyntagmeReplacementDefinition getSdr() {
            return sdr;
        }
        
        public SyntagmeDefinitionAbstract getSdef() {
            return sd != null ? sd : sdr;
        }

        public Element getListEl() {
            return listEl;
        }

        public void setCurItemEl(Element curItemEl) {
            this.curItemEl = curItemEl;
        }        
        public Element getCurItemEl() {
            return curItemEl;
        }

        public boolean isNewSentence() {
            return newSentence;
        }

        public void setNewSentence(boolean newSentence) {
            this.newSentence = newSentence;
        }

        public String getNextSeparator() {
            return nextSeparator;
        }

        public void setNextSeparator(String nextSeparator) {
            this.nextSeparator = nextSeparator;
        }
        
        
        public void setFlagLabel(String flagCode, 
                String textBefore, String label, String textAfter) {
            flagLabels.put(flagCode, new String[] { textBefore, label, textAfter });
        }
        public void setFlagLabel(String flagCode, String label) {
            setFlagLabel(flagCode, null, label, null);
        }
        public String[] getFlagLabel(String flagCode) {
            String result[] = flagLabels.get(flagCode);
            if (result == null) {
                logger.warn("No flag label for \"{}\"", flagCode);
                result = new String[] { null, flagCode, null };
            }
            return result;
        }
//        public String getFlagLabel(String flagCode, boolean ucFirst) {
//            String result = getFlagLabel(flagCode);
//            if (ucFirst) {
//                Locale l = getSdef().getType().getLanguage().getLocale();
//                result = result.substring(0, 1).toUpperCase(l) + result.substring(1);
//            }
//            return result;
//        }

        public void setFlagOptionSeparator(String flagOptionSeparator) {
            this.flagOptionSeparator = flagOptionSeparator;
        }
        public String getFlagOptionSeparator() {
            return flagOptionSeparator;
        }

        public void setFlagOptionSeparatorLast(String flagOptionSeparatorLast) {
            this.flagOptionSeparatorLast = flagOptionSeparatorLast;
        }
        public String getFlagOptionSeparatorLast() {
            return flagOptionSeparatorLast;
        }
        
        public SyntagmeAttribute getAttribute(String attrCode) {
            return getSdef().getAttribute(attrCode);
        }
        public Set<String> getAttributeFlagsOn(String attrCode) {
            SyntagmeAttribute attr = getAttribute(attrCode);
            Set<String> result = new HashSet<>(attr.getFlags().size());
            for (SyntagmeFlag flag : attr.getFlags()) {
                String flagCode = flag.getCode();
                if (getSdef().isFlagOn(flagCode)) {
                    result.add(flagCode);
                }
            }
            return result;
        }
        
        public boolean areAllFlagsOn(String... flagCodes) {
            for (String flagCode : flagCodes) {
                if (!getSdef().isFlagOn(flagCode)) {
                    return false;
                }
            }
            return true;
        }
    }
}
