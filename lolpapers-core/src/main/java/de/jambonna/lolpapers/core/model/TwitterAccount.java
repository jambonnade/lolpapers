package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.app.App;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * Utility wrapping a Twitter API client, making calls through some given
 * twitter account credentials.
 */
public class TwitterAccount {
    private static final Logger logger = LoggerFactory.getLogger(TwitterAccount.class);

    
    private final String consumerKey;
    private final String consumerSecret;
    private final String accessToken;
    private final String accessTokenSecret;
    private Twitter twitter;

    public TwitterAccount(String consumerKey, String consumerSecret, 
            String accessToken, String accessTokenSecret) {
        if (consumerKey == null || consumerKey.length() == 0) {
            throw new IllegalArgumentException("Invalid consumer key");
        }
        this.consumerKey = consumerKey;
        if (consumerSecret == null || consumerSecret.length() == 0) {
            throw new IllegalArgumentException("Invalid consumer secret");
        }
        this.consumerSecret = consumerSecret;
        if (accessToken == null || accessToken.length() == 0) {
            throw new IllegalArgumentException("Invalid access token");
        }
        this.accessToken = accessToken;
        if (accessTokenSecret == null || accessTokenSecret.length() == 0) {
            throw new IllegalArgumentException("Invalid access token secret");
        }
        this.accessTokenSecret = accessTokenSecret;
    }
    
    public Twitter getTwitter() {
        if (twitter == null) {
            twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            twitter.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
        }
        return twitter;
    }
    
    public void tweetLink(String url, String text) throws TwitterException {
        // Estimate of shorten link size
        int maxLinkLg = 40 + 1;
        int remainingLg = 280 - maxLinkLg;
        String tweet = StringUtils.abbreviate(text, remainingLg) + " " + url;
        logger.info("Tweeting \"{}\"", tweet);
        getTwitter().updateStatus(tweet);
        logger.info("... done");
    }
    
    public static TwitterAccount mainAccount(App app) {
        TwitterAccount th = new TwitterAccount(
                app.getConfig().getTwitterMainConsumerKey(), 
                app.getConfig().getTwitterMainConsumerSecret(),
                app.getConfig().getTwitterMainAccessToken(), 
                app.getConfig().getTwitterMainAccessTokenSecret());
        return th;
    }
}
