package de.jambonna.lolpapers.core.app;

import de.jambonna.lolpapers.core.model.Category;
import de.jambonna.lolpapers.core.model.CategoryData;
import de.jambonna.lolpapers.core.model.Url;
import de.jambonna.lolpapers.core.model.WebsiteEntity;
import de.jambonna.lolpapers.core.model.WebsiteData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds some data to be shared among all threads of the application and avoid
 * database requests
 */
@ThreadSafe
public class SharedData {
    private static final Logger logger = LoggerFactory.getLogger(SharedData.class);
    
    
    private final Map<String, Url> urlsByRequestPath;
    private final Map<String, Url> urlsByIdentifier;
    private WebsiteData[] websites = { };

    
    public SharedData() {
        urlsByRequestPath = new HashMap<>();
        urlsByIdentifier = new HashMap<>();
    }
    
    public void resetUrls(List<Url> urls) {
        synchronized(urlsByRequestPath) {
            synchronized(urlsByIdentifier) {
                urlsByRequestPath.clear();
                urlsByIdentifier.clear();
                for (Url url: urls) {
                    updateUrlByRequestPath(url);
                }
                logCurrentUrls();
            }
        }
    }
    
    public void updateUrls(List<Url> urls) {
        synchronized(urlsByRequestPath) {
            synchronized(urlsByIdentifier) {
                for (Url url: urls) {
                    updateUrlByRequestPath(url);
                }
                logCurrentUrls();
            }
        }
    }
    
    private void updateUrlByRequestPath(Url url) {
        Url urlCopy = new Url(url);
        urlsByRequestPath.put(urlCopy.getRequestPath(), urlCopy);
        if (!url.isHistory()) {
            urlsByIdentifier.put(urlCopy.getIdentifier(), urlCopy);
        }
    }
    
    public Url getUrlForRequestPath(String requestPath) {
        Url result = null;
        synchronized(urlsByRequestPath) {
            Url url = urlsByRequestPath.get(requestPath);
            if (url != null) {
                result = new Url(url);
            }
        }
        return result;
    }
    
    public Url getUrlForIdentifier(String identifier) {
        Url result = null;
        synchronized(urlsByIdentifier) {
            Url url = urlsByIdentifier.get(identifier);
            if (url != null) {
                result = new Url(url);
            }
        }
        return result;
    }
    
    public void logCurrentUrls() {
        logger.debug("Dumping current urls:");
        synchronized(urlsByRequestPath) {
            for (Map.Entry<String, Url> e : urlsByRequestPath.entrySet()) {
                Url url = e.getValue();
                logger.debug("'{}' => '{}' ({})", 
                    e.getKey(), url.getDestPath(), url.isHistory());
            }
        }
        synchronized(urlsByIdentifier) {
            for (Map.Entry<String, Url> e : urlsByIdentifier.entrySet()) {
                Url url = e.getValue();
                logger.debug("'{}' => '{}' ({})", 
                    e.getKey(), url.getRequestPath(), url.getDestPath());
            }
        }
    }
    
    
    
    public void updateWebsites(Collection<Category> websiteCategories) {
        Map<WebsiteEntity, List<Category>> websitesMap = new LinkedHashMap<>();
        
        for (Category c : websiteCategories) {
            WebsiteEntity w = c.getWebsite();
            if (w != null) {
                List<Category> cats = websitesMap.get(w);
                if (cats == null) {
                    cats = new ArrayList<>(websiteCategories.size());
                    websitesMap.put(w, cats);
                }
                cats.add(c);
            }
        }
        
        WebsiteData[] newWebsites = new WebsiteData[websitesMap.size()];
        int nb = 0;
        for (Map.Entry<WebsiteEntity, List<Category>> entry : websitesMap.entrySet()) {
            WebsiteEntity w = entry.getKey();
            newWebsites[nb++] = WebsiteData.createFromWebsite(w, entry.getValue());
        }
        websites = newWebsites;
        
        logCurrentWebsites();
    }
    
    public void logCurrentWebsites() {
        logger.debug("Dumping current websites:");
        for (WebsiteData w : websites) {
            logger.debug("{} ({}, {})", w.getName(), w.getId(), w.getCode());
            for (CategoryData c : w.getCategories()) {
                logger.debug(" => {} ({}, {})", c.getTitle(), c.getId(), c.getCode());
            }
        }
    }
    
    public WebsiteData[] getWebsites() {
        return Arrays.copyOf(websites, websites.length);
    }
    
    public WebsiteData getWebsite(Long id) {
        for (WebsiteData w : websites) {
            if (w.getId().equals(id)) {
                return w;
            }
        }
        return null;
    }
    
    public WebsiteData getWebsite(String code) {
        for (WebsiteData w : websites) {
            if (w.getCode().equals(code)) {
                return w;
            }
        }
        return null;
    }
}
