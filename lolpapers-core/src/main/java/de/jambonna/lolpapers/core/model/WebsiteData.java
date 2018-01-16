package de.jambonna.lolpapers.core.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A thread-safe read-only object holding Website entity data
 */
public class WebsiteData implements Website {
    private final Long id;
    private final String code;
    private final String name;
    private final String pageName;
    private final String requestPath;
    private final Locale locale;
    private final TimeZone defaultTimezone;
    private final CategoryData[] categories;

    public WebsiteData(Long id, String code, String name, String pageName,
            String requestPath, Locale locale, TimeZone defaultTimezone,
            CategoryData[] categories) {
        if (id == null || code == null || name == null || requestPath == null
                || locale == null || defaultTimezone == null || categories == null) {
            throw new IllegalArgumentException("Invalid website data");
        }

        this.id = id;
        this.code = code;
        this.name = name;
        this.pageName = pageName;
        this.requestPath = requestPath;
        this.locale = locale;
        this.defaultTimezone = defaultTimezone;
        this.categories = categories;
    }

    public static WebsiteData createFromWebsite(WebsiteEntity website, Collection<Category> categories) {
        if (website == null) {
            throw new IllegalArgumentException("Invalid website");
        }
        
        CategoryData[] cats = new CategoryData[categories.size()];
        int num = 0;
        for (Category c : categories) {
            if (!website.equals(c.getWebsite())) {
                throw new IllegalArgumentException("Invalid category");
            }
            cats[num++] = new CategoryData(c);
        }
        return new WebsiteData(website.getWebsiteId(), website.getCode(), 
                website.getName(), website.getPageName(),
                website.getRequestPath(), website.getLocale(), 
                website.getDefaultTimezone(), cats);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPageName() {
        return pageName;
    }
    
    @Override
    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public TimeZone getDefaultTimezone() {
        return defaultTimezone;
    }

    public CategoryData[] getCategories() {
        return Arrays.copyOf(categories, categories.length);
    }
}
