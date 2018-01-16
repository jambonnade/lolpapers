package de.jambonna.lolpapers.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


/**
 *
 */
public class CreateTwitterAccessToken {

    
    public static void main(String[] args) throws TwitterException, IOException {
        Twitter twitter = TwitterFactory.getSingleton();
        // Use -Dtwitter4j.oauth.consumerKey / -Dtwitter4j.oauth.consumerSecret
//    twitter.setOAuthConsumer(System.getProperty("twitterConsumerKey"), "[consumer secret]");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        if (System.getProperty("twitter4j.oauth.accessToken") == null 
                || System.getProperty("twitter4j.oauth.accessTokenSecret") == null) {
            
            RequestToken requestToken = twitter.getOAuthRequestToken();
            AccessToken accessToken = null;        
            while (null == accessToken) {
                System.out.println("Open the following URL and grant access to your account:");
                System.out.println(requestToken.getAuthorizationURL());
                System.out.println("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
                String pin = br.readLine();
                try {
                    if (pin.length() > 0) {
                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                    } else {
                        accessToken = twitter.getOAuthAccessToken();
                    }
                    System.out.println("Token: " + accessToken.getToken());
                    System.out.println("Token secret: " + accessToken.getTokenSecret());

                } catch (TwitterException te) {
                    if (401 == te.getStatusCode()) {
                        System.out.println("Unable to get the access token.");
                    } else {
                        te.printStackTrace();
                    }
                }
            }
        }
        System.out.println("Your tweet:");
        String tweet = br.readLine();
        Status status = twitter.updateStatus(tweet);
        System.out.println("Successfully updated the status to [" + status.getText() + "].");
    }
}
