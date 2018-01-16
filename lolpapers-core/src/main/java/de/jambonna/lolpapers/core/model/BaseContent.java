package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.model.lang.Language;
import de.jambonna.lolpapers.core.model.lang.Languages;
import de.jambonna.lolpapers.core.validation.BeanValidator;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Base content entity type. Base content entities are used to create 
 * ContentTemplate entities
 */
@Entity
public abstract class BaseContent extends ModelAbstract {
    
    @Id
    private Long baseContentId;

    @Version
    private Long versionNumber;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @NotNull
    @Size(max = 250)
    private String identifier;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
//    @ManyToOne
//    @JoinColumn(name = "takenByUserId")
//    private User takenByUser;
    
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Transient
    private Languages languages;
    
    @Transient
    private boolean toSave;
    
//    @ManyToMany
//    @JoinTable(
//        name = "BaseContentCategory",
//        joinColumns = @JoinColumn(name = "baseContentId"),
//        inverseJoinColumns = @JoinColumn(name = "categoryId")
//    )   
//    private List<Category> categories = new ArrayList<>();


    
    
    public Long getBaseContentId() {
        return baseContentId;
    }

    public void setBaseContentId(Long baseContentId) {
        this.baseContentId = baseContentId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Locale getLocale() {
        Category cat = getCategory();
        if (cat == null || cat.getWebsite() == null) {
            throw new IllegalStateException("BaseContent : no website");
        }
        return cat.getWebsite().getLocale();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        BeanValidator.getInstance().validatePropertyValue(BaseContent.class, "identifier", identifier);
        this.identifier = identifier;
    }    

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public void take() {
        if (getStatus() != Status.AVAILABLE) {
            throw new IllegalStateException("Unavailable base content");
        }
        setStatus(Status.TAKEN);
    }

//    public User getTakenByUser() {
//        return takenByUser;
//    }
//
//    public void setTakenByUser(User takenByUser) {
//        this.takenByUser = takenByUser;
//    }
//    
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Languages getLanguages() {
        if (languages == null) {
            languages = new Languages();
        }
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
    }
    
    public Language getLanguage() {
        return getLanguages().getByLocale(getLocale());
    }

    public boolean isToSave() {
        return toSave;
    }

    
    public void setToSave(boolean toSave) {
        this.toSave = toSave;
    }
    
    
    
    
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        if (getStatus() != Status.AVAILABLE) {
//            throw new IllegalStateException(
//                    "Unable to attach user to content : invalid status");
//        }
//        this.user = user;
//        setStatus(Status.TAKEN);
//    }
    
//    public List<Category> getCategories() {
//        return categories;
//    }

    
//    public static <T extends BaseContent> T getBaseContentFromIdentifier(
//            EntityManager em, Class<T> cls, String identifier) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        
//        CriteriaQuery<T> cq = cb.createQuery(cls);
//        Root<T> root = cq.from(cls);
//        cq.select(root);
//        cq.where(cb.equal(root.get(BaseContent_.identifier), identifier));                
//        TypedQuery<T> q = em.createQuery(cq);
//        List<T> items = q.getResultList();
//        for (T item: items) {
//            return item;
//        }
//        return null;
//    }
    
    public static enum Status {
        AVAILABLE,
        TAKEN,
        REJECTED,
        OUTDATED,
        ON_ERROR
    };

}
