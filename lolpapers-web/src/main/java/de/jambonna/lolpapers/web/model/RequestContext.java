package de.jambonna.lolpapers.web.model;

import de.jambonna.lolpapers.core.app.App;
import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.Url;
import de.jambonna.lolpapers.core.model.Urls;
import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.core.model.Website;
import de.jambonna.lolpapers.core.model.WebsiteData;
import de.jambonna.lolpapers.core.model.WebsiteEntity;
import de.jambonna.lolpapers.core.persistence.MainDao;
import de.jambonna.lolpapers.web.model.session.UserSession;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;


/**
 * Object wrapping a HttpServletRequest, provides base services for any web component.
 * Is to be stored in the request so that all components use the same object for
 * one request. Holds a MainDao object so that any web component use the same
 * MainDao object during the request.
 * The caller has to close() this object when no more needed.
 */
public class RequestContext {
    public static final String REQUEST_ATTRIBUTE = "myRequestCtx";
    
    private final HttpServletRequest servletRequest;
    private ResourceBundle messageBundle;
    private WebApp webApp;
    private MainDao mainDao;
    private String baseUrl;
    private String mediaBaseUrl;
    private WebsiteData websiteData;
    private WebsiteEntity websiteEntity;
    private UserSession userSession;
    private User user;

    
    public RequestContext(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
    
    public void setupInRequest() {
        getRequest().setAttribute(REQUEST_ATTRIBUTE, this);
        
        // Set JSTL locale since we can't tell it what locale to use when 
        // client's locale is not managed
        Config.set(getRequest(), Config.FMT_LOCALE, getLocale());
    }
    
    public static RequestContext getFromRequest(HttpServletRequest r) {
        Object o = r.getAttribute(REQUEST_ATTRIBUTE);
        if (o instanceof RequestContext) {
            return (RequestContext)o;
        }
        return null;
    }

    public HttpServletRequest getRequest() {
        if (servletRequest == null) {
            throw new IllegalStateException("No request");
        }
        return servletRequest;
    }
    
    public ServletContext getServletContext() {
        return getRequest().getServletContext();
    }
    
    public String getParam(String name) {
        return getRequest().getParameter(name);
    }
    
    public Long getLongParam(String name) {
        String value = getRequest().getParameter(name);
        if (value != null) {
            try {
                return Long.valueOf(value);
            } catch (NumberFormatException ex) {
            }
        }
        return null;
    }

    public Locale getLocale() {
        return getRequest().getLocale();
    }
    
    public ResourceBundle getMessageBundle() {
        if (messageBundle == null) {
            // Use the same bundle as Jstl tags
            String messageBundleName = getServletContext().getInitParameter(
                    "javax.servlet.jsp.jstl.fmt.localizationContext");
            messageBundle = ResourceBundle.getBundle(messageBundleName, getLocale());
        }
        return messageBundle;
    }

    public WebApp getWebApp() {
        if (webApp == null) {
            webApp = WebApp.getFromContext(getServletContext());
            if (webApp == null) {
                throw new IllegalStateException("No WebApp in context lol");
            }
        }
        return webApp;
    }
    public App getApp() {
        return getWebApp().getApp();
    }

    public MainDao getMainDao() throws AppException {
        if (mainDao == null) {
            mainDao = getApp().createDaoObject(MainDao.class);
        }
        return mainDao;
    }
    
//    public Urls getUrls() {
//        return getWebApp().getUrls();
//    }
    
    public String getBaseUrl() {
        if (baseUrl == null) {            
            baseUrl = getApp().getConfig().getAppBaseUrl();
            if (baseUrl == null || baseUrl.length() == 0) {
                baseUrl = getRequest().getContextPath() + "/";
            }
        }
        return baseUrl;
    }
    
    public boolean isFullBaseUrl() {
        String bu = getBaseUrl();
        return bu.startsWith("http://") || bu.startsWith("https://");
    }
    
    public String getMediaBaseUrl() {
        if (mediaBaseUrl == null) {
            String mediaBasePath = getServletContext().getInitParameter(
                    "de.jambonna.lolpapers.web.varFilesBasePath");
            mediaBaseUrl = getBaseUrl() + mediaBasePath;
        }
        return mediaBaseUrl;
    }
    
    public String getRequestedInternalPath() {
        // getRequestURI() always returns the internal uri (ex : even if request through proxy)
        String requestPath = getRequest().getRequestURI();
        String contextPath = getRequest().getContextPath() + "/";
        if (requestPath.startsWith(contextPath)) {
            requestPath = requestPath.substring(contextPath.length());
        }
        return requestPath;
    }
    
    public String getRequestedPubUrl() {
        String requestPath = (String)getRequest().getAttribute("javax.servlet.forward.request_uri");
        if (requestPath != null) {
            String contextPath = getRequest().getContextPath() + "/";
            if (requestPath.startsWith(contextPath)) {
                requestPath = requestPath.substring(contextPath.length());
            }
        } else {
            requestPath = getRequestedInternalPath();
        }
        return getUrl(requestPath);

    }
    
    
    public Urls getUrls() {
        return Urls.getInstance();
    }
    
    public String getUrl(String identifier, Website website) {
        String theIdentifier = getUrls().getIdentifier(identifier, website);
        Url url = getApp().getSharedData().getUrlForIdentifier(theIdentifier);
        if (url == null) {
            throw new IllegalArgumentException("Invalid url id : \"" + theIdentifier + "\"");
        }
        return getUrl(url.getRequestPath());
    }
    
    public String getUrl(String requestPath) {
        String result = getBaseUrl() + requestPath;
        return result;
    }
    

    
    public boolean isDevMode() {
        String k = getWebApp().getDevKey();
        return k != null && k.equals(getRequest().getHeader("X-Dev-Key"));
    }
    
    public Long getDevRngSeed() {
        if (isDevMode()) {
            String hdr = getRequest().getHeader("X-Dev-Rng-Seed");
            try {
                return Long.valueOf(hdr);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }
    
    public Random getRng() {
        Long seed = getDevRngSeed();
        if (seed != null) {
            return new Random(seed);
        }
        return new Random();
    }
    

    public WebsiteData getWebsiteData() {
        return websiteData;
    }

    public void setWebsiteData(WebsiteData websiteData) {
        this.websiteData = websiteData;
    }
    
    public WebsiteData loadWebsiteDataFromParam() throws AppException {
        Long websiteId = getLongParam(Urls.COMMON_WEBSITE_ID_PARAM);
        if (websiteId == null) {
            throw new AppException("No website param");
        }
        websiteData = getApp().getSharedData().getWebsite(websiteId);
        if (websiteData == null) {
            throw new AppException("No website data for id " + websiteId);
        }
        return websiteData;
    }

    
 
    public void setWebsiteEntity(WebsiteEntity websiteEntity) throws AppException {
        this.websiteEntity = websiteEntity;
        if (websiteEntity != null) {
            Long websiteId = websiteEntity.getWebsiteId();
            websiteData = getApp().getSharedData().getWebsite(websiteId);
            if (websiteData == null) {
                throw new AppException("No website data for id " + websiteId);
            }
        }
    }
    
    public WebsiteEntity getWebsiteEntity() throws AppException {
        if (websiteEntity == null) {
            Long websiteId = getLongParam(Urls.COMMON_WEBSITE_ID_PARAM);
            if (websiteId == null) {
                throw new AppException("No website param");
            }
            websiteEntity = getMainDao().findWebsite(websiteId);
            if (websiteEntity == null) {
                throw new AppException("No such website " + websiteId);
            }
        }
        return websiteEntity;
    }
    
    
    public boolean isWebsiteMatchingParam(Website website) {
        return website != null && website.getId() != null 
                && website.getId().equals(getLongParam(Urls.COMMON_WEBSITE_ID_PARAM));
    }
    
    
    public UserSession getUserSession() {
        if (userSession == null) {
            userSession = (UserSession)getRequest().getSession().getAttribute("userSession");
            if (userSession == null) {
                userSession = new UserSession();
                getRequest().getSession().setAttribute("userSession", userSession);
            }
        }
        return userSession;
    }
    
    public void setCurrentUrlForAfterLogin() {
        getUserSession().setUrlAfterLogin(getRequestedPubUrl());
    }

    
    public User getUser() throws AppException {
        if (user == null) {
            user = new User();
            UserSession session = getUserSession();
            Long userId = session.getUserId();
            if (userId != null) {
                user = getMainDao().findUser(userId);
                if (!user.isActive()) {
                    user = null;
                    session.setUserId(null);
                    session.addErrorTextMessage(
                        getMessageBundle().getString("user.disabledError"));
                }
            }
        }
        return user;
    }
    
    
    public void close() {
        if (mainDao != null) {
            mainDao.close();
            mainDao = null;
        }
    }
    
}
