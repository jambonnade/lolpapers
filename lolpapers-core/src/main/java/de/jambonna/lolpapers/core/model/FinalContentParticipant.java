package de.jambonna.lolpapers.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * A User that took part in the making of a final content
 */
@Entity
public class FinalContentParticipant {
    @Id
    private Long finalContentParticipantId;
    
    @ManyToOne
    @JoinColumn(name = "finalContentId")    
    private FinalContent finalContent;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
        
    private boolean initiator;
    private int placeholderCount;
    private int replacementCount;

    public Long getFinalContentParticipantId() {
        return finalContentParticipantId;
    }

    public void setFinalContentParticipantId(Long finalContentParticipantId) {
        this.finalContentParticipantId = finalContentParticipantId;
    }

    public FinalContent getFinalContent() {
        return finalContent;
    }

    public void setFinalContent(FinalContent finalContent) {
        this.finalContent = finalContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isInitiator() {
        return initiator;
    }

    public void setInitiator(boolean initiator) {
        this.initiator = initiator;
    }
    
    public int getPlaceholderCount() {
        return placeholderCount;
    }

    public void setPlaceholderCount(int placeholderCount) {
        this.placeholderCount = placeholderCount;
    }
    
    public void incPlaceholderCount() {
        placeholderCount++;
    }

    public int getReplacementCount() {
        return replacementCount;
    }

    public void setReplacementCount(int replacementCount) {
        this.replacementCount = replacementCount;
    }
    
    public void incReplacementCount() {
        replacementCount++;
    }
}
