package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.validation.BeanValidator;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;


/**
 * An RSS feed and its related data
 */
@Entity
public class NewsFeed {

    @Id
    private Long newsFeedId;
    
    @NotNull
    @Size(max = 250)
    private String url;
    
    private String code;
    
    private boolean active;
    
    @Size(max = 250)
    private String title;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    
    private Integer itemLimit;
    
    @OneToMany(mappedBy = "newsFeed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NewsFeedItem> items = new ArrayList<>();

    @Transient
    private InputStream feedStream;
    
    @Transient
    private ArticleDownloader articleDownloader;
    
//    @ManyToMany
//    @JoinTable(
//        name = "NewsFeedCategory",
//        joinColumns = @JoinColumn(name = "newsFeedId"),
//        inverseJoinColumns = @JoinColumn(name = "categoryId")
//    )   
//    private List<Category> categories = new ArrayList<>();

    
    public Long getNewsFeedId() {
        return newsFeedId;
    }

    public void setNewsFeedId(Long newsFeedId) {
        this.newsFeedId = newsFeedId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        BeanValidator.getInstance().validatePropertyValue(NewsFeed.class, "url", url);
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Integer maxLg = BeanValidator.getInstance()
                .getPropertyMaxLength(NewsFeed.class, "title");
        title = StringUtils.abbreviate(title, maxLg);

        this.title = title;
    }


    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getItemLimit() {
        return itemLimit;
    }

    public void setItemLimit(Integer itemLimit) {
        this.itemLimit = itemLimit;
    }
    
    

    public List<NewsFeedItem> getItems() {
        return items;
    }

    public InputStream getFeedStream() {
        return feedStream;
    }

    public void setFeedStream(InputStream feedStream) {
        this.feedStream = feedStream;
    }

    

    public void update() throws IOException {
        Date updateDate = new Date();
        
        if (isActive()) {
            SyndFeedInput input = new SyndFeedInput();
            InputStream is = getFeedStream();
            XmlReader reader;
            if (is == null) {
                String feedUrlStr = getUrl();
                if (feedUrlStr == null || "".equals(feedUrlStr)) {
                    throw new IllegalStateException("Empty feed url");
                }
                URL feedUrl = new URL(feedUrlStr);
                reader = new XmlReader(feedUrl);
            } else {
                reader = new XmlReader(is);

            }
            try {
                SyndFeed feed = input.build(reader);
                updateFromFeed(feed);
            } catch (FeedException e) {
                throw new IOException(e);
            }
        } else {
            items.clear();
        }
        
        setUpdatedAt(updateDate);
    }

    protected void updateFromFeed(SyndFeed feed) {
        setTitle(feed.getTitle());
        
        items.clear();
        for (SyndEntry e : feed.getEntries()) {
            if (getItemLimit() == null || items.size() < getItemLimit()) {
                addItemFromFeed(e);
            }
        }
    }
    
    protected void addItemFromFeed(SyndEntry e) {
        NewsFeedItem item = new NewsFeedItem();
        item.setNewsFeed(this);
        item.setFromSyndEntry(e);
//        item.setCreatedAt(new Date());
//        item.updateUpdatedAt();
        items.add(item);
    }
    

    
//    public static List<NewsFeed> getAllNewsFeed(EntityManager em) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<NewsFeed> cq = cb.createQuery(NewsFeed.class);
//        Root<NewsFeed> nfRoot = cq.from(NewsFeed.class);
//        cq.select(nfRoot);
//        TypedQuery<NewsFeed> q = em.createQuery(cq);
//        List<NewsFeed> nf = q.getResultList();
//        return nf;
//    }

    public ArticleDownloader getArticleDownloader() {
        if (articleDownloader == null) {
            // Create a specific ArticleDownloader (or set configuration)
            // if a newsfeed needs special processing in fetching content
            articleDownloader = new ArticleDownloader();
        }
        return articleDownloader;
    }
}
