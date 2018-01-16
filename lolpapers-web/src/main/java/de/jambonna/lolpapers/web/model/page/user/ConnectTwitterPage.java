package de.jambonna.lolpapers.web.model.page.user;

import de.jambonna.lolpapers.web.model.page.Breadcrumb;
import de.jambonna.lolpapers.web.model.page.Page;
import java.util.List;


public class ConnectTwitterPage extends Page {
    private String screenName;

    @Override
    public List<Breadcrumb> getBreadcrumbs() {
        List<Breadcrumb> breadcrumbs = super.getBreadcrumbs();
        breadcrumbs.add(breadcrumbs.size() - 1,
            newBreadcrumbUrl(getUserConnectUrl(), 
                getRequestContext().getMessageBundle().getString("user.connect.pageTitle")));
        breadcrumbs.add(breadcrumbs.size() - 1,
            newBreadcrumbUrl(getUserConnectTwitterUrl(), 
                getRequestContext().getMessageBundle().getString("user.connectTwitter.pageTitle")));
        return breadcrumbs;
    }

    @Override
    protected List<String> getPageTitleParts() {
        List<String> parts = super.getPageTitleParts();
        parts.add(1, getRequestContext().getMessageBundle().getString("user.connectTwitter.pageTitle"));
        return parts;
    }
    
    

    @Override
    public String getCurPageUrl() {
        return null;
    }

    
    
    public String getScreenName() {
        if (screenName == null) {
            screenName = (String)getServletRequest().getAttribute("screenName");
        }
        if (screenName == null) {
            throw new IllegalStateException("No input screen name");
        }
        return screenName;
    }
    
    
}
