package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.model.text.ContentProcessor;
import de.jambonna.lolpapers.core.model.text.ContentProcessor.WrapWordsContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Transient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 */
@Entity
public class ArticleTemplate extends ContentTemplate {
    private String articleTitle;
    
    @Transient
    private Element articleTitleElement;
    
    private String articleDescription;
    
    @Transient
    private Element articleDescriptionElement;
    
    private boolean articleDescriptionRejected;
    
    private String articleContent;
    
    @Transient
    private Element articleContentElement;
    
    @Transient
    private List<Element> articleContentElements;
    
    private String articleContentRemovedBlocks;
    
    @Transient
    private int[] articleContentRemovedBlockIds;
    
    
    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
    
    public Element getArticleTitleElement() {
        if (articleTitleElement == null) {
            if (articleTitle != null) {
                Document contentDoc = Jsoup.parseBodyFragment(articleTitle);
                articleTitleElement = contentDoc.body();
            }
        }
        return articleTitleElement;

    }

    public void setArticleTitleElement(Element articleTitleElement) {
        Element contentCopy = articleTitleElement.clone();
        setArticleTitle(contentCopy.html());
        this.articleTitleElement = contentCopy;
        articleContentElements = null;
    }
    
    public String getArticleTitleText() {
        return getArticleTitleElement().text();
    }


    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public Element getArticleDescriptionElement() {
        if (articleDescriptionElement == null) {
            if (articleDescription != null) {
                Document contentDoc = Jsoup.parseBodyFragment(articleDescription);
                articleDescriptionElement = contentDoc.body();
            }
        }
        return articleDescriptionElement;
    }

    public void setArticleDescriptionElement(Element articleDescriptionElement) {
        Element contentCopy = articleDescriptionElement.clone();
        setArticleDescription(contentCopy.html());
        this.articleDescriptionElement = contentCopy;
        articleContentElements = null;
    }

    public boolean isArticleDescriptionRejected() {
        return articleDescriptionRejected;
    }

    public void setArticleDescriptionRejected(boolean articleDescriptionRejected) {
        this.articleDescriptionRejected = articleDescriptionRejected;
    }


    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
    
    public Element getArticleContentElement() {
        if (articleContentElement == null) {
            if (articleContent != null) {
                Document articleContentDoc = Jsoup.parseBodyFragment(articleContent);
                articleContentElement = articleContentDoc.body();
            }
        }
        return articleContentElement;
    }

    public void setArticleContentElement(Element articleContentElement) {
        Element contentCopy = articleContentElement.clone();
        setArticleContent(contentCopy.html());
        this.articleContentElement = contentCopy;
        articleContentElements = null;
    }

    @Override
    public List<Element> getActualContentElements() {
        if (articleContentElements == null) {
            ContentProcessor p = getLanguage().getContentProcessor();
            List<Element> containers = new ArrayList<>(3);
            if (getArticleTitleElement() != null) {
                containers.add(getArticleTitleElement());
            }
            if (!isArticleDescriptionRejected() && getArticleDescriptionElement() != null) {
                containers.add(getArticleDescriptionElement());
            }
            if (getArticleContentElement() != null) {
                Element filteredContent = p.removeBlockElements(
                        getArticleContentElement(), getArticleContentRemovedBlockIds());
                containers.add(filteredContent);
            }
            articleContentElements = Collections.unmodifiableList(containers);
        }
        return articleContentElements;
    }

    
    public void setFromBaseArticle(BaseArticle ba) {
        setBaseContent(ba);

        ContentProcessor p = getLanguage().getContentProcessor();
        WrapWordsContext wwctx = new WrapWordsContext(p);

        if (!getLanguage().hasText(ba.getArticleTitle())) {
            throw new IllegalArgumentException("Invalid base article title");
        }
        setArticleTitleElement(
                wrapWordsArticleField(wwctx, ba.getArticleTitle()));
        
        setArticleDescriptionElement(
                wrapWordsArticleField(wwctx, ba.getArticleDescription()));
        
        Element baseContent = ba.getArticleContentElement();
        if (baseContent == null) {
            throw new IllegalArgumentException("Invalid base article content");
        }
        setArticleContentElement(p.wrapWords(baseContent, wwctx));        
    }
    
    private Element wrapWordsArticleField(WrapWordsContext ctx, String content) {
        Element outputContainer = null;
        if (getLanguage().hasText(content)) {
            Element textContainer = Document.createShell("").body().appendText(content);
            outputContainer = ctx.getProcessor().wrapWords(textContainer, ctx);
        }
        return outputContainer;
    }

    public String getArticleContentRemovedBlocks() {
        return articleContentRemovedBlocks;
    }

    public void setArticleContentRemovedBlocks(String articleContentRemovedBlocks) {
        this.articleContentRemovedBlocks = articleContentRemovedBlocks;
        articleContentRemovedBlockIds = null;
        articleContentElements = null;
    }

    public int[] getArticleContentRemovedBlockIds() {
        if (articleContentRemovedBlockIds == null && articleContentRemovedBlocks != null) {
            String[] strIds = articleContentRemovedBlocks.split(",");
            int[] ids = new int[strIds.length];
            int nb = 0;
            for (String blkId : strIds) {
                try {
                    int id = Integer.valueOf(blkId);
                    if (id > 0) {
                        ids[nb++] = id;
                    }
                } catch (NumberFormatException ex) {
                }
            }
            articleContentRemovedBlockIds = Arrays.copyOf(ids, nb);
            Arrays.sort(articleContentRemovedBlockIds);
        }
        return articleContentRemovedBlockIds;
    }

    public void setArticleContentRemovedBlockIds(int[] articleContentRemovedBlockIds) {
        this.articleContentRemovedBlockIds = null;
        articleContentRemovedBlocks = null;
        articleContentElements = null;
        if (articleContentRemovedBlockIds != null) {
            this.articleContentRemovedBlockIds = 
                    Arrays.copyOf(articleContentRemovedBlockIds, 
                            articleContentRemovedBlockIds.length);
            Arrays.sort(this.articleContentRemovedBlockIds);
            
            List<String> strIds = new ArrayList<>(this.articleContentRemovedBlockIds.length);
            for (int id : this.articleContentRemovedBlockIds) {
                if (id > 0) {
                    strIds.add(String.valueOf(id));
                }
            }
            articleContentRemovedBlocks = String.join(",", strIds);
        }
    }
    
    
    public BaseArticle getBaseArticle() {
        return (BaseArticle)getBaseContent();
    }
    
    public boolean canUserAccess() {
        return getFinishedAt() == null;
    }
    
    public int getMinPlaceholders() {
        ContentProcessor p = getLanguage().getContentProcessor();
        int wc = p.getWrappedWordCount(getActualContentElements());
        return Math.max(
                wc / getApp().getConfig().getAppArticleTemplateMaxWordsByPlaceholder(),
                getApp().getConfig().getAppArticleTemplateMinPlaceholder());
    }
    
    public void finish() throws UserException {
        ContentProcessor p = getLanguage().getContentProcessor();

        if (p.getWrappedWordCount(getActualContentElements())
                < getApp().getConfig().getAppArticleMinWc()) {
            throw new UserException("not-enough-words", "Not enough words");
        }
        
        if (getPlaceholders().size() < getMinPlaceholders()) {
            throw new UserException("not-enough-placeholders", "Not enough placeholders");
        }
        
        for (TemplatePlaceholder tp : getPlaceholders()) {
            tp.validatePlaceholder();
            tp.validatePlaceholderDefinition();
        }
        validatePlaceholderReferences();
        
        setFinishedAt(new Date());
        incUsersPlaceholderCount();
        User u = getUser();
        u.setFinishedArticleCount(u.getFinishedArticleCount() + 1);
        u.setToSave(true);
    }
    
    public FinalArticle toFinalArticle() {
        if (!areAllPlaceholdersFilled()) {
            throw new IllegalStateException("Some placeholders not filled");
        }
        
        FinalArticle fa = new FinalArticle();
        fa.init(getApp());
        fa.setCreatedAt(new Date());
        fa.setContentTemplate(this);        
        
        if (getArticleTitleElement() == null || getArticleContentElement() == null) {
            throw new IllegalStateException("Invalid article template");
        }
        
        ContentProcessor p = getLanguage().getContentProcessor();
        
        Element finalTitleElement = p.getFinalArticleContent(
                getArticleTitleElement(), getPlaceholders());
        fa.setArticleTitle(finalTitleElement.text());
        
        Element finalContentElement = p.getFinalArticleContent(
                p.removeBlockElements(getArticleContentElement(), 
                        getArticleContentRemovedBlockIds()), 
                getPlaceholders());
        fa.setArticleContent(finalContentElement.html());

        if (getArticleDescriptionElement() != null && !isArticleDescriptionRejected()) {
            Element finalDescrElement = p.getFinalArticleContent(
                    getArticleDescriptionElement(), getPlaceholders());
            fa.setArticleDescription(finalDescrElement.text());
            fa.setArticleWithDescription(true);
        } else {
            String text = p.getIntroText(finalContentElement, 
                    getApp().getConfig().getAppArticleAutoDescriptionMinWc());
            fa.setArticleDescription(text);
            fa.setArticleWithDescription(false);
        }
        
        FinalContentParticipant fcp = fa.getParticipant(getUser());
        fcp.setInitiator(true);
        for (TemplatePlaceholder tp : getPlaceholders()) {
            fcp = fa.getParticipant(tp.getCreatedBy());
            fcp.incPlaceholderCount();
            
            // Can be null in case of referenced placeholder
            if (tp.getReplacementBy() != null) {
                fcp = fa.getParticipant(tp.getReplacementBy());
                fcp.incReplacementCount();
            }
        }
        
        setCompletedAt(fa.getCreatedAt());
        setToSave(true);
        
        fa.updateArticleUrlKey(0);
        
        return fa;
    }
    

}
