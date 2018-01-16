package de.jambonna.lolpapers.web.components.front.user;

import de.jambonna.lolpapers.core.model.Urls;
import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.core.model.UserException;
import de.jambonna.lolpapers.web.components.front.*;
import de.jambonna.lolpapers.web.model.RequestContext;
import de.jambonna.lolpapers.web.model.session.TwitterSigninData;
import de.jambonna.lolpapers.web.model.session.UserSession;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


@WebServlet(name = "User Connect Twitter", urlPatterns = { "/internal/user/connect/twitter" })
public class ConnectTwitterServlet extends FrontServletAbstract {

    private static final Logger logger = LoggerFactory.getLogger(ConnectTwitterServlet.class);

    @Override
    protected boolean preProcess(RequestContext rc, HttpServletResponse response) throws Exception {
        rc.loadWebsiteDataFromParam();
        
        if (rc.getUser().isExisting()) {
            redirectHome(rc, response);
            return false;
        }
        return true;
    }
    
    
    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        UserSession session = rc.getUserSession();
        
        TwitterSigninData tsd = session.getTwitterSigninData();
        if (tsd == null) {
            tsd = new TwitterSigninData();
            session.setTwitterSigninData(tsd);
        }

        Twitter twitter = getTwitterInstance(rc);

        AccessToken accessToken = tsd.getAccessToken();
        
        if (accessToken != null) {
            twitter.setOAuthAccessToken(accessToken);
                        
            // Gets user data first if not done yet
            if (tsd.getUserId() == null) {
                twitter4j.User twitterUser = twitter.verifyCredentials();
                tsd.setTwitterUser(twitterUser);
            }
            
            // Login by twitter user id if user exists
            User user = rc.getMainDao().findUserByTwitterId(tsd.getUserId());
            if (user != null) {
                login(rc, response, user);
                return;
            }
        
            // Asks for screen name
            
            rc.getRequest().setAttribute("screenName", tsd.getScreenName());
            executeJsp(rc, response, "user/connect/twitter.jsp");
            
        } else {
            String oauthTokenParam = rc.getParam("oauth_token");
            String oauthVerifierParam = rc.getParam("oauth_verifier");
            RequestToken requestToken = tsd.getRequestToken();
            if (oauthTokenParam != null && oauthVerifierParam != null
                    && requestToken != null && oauthTokenParam.equals(requestToken.getToken())) {
                // User returns from twitter auth page

                accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifierParam);
                tsd.setAccessToken(accessToken);
                
                // Redirect without parameters
                redirectTwitterConnect(rc, response);
            } else {
                // Gets a new request token and redirect to auth page
                requestToken = twitter.getOAuthRequestToken();
                tsd.setRequestToken(requestToken);
                
                String url = requestToken.getAuthorizationURL();
                response.sendRedirect(url);
            }
        }
        
    }

    @Override
    protected void postAction(RequestContext rc, HttpServletResponse response) throws Exception {

        UserSession session = rc.getUserSession();
        
        AccessToken accessToken = null;
        Long twitterUserId = null;
        TwitterSigninData tsd = session.getTwitterSigninData();
        if (tsd != null) {
            twitterUserId = tsd.getUserId();
            accessToken = tsd.getAccessToken();
        }
        String name = rc.getRequest().getParameter("name");
        
        if (twitterUserId != null && accessToken != null && name != null) {
            try {
                User user = User.newUser(name, rc.getApp());
                user.setTwitterUserId(twitterUserId);
                user.setTwitterOauthToken(accessToken.getToken());
                user.setTwitterOauthTokenSecret(accessToken.getTokenSecret());
                rc.getMainDao().saveUser(user);

                login(rc, response, user);
                return;
            } catch (UserException ex) {
                switch (ex.getCode()) {
                    case User.USER_EXCEPTION_INVALID_NAME:
                        addSessionError(rc, "user.invalidUserNameError", name);
                        break;
                    default:
                        throw ex;
                }
            }
        }

        
        redirectTwitterConnect(rc, response);
    }
    
    protected Twitter getTwitterInstance(RequestContext rc) {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(
            rc.getApp().getConfig().getTwitterSigninConsumerKey(),
            rc.getApp().getConfig().getTwitterSigninConsumerSecret());
        return twitter;
    }
    
    protected void redirectTwitterConnect(RequestContext rc, HttpServletResponse response) throws IOException {
        String url = rc.getUrl(Urls.USER_CONNECT_TWITTER_URL_ID, rc.getWebsiteData());
        response.sendRedirect(url);
    }
    
    protected void login(RequestContext rc, HttpServletResponse response, User user) throws IOException {
        UserSession session = rc.getUserSession();
        
        session.setUserId(user.getUserId());
        addSessionSuccess(rc, "user.connectSuccess", user.getScreenName());

        String url = session.getUrlAfterLogin();
        if (url != null) {
            response.sendRedirect(url);
        } else {
            redirectHome(rc, response);
        }
        
        session.setTwitterSigninData(null);
    }
    
}
