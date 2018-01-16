package de.jambonna.lolpapers.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Url entity used in the webapp "URL rewriting process" 
 */
@Entity
public class Url {
    @Id
    private Long urlId;
    
    private String identifier;
    private String requestPath;
    private String destPath;
    private boolean history;
    
    @Transient
    private String altRequestPath;
    

    
    public Url() {
        
    }
    
    public Url(Url url) {
        urlId = url.getUrlId();
        identifier = url.getIdentifier();
        requestPath = url.getRequestPath();
        destPath = url.getDestPath();
        history = url.isHistory();
        altRequestPath = url.getAltRequestPath();
    }
    

    public Long getUrlId() {
        return urlId;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public String getAltRequestPath() {
        return altRequestPath;
    }

    public void setAltRequestPath(String altRequestPath) {
        this.altRequestPath = altRequestPath;
    }
}
