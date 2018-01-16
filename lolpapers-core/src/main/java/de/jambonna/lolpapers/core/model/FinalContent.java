package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.app.App;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Abstract final content entity type. Will appear publicly in the website
 */
@Entity
public abstract class FinalContent extends ModelAbstract {

    
    @Id
    private Long finalContentId;
    
    @ManyToOne
    @JoinColumn(name = "baseContentId")
    private BaseContent baseContent;

    @ManyToOne
    @JoinColumn(name = "contentTemplateId")
    private ContentTemplate contentTemplate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToMany
    @JoinTable(
        name = "FinalContentUpvote",
        joinColumns = @JoinColumn(name = "finalContentId"),
        inverseJoinColumns = @JoinColumn(name = "userId")
    )   
    private Set<User> upvoteUsers = new HashSet<>();
    private int upvotes;
    
    @OneToMany(mappedBy = "finalContent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinalContentParticipant> participants = new ArrayList<>();

    
    @Override
    public void init(App app) {
        super.init(app);
        if (getBaseContent() != null) {
            getBaseContent().init(app);
        }
    }

    public Long getFinalContentId() {
        return finalContentId;
    }

    public void setFinalContentId(Long finalContentId) {
        this.finalContentId = finalContentId;
    }

    public BaseContent getBaseContent() {
        return baseContent;
    }

    public void setBaseContent(BaseContent baseContent) {
        this.baseContent = baseContent;
    }

    public ContentTemplate getContentTemplate() {
        return contentTemplate;
    }

    public void setContentTemplate(ContentTemplate contentTemplate) {
        this.contentTemplate = contentTemplate;
        setBaseContent(contentTemplate.getBaseContent());
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<User> getUpvoteUsers() {
        return upvoteUsers;
    }

    public void setUpvoteUsers(Set<User> upvoteUsers) {
        this.upvoteUsers = upvoteUsers;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }
    
    public void updateUpvotes() {
        Set<User> users = getUpvoteUsers();
        setUpvotes(users != null ? users.size() : 0);
    }
    
    
    public List<FinalContentParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<FinalContentParticipant> participants) {
        this.participants = participants;
    }
    
    public FinalContentParticipant getParticipant(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Invalid user");
        }
        for (FinalContentParticipant p : getParticipants()) {
            if (user.equals(p.getUser())) {
                return p;
            }
        }
        FinalContentParticipant p = new FinalContentParticipant();
        p.setFinalContent(this);
        p.setUser(user);
        getParticipants().add(p);
        return p;
    }
}
