package de.jambonna.lolpapers.web.model.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Main object stored in session
 */
public class UserSession implements Serializable {
    private Long userId;
    
    private String urlAfterLogin;

    private TwitterSigninData twitterSigninData;
    
    private List<Message> messages;
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUrlAfterLogin() {
        return urlAfterLogin;
    }

    public void setUrlAfterLogin(String urlAfterLogin) {
        this.urlAfterLogin = urlAfterLogin;
    }

    public TwitterSigninData getTwitterSigninData() {
        return twitterSigninData;
    }

    public void setTwitterSigninData(TwitterSigninData twitterSigninData) {
        this.twitterSigninData = twitterSigninData;
    }
    
    
    public void addInfoTextMessage(String message) {
        addMessage(new Message(message, Message.TYPE_INFO, false));
    }
    public void addInfoHtmlMessage(String message) {
        addMessage(new Message(message, Message.TYPE_INFO, true));
    }
    public void addErrorTextMessage(String message) {
        addMessage(new Message(message, Message.TYPE_ERROR, false));
    }
    public void addErrorHtmlMessage(String message) {
        addMessage(new Message(message, Message.TYPE_ERROR, true));
    }
    
    public synchronized void addMessage(Message message) {
        if (messages == null) {
            messages = new LinkedList<>();
        }
        messages.add(message);
    }
    
    public synchronized List<Message> getMessages(boolean consume) {
        if (messages == null) {
            return Collections.emptyList();
        }
        
        List<Message> listCopy = new ArrayList<>(messages);
        if (consume) {
            messages = null;
        }
        return listCopy;
    }

    public List<Message> getMessages() {
        return getMessages(true);
    }
    
}
