package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.app.App;
import de.jambonna.lolpapers.core.model.lang.Language;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.jsoup.nodes.Element;

/**
 * Abstract content template entity type. Content templates hold a list of 
 * placeholders and other informations to create a final content entity.
 */
@Entity
public abstract class ContentTemplate extends ModelAbstract {
    
    
    @Id
    private Long contentTemplateId;
    
    @Version
    private Long versionNumber;
    
    @ManyToOne
    @JoinColumn(name = "baseContentId")
    private BaseContent baseContent;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSaveAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishedAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;
    
    @OneToMany(mappedBy = "contentTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("fromWord")
    private List<TemplatePlaceholder> placeholders = new ArrayList<>();

    @Transient
    private boolean toSave;
    
    @Transient
    private boolean toRemove;

//    @Transient
//    private boolean baseContentToSave;

//    @Transient
//    private boolean usersToSave;

    
    
    
    @Override
    public void init(App app) {
        super.init(app);
        if (getBaseContent() != null) {
            getBaseContent().init(app);
        }
    }
    

    public Long getContentTemplateId() {
        return contentTemplateId;
    }

    public void setContentTemplateId(Long contentTemplateId) {
        this.contentTemplateId = contentTemplateId;
    }
    
    public Long getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    public BaseContent getBaseContent() {
        return baseContent;
    }

    public void setBaseContent(BaseContent baseContent) {
        this.baseContent = baseContent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLastSaveAt() {
        return lastSaveAt;
    }

    public void setLastSaveAt(Date lastSaveAt) {
        this.lastSaveAt = lastSaveAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }
    
    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }
    
    public boolean isCompleted() {
        return getCompletedAt() != null;
    }
    

    public List<TemplatePlaceholder> getPlaceholders() {
        return placeholders;
    }
    
    public TemplatePlaceholder getPlaceholder(Long id) {
        for (TemplatePlaceholder p : placeholders) {
            if (id != null && id.equals(p.getTemplatePlaceholderId())) {
                return p;
            }
        }
        return null;
    }
    
//    public TemplatePlaceholder getPlaceholder(String reference) {
//        for (TemplatePlaceholder p : placeholders) {
//            if (reference != null && reference.equals(p.getReference())) {
//                return p;
//            }
//        }
//        return null;
//    }
//    
    public void validatePlaceholderReferences() throws UserException {
        Set<String> references = new HashSet<>(placeholders.size());
        for (TemplatePlaceholder p : placeholders) {
            if (p.getReference() == null) {
                throw new UserException("tp-without-ref", "Placeholder has no ref");
            }
            if (!references.add(p.getReference())) {
                throw new UserException("tp-not-unique-ref", 
                        "Placeholders have non unique refs");
            }
        }
        for (TemplatePlaceholder p : placeholders) {
            String plRef = p.getUsePlaceholder();
            if (plRef != null) {
                if (plRef.equals(p.getReference()) || !references.contains(plRef)) {
                    throw new UserException("tp-invalid-use-ref", 
                            "Invalid referenced placeholder");
                }
            }
        }
    }
    
    public boolean areAllPlaceholdersFilled() {
        for (TemplatePlaceholder tp : placeholders) {
            if (tp.isReplacementNeeded() && !tp.isReplacementFilled()) {
                return false;
            }
        }
        return true;
    }
    
    public Category getCategory() {
        BaseContent bc = getBaseContent();
        if (bc == null) {
            throw new IllegalStateException("No base content");
        }
        Category cat = bc.getCategory();
        if (cat == null) {
            throw new IllegalStateException("No category");
        }
        return cat;
    }
    
    public WebsiteEntity getWebsite() {
        WebsiteEntity w = getCategory().getWebsite();
        if (w == null) {
            throw new IllegalStateException("No website");
        }
        return w;
    }

    public Language getLanguage() {
        BaseContent bc = getBaseContent();
        if (bc == null) {
            throw new IllegalStateException("No base content");
        }
        return bc.getLanguage();
    }
    
    public void incUsersPlaceholderCount() {
        for (TemplatePlaceholder tp : getPlaceholders()) {
            if (tp.isReplacementNeeded()) {
                User u = tp.getCreatedBy();
                u.incPlaceholderCount();
                u.setToSave(true);
            }
        }
    }
    
    public Set<User> getAllUsersInvolved() {
        Set<User> result = new HashSet<>(1);
        if (getUser() != null) {
            result.add(getUser());
        }
        for (TemplatePlaceholder tp : getPlaceholders()) {
            result.add(tp.getCreatedBy());
        }
        return result;
    }

    public List<Element> getActualContentElements() {
        return Collections.emptyList();
    }
    
    public boolean isToSave() {
        return toSave;
    }

    public void setToSave(boolean toSave) {
        this.toSave = toSave;
    }
    
    public boolean isToRemove() {
        return toRemove;
    }

    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }
    
    
//    public boolean isBaseContentToSave() {
//        return baseContentToSave;
//    }
//
//    public void setBaseContentToSave(boolean baseContentToSave) {
//        this.baseContentToSave = baseContentToSave;
//    }

//    public boolean isUsersToSave() {
//        return usersToSave;
//    }
//
//    public void setUsersToSave(boolean usersToSave) {
//        this.usersToSave = usersToSave;
//    }
//    
}
