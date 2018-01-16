package de.jambonna.lolpapers.core.model;

import java.util.Locale;
import java.util.TimeZone;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * A Website in the WebApp. There is typically a Website by language
 */
@Entity(name = "Website")
public class WebsiteEntity extends ModelAbstract implements Website {
    
    @Id
    private Long websiteId;

    private String code;

    private String urlKey;
    
    private String name;
    
    private String pageName;
    
    private String localeCode;
    
    @Transient
    private Locale locale;
    
    private String defaultTimezoneCode;
    
    @Transient
    private TimeZone defaultTimezone;
    

    
    public Long getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }
    
    @Override
    public Long getId() {
        return getWebsiteId();
    }
    
    @Override
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
    
    @Override
    public String getRequestPath() {
        return getAltRequestPath() + "/";
    }
    public String getAltRequestPath() {
        String theUrlKey = getUrlKey();
        if (theUrlKey == null) {
            throw new IllegalStateException("No website url key");
        }
        return theUrlKey;
    }
    
    public Url getUrlToGenerate() {
        Url url = new Url();
        url.setRequestPath(getRequestPath());
        url.setAltRequestPath(getAltRequestPath());
        Long id = getWebsiteId();
        if (id != null) {
            Urls urls = Urls.getInstance();
            url.setIdentifier(urls.getIdentifier("home", this));
            url.setDestPath(urls.getInternalPageRequestPath("home", this, null));
        }
        return url;
    }
    

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
    
    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }
    
    @Override
    public Locale getLocale() {
        if (locale == null) {
            locale = new Locale(getLocaleCode());
        }
        return locale;
    }


    public String getDefaultTimezoneCode() {
        return defaultTimezoneCode;
    }

    public void setDefaultTimezoneCode(String defaultTimezoneCode) {
        this.defaultTimezoneCode = defaultTimezoneCode;
    }
    
    @Override
    public TimeZone getDefaultTimezone() {
        if (defaultTimezone == null) {
            if (getDefaultTimezoneCode() != null) {
                defaultTimezone = TimeZone.getTimeZone(getDefaultTimezoneCode());
            } else {
                defaultTimezone = TimeZone.getDefault();
            }
        }
        return defaultTimezone;
    }
}
