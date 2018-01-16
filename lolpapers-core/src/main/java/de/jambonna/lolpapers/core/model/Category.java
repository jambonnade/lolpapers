package de.jambonna.lolpapers.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Category in a Website for the webapp
 */
@Entity
public class Category extends ModelAbstract {

    
    @Id
    private Long categoryId;
    
    @ManyToOne
    @JoinColumn(name = "websiteId")
    private WebsiteEntity website;
    
    private String code;
    
    private String urlKey;

    private String title;
    
    private int position;

    
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public WebsiteEntity getWebsite() {
        return website;
    }

    public void setWebsite(WebsiteEntity website) {
        this.website = website;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }
    
    public String getUrlRequestPath() {
        return getUrlAltRequestPath() + "/";
    }
    
    public String getUrlAltRequestPath() {
        WebsiteEntity theWebsite = getWebsite();
        if (theWebsite == null) {
            throw new IllegalStateException("No category website");
        }
        String theUrlKey = getUrlKey();
        if (theUrlKey == null) {
            throw new IllegalStateException("No category url key");
        }
        return theWebsite.getRequestPath() + theUrlKey;
    }
    
    public Url getUrlToGenerate() {
        Url url = new Url();
        url.setRequestPath(getUrlRequestPath());
        url.setAltRequestPath(getUrlAltRequestPath());
        Long id = getCategoryId();
        if (id != null) {
            Urls urls = Urls.getInstance();
            url.setIdentifier(urls.getIdentifier("category-" + id, null));
            url.setDestPath(urls.getInternalRequestPath("category", Urls.query().param("id", id)));
        }
        return url;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
