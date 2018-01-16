package de.jambonna.lolpapers.web.model.session;

import java.io.Serializable;

/**
 * A message in session for the user
 */
public class Message implements Serializable {
    public static final int TYPE_INFO = 1;
    public static final int TYPE_ERROR = 2;
    
    private final String message;
    private final int type;
    private final boolean html;

    public Message(String message, int type, boolean html) {
        this.message = message;
        this.type = type;
        this.html = html;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }
    
    public boolean isError() {
        return getType() == TYPE_ERROR;
    }

    public boolean isHtml() {
        return html;
    }
}
