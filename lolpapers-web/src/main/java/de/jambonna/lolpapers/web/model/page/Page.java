package de.jambonna.lolpapers.web.model.page;

import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.ArticleTemplate;
import de.jambonna.lolpapers.core.model.TemplatePlaceholder;
import de.jambonna.lolpapers.core.model.Urls;
import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.core.model.UserException;
import de.jambonna.lolpapers.core.model.WebsiteData;
import de.jambonna.lolpapers.core.model.WebsiteEntity;
import de.jambonna.lolpapers.web.model.RequestContext;
import de.jambonna.lolpapers.web.model.session.Message;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;


/**
 * Base class for frontend beans typically used in JSPs
 */
public class Page {
    private HttpServletRequest servletRequest;
    private RequestContext requestContext;
    private List<Message> messages;
    private String bodyClass;
    private String curPageTitle;
    private WebsiteData[] websites;
    
    private List<ArticleTemplate> userArticleTemplates;
    private Integer userNbPlaceholdersToFill;



    
    public HttpServletRequest getServletRequest() {
        if (servletRequest == null) {
            throw new IllegalStateException("No request");
        }
        return servletRequest;
    }

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
    
    public RequestContext getRequestContext() {
        if (requestContext == null) {
            requestContext = RequestContext.getFromRequest(getServletRequest());
            if (requestContext == null) {
                throw new IllegalStateException("No request context");
            }
        }
        return requestContext;
    }
    
    public String getBaseUrl() {
        return getRequestContext().getBaseUrl();
    }
    
    public String getMediaBaseUrl() {
        return getRequestContext().getMediaBaseUrl();
    }
    
    public String getUrl(String requestPath) {
        return getRequestContext().getUrl(requestPath);
    }
    
    public String getUserConnectUrl() {
        return getRequestContext().getUrl(Urls.USER_CONNECT_URL_ID, getWebsite());
    }
    public String getUserConnectTwitterUrl() {
        return getRequestContext().getUrl(Urls.USER_CONNECT_TWITTER_URL_ID, getWebsite());
    }
    public String getUserDisconnectUrl() {
        return getRequestContext().getUrl(Urls.USER_DISCONNECT_URL_ID, getWebsite());
    }
    public String getUserProfilerUrl() throws AppException {
        User u = getUser();
        return getUrl(u.getProfileRequestPath(getWebsite()));
    }
    public String getNewArticleTemplateUrl() {
        return getRequestContext().getUrl(Urls.ARTICLE_TEMPLATE_NEW_URL_ID, getWebsite());
    }
    public String getEditArticleTemplateUrl(ArticleTemplate t) {
        return getRequestContext().getUrl(Urls.ARTICLE_TEMPLATE_EDIT_URL_ID, t.getWebsite()) 
                + Urls.query().param("id", t.getContentTemplateId());
    }
    public String getFillReplacementUrl() {
        return getRequestContext().getUrl(Urls.FILL_REPLACEMENT_URL_ID, getWebsite());
    }
    public String getHelpUrl() {
        return getRequestContext().getUrl("help", getWebsite());
    }
    public String getHelpAboutUrl() {
        return getRequestContext().getUrl("about", getWebsite());
    }
    
    public boolean isDevMode() {
        return getRequestContext().isDevMode();
    }
    public String getAssetsVer() {
        String value = getRequestContext().getServletContext()
                .getInitParameter("de.jambonna.lolpapers.web.assetsVer");
        return value != null ? value : "";
    }


    public String getPageTitle() throws AppException {
        return String.join(" - ", getPageTitleParts());
    }
    


    public void setCurPageTitle(String curPageTitle) {
        this.curPageTitle = curPageTitle;
    }

    public String getCurPageTitle() {
        return curPageTitle;
    }
    
    protected List<String> getPageTitleParts() {
        List<String> parts = new LinkedList<>();
        
        String curPage = getCurPageTitle();
        if (curPage != null) {
            parts.add(curPage);
        }
        
        if (isInWebsite()) {
            WebsiteData w = getWebsite();
            parts.add(w.getName());
        } else {
            parts.add(getRequestContext().getMessageBundle().getString("pageTitle.root"));
        }
        return parts;
    }
    
    public boolean isInWebsite()  {
        return getRequestContext().getWebsiteData() != null;
    }
    
    public WebsiteData getWebsite() {
        WebsiteData website = getRequestContext().getWebsiteData();
        if (website == null) {
            throw new IllegalStateException("No website data");
        }
        return website;
    }
    
    public WebsiteEntity getWebsiteEntity() throws AppException {
        WebsiteEntity w = getRequestContext().getWebsiteEntity();
        return w;
    }
    
    public WebsiteData[] getWebsites() {
        if (websites == null) {
            websites = getRequestContext().getApp().getSharedData().getWebsites();
        }
        return websites;
    }
    
    
    public String getWebsiteHomeUrl() {
        return getUrl(getWebsite().getRequestPath());
    }
    
    public String getCurPageUrl() {
        return getRequestContext().getRequestedPubUrl();
    }
    
    public List<Breadcrumb> getBreadcrumbs() {
        List<Breadcrumb> breadcrumbs = new LinkedList<>();
                
        WebsiteData w = getWebsite();
        if (w != null) {
            breadcrumbs.add(newBreadcrumb(w.getRequestPath(), w.getName()));
        }
        
        String u = getCurPageUrl();
        String t = getCurPageTitle();
        if (t != null) {
            breadcrumbs.add(newBreadcrumbUrl(u, t));
        }
        
        return breadcrumbs;
    }
    
    protected Breadcrumb newBreadcrumb(String path, String label) {
        Breadcrumb b = new Breadcrumb();
        b.setUrl(getUrl(path));
        b.setLabel(label);
        b.setAlt(label);
        return b;
    }
    protected Breadcrumb newBreadcrumbUrl(String url, String label) {
        Breadcrumb b = new Breadcrumb();
        b.setUrl(url);
        b.setLabel(label);
        b.setAlt(label);
        return b;
    }

    public String getBodyClass() {
        return bodyClass;
    }

    public void setBodyClass(String bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Map<String, String> getMetaProperties() {
        return Collections.emptyMap();
    }
        
    public String getTestException() throws UserException {
        throw new UserException("lol", "Machin bidule trucs");
    }
    public String getTestException2() {
        throw new IllegalStateException("Coucou, tu veux voir ma");
    }
    
    public String getUniqueHtmlId() {
        return UUID.randomUUID().toString();
    }
    
    public String getShortText(String text, int maxLg) {
        return StringUtils.abbreviate(text, maxLg);
    }
    
    public User getUser() throws AppException {
        return getRequestContext().getUser();
    }
    
    public List<Message> getMessages() {
        if (messages == null) {
            messages = getRequestContext().getUserSession().getMessages(true);
        }
        return messages;
    }
    
    public List<ArticleTemplate> getUserArticleTemplates() throws AppException {
        if (userArticleTemplates == null) {
            userArticleTemplates = Collections.emptyList();
            
            User user = getUser();
            if (user.isExisting()) {
                userArticleTemplates = getRequestContext().getMainDao()
                        .getUserUnfinishedArticleTemplates(user);
            }
        }
        return userArticleTemplates;
    }
    

    public int getUserNbPlaceholdersToFill() throws AppException {
        if (userNbPlaceholdersToFill == null) {
            User user = getUser();
            userNbPlaceholdersToFill = user.getRemainingPossibleReplacement();
            if (user.isExisting() && userNbPlaceholdersToFill > 0) {
                List<TemplatePlaceholder> placeholders = getRequestContext().getMainDao()
                        .getOrderedUnfilledTemplatePlaceholders(getWebsiteEntity());
                userNbPlaceholdersToFill = Math.min(
                        userNbPlaceholdersToFill, 
                        user.getPlaceholdersToFill(placeholders).size());
            }
        }
        return userNbPlaceholdersToFill;
    }
    
    public TimeZone getTimezone() {
        TimeZone tz = TimeZone.getDefault();
        if (isInWebsite()) {
            tz = getWebsite().getDefaultTimezone();
        }
        return tz;
    }
}
