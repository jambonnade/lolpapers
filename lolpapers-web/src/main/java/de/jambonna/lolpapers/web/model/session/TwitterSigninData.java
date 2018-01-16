package de.jambonna.lolpapers.web.model.session;

import java.io.Serializable;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Twitter signin related data in session
 */
public class TwitterSigninData implements Serializable {
    private String requestToken;
    private String requestTokenSecret;
    private String accessToken;
    private String accessTokenSecret;
    private Long userId;
    private String screenName;


    public synchronized RequestToken getRequestToken() {
        if (requestToken != null && requestTokenSecret != null) {
            return new RequestToken(requestToken, requestTokenSecret);
        }
        return null;
    }
    
    public synchronized void setRequestToken(RequestToken token) {
        requestToken = token.getToken();
        requestTokenSecret = token.getTokenSecret();
    }

    public synchronized AccessToken getAccessToken() {
        if (accessToken != null && accessTokenSecret != null) {
            return new AccessToken(accessToken, accessTokenSecret);
        }
        return null;
    }
    
    public synchronized void setAccessToken(AccessToken token) {
        accessToken = token.getToken();
        accessTokenSecret = token.getTokenSecret();
    }

    public Long getUserId() {
        return userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public synchronized void setTwitterUser(User twitterUser) {
        screenName = twitterUser.getScreenName();
        userId = twitterUser.getId();
    }

}
