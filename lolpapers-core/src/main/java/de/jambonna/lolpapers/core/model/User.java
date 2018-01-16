package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.app.App;
import de.jambonna.lolpapers.core.validation.BeanValidator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Size;

/**
 * A User entity in the application
 */
@Entity
public class User extends ModelAbstract {
    
    public static final String USER_EXCEPTION_INVALID_NAME = "user-invalid-name";
    
    @Id
    private Long userId;
    
    @Version
    private Long versionNumber;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String screenName;
    
    @Size(max = 250)
    private String urlKey;
    
    @Transient
    private boolean withUrlUpdate;
    
    private boolean active;
    private boolean trusted;
    
    private Long twitterUserId;
    private String twitterOauthToken;
    private String twitterOauthTokenSecret;

    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastArticleLimitReset;
    
    private int startedArticleCount;
    
    private int placeholderCount;
    private int replacementCount;
    private int finishedArticleCount;
    
    private boolean sdefTooltipsDisabled;
    
    @Transient
    private boolean toSave;
    
//    @ManyToMany
//    @JoinTable(
//        name = "UserBaseContent",
//        joinColumns = @JoinColumn(name = "userId"),
//        inverseJoinColumns = @JoinColumn(name = "baseContentId")
//    )
//    @OrderColumn(name = "position")
//    private final List<BaseContent> baseContentList = new ArrayList<>();

    public static User newUser(String screenName, App app) throws UserException {
        User user = new User();
        user.init(app);
        user.setCreatedAt(new Date());
        user.updateScreenName(screenName);
        user.setActive(true);
        user.setLastArticleLimitReset(new Date());
        return user;
    }
    

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public boolean isExisting() {
        return getUserId() != null;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
    
    public void updateScreenName(String screenName) throws UserException {
        setScreenName(screenName);
        updateUrlKey(0);
        if ("".equals(getUrlKey())) {
            throw new UserException(USER_EXCEPTION_INVALID_NAME,
                    "Invalid screen name \"" + screenName + "\"");
        }
        setWithUrlUpdate(true);
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public boolean isWithUrlUpdate() {
        return withUrlUpdate;
    }

    public void setWithUrlUpdate(boolean withUrlUpdate) {
        this.withUrlUpdate = withUrlUpdate;
    }
    
    
    
    public void updateUrlKey(int suffixNum) {
        Integer maxLg = BeanValidator.getInstance()
                .getPropertyMaxLength(User.class, "urlKey");
        if (maxLg == null) {
            throw new IllegalStateException("Can't find url key max length");
        }
        
        String name = getScreenName();
        if (name == null) {
            throw new IllegalStateException("No user name");
        }
        Urls urls = Urls.getInstance();
        String result = urls.getFinalPubRequestPath(
                urls.normalizeForPubRequestPath(name), suffixNum, maxLg);
        setUrlKey(result);
    }

    public String getProfileRequestPath(Website website) {
        String key = getUrlKey();
        if (key == null) {
            throw new IllegalStateException("No url key");
        }
        if (website == null) {
            throw new IllegalArgumentException("No website");
        }
        String requestPath = website.getRequestPath() + "user/profile/" + key;
        return requestPath;
    }
    
    public Url getProfileUrl(Website website) {
        Url url = new Url();
        Long id = getUserId();
        if (id != null) {
            url.setIdentifier(Urls.getInstance().getIdentifier(
                    "user-profile-" + id, website));
            url.setDestPath(Urls.getInstance().getInternalRequestPath(
                    "user/profile", website, Urls.query().param("id", id)));
        }

        url.setRequestPath(getProfileRequestPath(website));
        return url;
    }
    
    public EntityUrlUpdater getProfileUrlUpdater(
            final List<? extends Website> websites) {
        
        return new EntityUrlUpdater() {
            private int suffix = 0;

            @Override
            public List<Url> getUrls() {
                List<Url> urls = new ArrayList<>(websites.size());
                for (Website w : websites) {
                    urls.add(getProfileUrl(w));
                }
                return urls;
            }
            
            @Override
            public boolean nextBasePath() {
                suffix++;
                if (suffix >= 100) {
                    return false;
                }
                updateUrlKey(suffix);
                return true;
            }
        };
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }
    
    public Long getTwitterUserId() {
        return twitterUserId;
    }

    public void setTwitterUserId(Long twitterUserId) {
        this.twitterUserId = twitterUserId;
    }

    public String getTwitterOauthToken() {
        return twitterOauthToken;
    }

    public void setTwitterOauthToken(String twitterOauthToken) {
        this.twitterOauthToken = twitterOauthToken;
    }

    public String getTwitterOauthTokenSecret() {
        return twitterOauthTokenSecret;
    }

    public void setTwitterOauthTokenSecret(String twitterOauthTokenSecret) {
        this.twitterOauthTokenSecret = twitterOauthTokenSecret;
    }
        
    
    

    public Date getLastArticleLimitReset() {
        return lastArticleLimitReset;
    }

    public void setLastArticleLimitReset(Date lastArticleLimitReset) {
        this.lastArticleLimitReset = lastArticleLimitReset;
    }
    
    public Date getNextArticleLimitReset() {
        return new Date(getLastArticleLimitReset().getTime()
                + getApp().getConfig().getAppUserArticleTemplateResetDelay() * 1000);
    }

    public int getStartedArticleCount() {
        return startedArticleCount;
    }

    public void setStartedArticleCount(int startedArticleCount) {
        this.startedArticleCount = startedArticleCount;
    }
    
    public boolean canStartArticleTemplate() {
        return getStartedArticleCount() < getApp().getConfig().getAppUserArticleTemplateLimit()
                || new Date().after(getNextArticleLimitReset());
    }

    public ArticleTemplate startArticleTemplate(BaseArticle ba) {
        if (!canStartArticleTemplate()) {
            throw new IllegalStateException("User can't start article");
        }
        Date now = new Date();
        if (now.after(getNextArticleLimitReset())) {
            setLastArticleLimitReset(now);
            setStartedArticleCount(0);
        }
        setStartedArticleCount(getStartedArticleCount() + 1);
        setToSave(true);
                
        ArticleTemplate t = new ArticleTemplate();
        t.init(getApp());
        t.setFromBaseArticle(ba);
        t.setUser(this);
        t.setCreatedAt(now);
        t.setLastSaveAt(now);
        
        ba.take();
        ba.setToSave(true);

        return t;
    }
    

    public int getPlaceholderCount() {
        return placeholderCount;
    }

    public void setPlaceholderCount(int placeholderCount) {
        this.placeholderCount = placeholderCount;
    }
    
    public void incPlaceholderCount() {
        placeholderCount++;
    }

    public int getReplacementCount() {
        return replacementCount;
    }

    public void setReplacementCount(int replacementCount) {
        this.replacementCount = replacementCount;
    }
    
    public int getRemainingPossibleReplacement() {
        return getPlaceholderCount() - getReplacementCount();
    }
    public boolean canDoReplacement() {
        return getRemainingPossibleReplacement() > 0;
    }
    
    
    public List<TemplatePlaceholder> getPlaceholdersToFill(
            List<TemplatePlaceholder> placeholders) {
        List<TemplatePlaceholder> result = new ArrayList<>(placeholders.size());
        
        Date now = new Date();
        long maxStartTimeForExpired = now.getTime() 
                - getApp().getConfig().getAppUserTemplatePlacholderFillMaxDuration() * 1000;
        for (TemplatePlaceholder tp : placeholders) {
            if (!equals(tp.getCreatedBy())
                    && (equals(tp.getReplacementBy()) 
                        || tp.getReplacementBy() == null
                        || (tp.getReplacementStartedAt() != null
                            && tp.getReplacementStartedAt().getTime() < maxStartTimeForExpired))) {
                result.add(tp);
            }
        }
        return result;
    }
    
    public TemplatePlaceholder getStartedBySelfPlaceholderToFill(
            List<TemplatePlaceholder> placeholders) {
        for (TemplatePlaceholder tp : placeholders) {
            if (equals(tp.getReplacementBy())) {
                return tp;
            }
        }
        return null;
    }
    
    public void takePlaceholderToFill(TemplatePlaceholder tp) {
        tp.setReplacementBy(this);
        tp.setReplacementStartedAt(new Date());

//        // Count this as a replacement even if replacement not yet completed
//        replacementCount++;
//        // User has to be saved during placeholder save
//        setToSave(true);
    }


    public int getFinishedArticleCount() {
        return finishedArticleCount;
    }

    public void setFinishedArticleCount(int finishedArticleCount) {
        this.finishedArticleCount = finishedArticleCount;
    }    

    public boolean isSdefTooltipsDisabled() {
        return sdefTooltipsDisabled;
    }

    public void setSdefTooltipsDisabled(boolean sdefTooltipsDisabled) {
        this.sdefTooltipsDisabled = sdefTooltipsDisabled;
    }
    
    public boolean isToSave() {
        return toSave;
    }

    public void setToSave(boolean toSave) {
        this.toSave = toSave;
    }
    
}
