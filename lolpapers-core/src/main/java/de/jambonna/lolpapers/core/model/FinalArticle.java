package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.validation.BeanValidator;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

/**
 * Final article (main content entity type).
 */
@Entity
public class FinalArticle extends FinalContent {
    
    private String articleTitle;
    private boolean articleWithDescription;
    private String articleDescription;
    private String articleContent;
    
    @Size(max = 250)
    private String articleUrlKey;
    
    

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
    

    public boolean isArticleWithDescription() {
        return articleWithDescription;
    }

    public void setArticleWithDescription(boolean articleWithDescription) {
        this.articleWithDescription = articleWithDescription;
    }
    
    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
    
    public String getArticleUrlKey() {
        return articleUrlKey;
    }
    
    public void setArticleUrlKey(String articleUrlKey) {
        this.articleUrlKey = articleUrlKey;
    }
    
    public void updateArticleUrlKey(int suffixNum) {
        Integer maxLg = BeanValidator.getInstance()
                .getPropertyMaxLength(FinalArticle.class, "articleUrlKey");
        if (maxLg == null) {
            throw new IllegalStateException("Can't find url key max length");
        }
        
        String title = getArticleTitle();
        if (title == null) {
            throw new IllegalStateException("No article title");
        }
        Urls urls = Urls.getInstance();
        String urlKey = urls.getFinalPubRequestPath(
                urls.normalizeForPubRequestPath(title), suffixNum, maxLg);
        setArticleUrlKey(urlKey);
    }

    public String getArticleUrlRequestPath() {
        String urlKey = getArticleUrlKey();
        if (urlKey == null) {
            throw new IllegalStateException("No url key");
        }
        BaseContent bc = getBaseContent();
        if (bc == null || bc.getCategory() == null) {
            throw new IllegalStateException("Invalid base content");
        }
        String requestPath = bc.getCategory().getUrlRequestPath() + urlKey;
        return requestPath;
    }
    
    public Url getArticleUrl() {
        Url url = new Url();
        Long id = getFinalContentId();
        if (id != null) {
            Urls urls = Urls.getInstance();
            url.setIdentifier(urls.getIdentifier("finalarticle-" + id, null));
            url.setDestPath(urls.getInternalRequestPath("finalarticle", Urls.query().param("id", id)));
        }

        url.setRequestPath(getArticleUrlRequestPath());
        return url;
    }
    
    public EntityUrlUpdater getArticleUrlUpdater() {
        return new EntityUrlUpdater() {
            private int suffix = 0;

            @Override
            public List<Url> getUrls() {
                return Arrays.asList(getArticleUrl());
            }
            
            @Override
            public boolean nextBasePath() {
                suffix++;
                if (suffix >= 100) {
                    return false;
                }
                updateArticleUrlKey(suffix);
                return true;
            }
        };
    }
    
    public BaseArticle getBaseArticle() {
        BaseContent bc = getBaseContent();
        if (bc == null || !(bc instanceof BaseArticle)) {
            throw new IllegalStateException("Invalid base article");
        }
        return (BaseArticle)bc;
    }
}
