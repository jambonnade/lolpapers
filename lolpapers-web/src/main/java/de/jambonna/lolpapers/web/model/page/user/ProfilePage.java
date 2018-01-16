package de.jambonna.lolpapers.web.model.page.user;

import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.web.model.page.Page;


public class ProfilePage extends Page {
    private User profileUser;

    public User getProfileUser() {
        if (profileUser == null) {
            profileUser = (User)getServletRequest().getAttribute("user");
            if (profileUser == null) {
                throw new IllegalStateException("No user");
            }
        }
        return profileUser;
    }
    
    @Override
    public String getCurPageUrl() {
        return getUrl(getProfileUser().getProfileRequestPath(getWebsite()));
    }
   
}
