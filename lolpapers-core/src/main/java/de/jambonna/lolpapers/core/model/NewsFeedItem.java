package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.validation.BeanValidator;
import com.rometools.rome.feed.synd.SyndEntry;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

/**
 * An RSS feed item
 */
@Entity
public class NewsFeedItem {

    @Id
    private Long newsFeedItemId;
    
    @ManyToOne
    @JoinColumn(name = "newsFeedId")
    private NewsFeed newsFeed;
    
    @NotNull
    @Size(max = 250)
    private String title;
    
    @NotNull
    @URL
    @Size(max = 250)
    private String url;
    
    @NotNull
    @Size(max = 250)
    private String uid;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date pubDate;
    
    


    public Long getNewsFeedItemId() {
        return newsFeedItemId;
    }

    public void setNewsFeedItemId(Long newsFeedItemId) {
        this.newsFeedItemId = newsFeedItemId;
    }

    public NewsFeed getNewsFeed() {
        return newsFeed;
    }

    public void setNewsFeed(NewsFeed newsFeed) {
        this.newsFeed = newsFeed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        BeanValidator.getInstance().validatePropertyValue(NewsFeedItem.class, "url", url);
        this.url = url;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        BeanValidator.getInstance().validatePropertyValue(NewsFeedItem.class, "uid", uid);
        this.uid = uid;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        BeanValidator.getInstance().validatePropertyValue(NewsFeedItem.class, "title", title);
        this.title = title;
    }
    
    public String getArticleIdentifier() {
        return String.format("nf:%s:%s", getNewsFeed().getCode(), getUid());
    }

    

    
    public void setFromSyndEntry(SyndEntry entry) {
        setTitle(entry.getTitle());
        setUrl(entry.getLink());
        setUid(entry.getUri());
        setPubDate(entry.getPublishedDate());
    }
    
}
