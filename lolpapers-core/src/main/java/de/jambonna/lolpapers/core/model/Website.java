package de.jambonna.lolpapers.core.model;

import java.util.Locale;
import java.util.TimeZone;

/**
 * An object holding Website entity data
 */
public interface Website {
    public Long getId();
    
    public String getCode();

    public String getName();

    public String getPageName();
    
    public String getRequestPath();

    public Locale getLocale();
    
    public TimeZone getDefaultTimezone();

}
