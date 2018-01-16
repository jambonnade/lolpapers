package de.jambonna.lolpapers.core.persistence;

import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.ArticleTemplate;
import de.jambonna.lolpapers.core.model.BaseArticle;
import de.jambonna.lolpapers.core.model.BaseArticle_;
import de.jambonna.lolpapers.core.model.BaseContent;
import de.jambonna.lolpapers.core.model.BaseContent_;
import de.jambonna.lolpapers.core.model.Category;
import de.jambonna.lolpapers.core.model.Category_;
import de.jambonna.lolpapers.core.model.ContentTemplate;
import de.jambonna.lolpapers.core.model.ContentTemplate_;
import de.jambonna.lolpapers.core.model.FinalArticle;
import de.jambonna.lolpapers.core.model.FinalContentParticipant;
import de.jambonna.lolpapers.core.model.NewsFeed;
import de.jambonna.lolpapers.core.model.Url;
import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.core.model.WebsiteEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import de.jambonna.lolpapers.core.model.EntityUrlUpdater;
import de.jambonna.lolpapers.core.model.FinalContent_;
import de.jambonna.lolpapers.core.model.ModelAbstract;
import de.jambonna.lolpapers.core.model.TemplatePlaceholder;
import de.jambonna.lolpapers.core.model.TemplatePlaceholder_;
import de.jambonna.lolpapers.core.model.Url_;
import de.jambonna.lolpapers.core.model.User_;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 */
public abstract class MainDaoAbstract extends DaoAbstract implements MainDao {

    @Override
    public List<Url> getAllUrls() throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Url> cq = cb.createQuery(Url.class);
            Root<Url> root = cq.from(Url.class);
            cq.select(root);
            TypedQuery<Url> q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            throw new AppException("Unable to load urls", e);
        }
    }

    @Override
    public void updateUrlsFor(Url url) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            em.getTransaction().begin();
            List<Url> urls = updateUrlsFor(em, url);
            em.getTransaction().commit();
            
            getApp().getSharedData().updateUrls(urls);
        } catch (Exception e) {
            silentlyRollbackEntityManager(em);
            throw new AppException("Unable to save urls", e);
        }
    }
    

    @Override
    public WebsiteEntity findWebsite(Long id) throws AppException {
        return basicLoadEntity(WebsiteEntity.class, id);
    }    

    @Override
    public void saveWebsite(WebsiteEntity website) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(website);
            List<Url> urls = updateUrlsFor(em, website.getUrlToGenerate());
            em.getTransaction().commit();
            
            getApp().getSharedData().updateUrls(urls);
        } catch (Exception e) {
            silentlyRollbackEntityManager(em);
            throw new AppException("Unable to save website", e);
        }
        
    }

    @Override
    public List<Category> getAllCategories() throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Category> cq = cb.createQuery(Category.class);
            Root<Category> root = cq.from(Category.class);
            cq.select(root);
            cq.orderBy(
                    cb.asc(root.get(Category_.position)),
                    cb.asc(root.get(Category_.categoryId)));
            TypedQuery<Category> q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            throw new AppException("Unable to load categories", e);
        }
    }

    @Override
    public Category findCategory(Long id) throws AppException {
        return basicLoadEntity(Category.class, id);
    }
    
    @Override
    public void saveCategory(Category category) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(category);
            List<Url> urls = updateUrlsFor(em, category.getUrlToGenerate());
            em.getTransaction().commit();
            
            getApp().getSharedData().updateUrls(urls);
        } catch (Exception e) {
            silentlyRollbackEntityManager(em);
            throw new AppException("Unable to save category", e);
        }
    }

    @Override
    public List<NewsFeed> getAllNewsFeeds() throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<NewsFeed> cq = cb.createQuery(NewsFeed.class);
            Root<NewsFeed> root = cq.from(NewsFeed.class);
            cq.select(root);
            TypedQuery<NewsFeed> q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            throw new AppException("Unable to load newsfeeds", e);
        }
    }
    
    @Override
    public void saveNewsFeed(NewsFeed newsFeed) throws AppException {
        basicSaveEntity(newsFeed);
    }
    


    @Override
    public ArticleTemplate findArticleTemplate(Long id) throws AppException {
        return basicLoadEntity(ArticleTemplate.class, id);
    }

    @Override
    public List<ArticleTemplate> getUserUnfinishedArticleTemplates(User user) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ArticleTemplate> cq = cb.createQuery(ArticleTemplate.class);
            Root<ArticleTemplate> root = cq.from(ArticleTemplate.class);
            cq.select(root);
                        
            cq.where(cb.isNull(root.get(ContentTemplate_.finishedAt)),
                    cb.equal(root.get(ContentTemplate_.user), user));

            cq.orderBy(cb.asc(root.get(ContentTemplate_.createdAt)));
            
            TypedQuery<ArticleTemplate> q = em.createQuery(cq);
            List<ArticleTemplate> items = q.getResultList();
            initEntities(items);
            return items;
        } catch (Exception e) {
            throw new AppException("Unable to get unfinished article templates", e);
        }
    }
    
    @Override
    public void saveArticleTemplate(ArticleTemplate articleTemplate) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            em.getTransaction().begin();
            
            BaseContent bc = articleTemplate.getBaseContent();
            if (bc.isToSave()) {
                em.persist(bc);
            }
            
            Set<User> users = articleTemplate.getAllUsersInvolved();
            for (User u : users) {
                if (u.isToSave()) {
                    em.persist(u);
                }
            }
            if (articleTemplate.isToRemove()) {
                em.remove(articleTemplate);
            } else {
                em.persist(articleTemplate);
            }
            em.getTransaction().commit();
            
            bc.setToSave(false);
            for (User u : users) {
                u.setToSave(false);
            }
        } catch (Exception e) {
            silentlyRollbackEntityManager(em);
            throw new AppException("Unable to save article template", e);
        }
    }

    @Override
    public BaseArticle findBaseArticle(Long id) throws AppException {
        return basicLoadEntity(BaseArticle.class, id);
    }    
    
    @Override
    public BaseArticle findBaseArticleByIdentifier(String identifier) throws AppException {
        return basicLoadEntityByField(BaseArticle.class, BaseContent_.identifier, identifier);
    }
    
    @Override
    public BaseArticle findBaseArticleByCanonicalUrl(String url) throws AppException {
        return basicLoadEntityByField(BaseArticle.class, BaseArticle_.articleCanonicalUrl, url);
    }

    @Override
    public List<BaseArticle> getAvailableBaseArticles(WebsiteEntity website) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<BaseArticle> cq = cb.createQuery(BaseArticle.class);
            Root<BaseArticle> root = cq.from(BaseArticle.class);
            cq.select(root);

            Predicate statusFilter = 
                    cb.equal(root.get(BaseContent_.status), BaseContent.Status.AVAILABLE);
            
            if (website == null) {
                cq.where(statusFilter);
            } else {
                Join<BaseArticle, Category> cat = root.join(BaseContent_.category);
                cq.where(
                    statusFilter,
                    cb.equal(cat.get(Category_.website), website));
            }

            TypedQuery<BaseArticle> q = em.createQuery(cq);
            List<BaseArticle> items = q.getResultList();
            initEntities(items);
            return items;
        } catch (Exception e) {
            throw new AppException("Unable to get random base article", e);
        }
    }
    
    
    @Override
    public void saveBaseArticle(BaseArticle baseArticle) throws AppException {
        basicSaveEntity(baseArticle);
    }
    
    
    @Override
    public TemplatePlaceholder findTemplatePlaceholder(Long id) throws AppException {
        return basicLoadEntity(TemplatePlaceholder.class, id);
    }

    @Override
    public List<TemplatePlaceholder> getOrderedUnfilledTemplatePlaceholders(WebsiteEntity website) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<TemplatePlaceholder> cq = cb.createQuery(TemplatePlaceholder.class);
            Root<TemplatePlaceholder> root = cq.from(TemplatePlaceholder.class);
            cq.select(root);
            
            Join<TemplatePlaceholder, ContentTemplate> tc = root.join(TemplatePlaceholder_.contentTemplate);
            Join<ContentTemplate, BaseContent> bc = tc.join(ContentTemplate_.baseContent);
            Join<BaseContent, Category> cat = bc.join(BaseContent_.category);
            
            cq.where(cb.isNull(root.get(TemplatePlaceholder_.replacementAt)),
                    cb.isNull(root.get(TemplatePlaceholder_.usePlaceholder)),
                    cb.isNotNull(tc.get(ContentTemplate_.finishedAt)),
                    cb.equal(cat.get(Category_.website), website));

            Join<TemplatePlaceholder, ContentTemplate> ct = 
                    root.join(TemplatePlaceholder_.contentTemplate);

            cq.orderBy(
                cb.asc(ct.get(ContentTemplate_.createdAt)),
                cb.asc(root.get(TemplatePlaceholder_.fromWord)));
            
            TypedQuery<TemplatePlaceholder> q = em.createQuery(cq);
            List<TemplatePlaceholder> items = q.getResultList();
            initEntities(items);
            return items;
        } catch (Exception e) {
            throw new AppException("Unable to get unfilled template placeholders", e);
        }
    }

    @Override
    public void saveTemplatePlaceholder(TemplatePlaceholder templatePlaceholder) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            em.getTransaction().begin();
            
            em.persist(templatePlaceholder);
            
            User replacementUser = templatePlaceholder.getReplacementBy();
            if (replacementUser != null && replacementUser.isToSave()) {
                em.persist(replacementUser);
            }
            
            em.getTransaction().commit();
            
            if (replacementUser != null) {
                replacementUser.setToSave(false);
            }
            
        } catch (Exception e) {
            silentlyRollbackEntityManager(em);
            throw new AppException("Unable to save template placeholder", e);
        }
    }

    @Override
    public List<FinalArticle> getLastArticles(WebsiteEntity website, Category category, int maxNb) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<FinalArticle> cq = cb.createQuery(FinalArticle.class);
            Root<FinalArticle> root = cq.from(FinalArticle.class);
            cq.select(root);
            
            Join<FinalArticle, BaseContent> bc = root.join(FinalContent_.baseContent);
            
            Predicate categoryFilter = null;
            if (category != null) {
                categoryFilter = cb.equal(bc.get(BaseContent_.category), category);
            } else {
                if (website == null) {
                    throw new IllegalArgumentException("Provide website or category filter");
                }
                Join<BaseContent, Category> cat = bc.join(BaseContent_.category);
                categoryFilter = cb.equal(cat.get(Category_.website), website);
            }
            
            cq.where(categoryFilter);

            cq.orderBy(cb.desc(root.get(FinalContent_.createdAt)));
            
            TypedQuery<FinalArticle> q = em.createQuery(cq);
            q.setMaxResults(maxNb);
            List<FinalArticle> items = q.getResultList();
            initEntities(items);
            return items;
        } catch (Exception e) {
            throw new AppException("Unable to get top articles", e);
        }
    }

    @Override
    public List<FinalArticle> getTopArticles(WebsiteEntity website, Category category, int maxNb, Date minDate) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<FinalArticle> cq = cb.createQuery(FinalArticle.class);
            Root<FinalArticle> root = cq.from(FinalArticle.class);
            cq.select(root);
            
            Join<FinalArticle, BaseContent> bc = root.join(FinalContent_.baseContent);
            
            Predicate categoryFilter = null;
            if (category != null) {
                categoryFilter = cb.equal(bc.get(BaseContent_.category), category);
            } else {
                if (website == null) {
                    throw new IllegalArgumentException("Provide website or category filter");
                }
                Join<BaseContent, Category> cat = bc.join(BaseContent_.category);
                categoryFilter = cb.equal(cat.get(Category_.website), website);
            }
            
            if (minDate != null) {
                cq.where(categoryFilter, cb.greaterThan(root.get(FinalContent_.createdAt), minDate));
            } else {
                cq.where(categoryFilter);
            }


            cq.orderBy(
                cb.desc(root.get(FinalContent_.upvotes)),
                cb.desc(root.get(FinalContent_.createdAt)));
            
            TypedQuery<FinalArticle> q = em.createQuery(cq);
            q.setMaxResults(maxNb);
            List<FinalArticle> items = q.getResultList();
            initEntities(items);
            return items;
        } catch (Exception e) {
            throw new AppException("Unable to get top articles", e);
        }
    }

    @Override
    public FinalArticle findFinalArticle(Long id) throws AppException {
        return basicLoadEntity(FinalArticle.class, id);
    }
    

    

    
    @Override
    public void saveFinalArticle(FinalArticle finalArticle) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            em.getTransaction().begin();
            
            checkUpdateEntityUrl(em, finalArticle.getArticleUrlUpdater());
            
            em.persist(finalArticle);
            
            ContentTemplate t = finalArticle.getContentTemplate();
            if (t.isToSave()) {
                em.persist(t);
            }
            List<FinalContentParticipant> participants = finalArticle.getParticipants();
            for (FinalContentParticipant p : participants) {
                if (p.getUser().isToSave()) {
                    em.persist(p.getUser());
                }
            }
            List<Url> urls = updateUrlsFor(em, finalArticle.getArticleUrl());
            
            em.getTransaction().commit();
            
            t.setToSave(false);
            for (FinalContentParticipant p : participants) {
                p.getUser().setToSave(false);
            }
            
            getApp().getSharedData().updateUrls(urls);
        } catch (Exception e) {
            silentlyRollbackEntityManager(em);
            throw new AppException("Unable to save final article", e);
        }

    }
    

    @Override
    public User findUser(Long id) throws AppException {
        return basicLoadEntity(User.class, id);
    }

    @Override
    public User findUserByTwitterId(Long id) throws AppException {
        return basicLoadEntityByField(User.class, User_.twitterUserId, id);
    }
    

    @Override
    public void saveUser(User user) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            em.getTransaction().begin();
            
            EntityUrlUpdater urlUpdater = null;
            if (user.isWithUrlUpdate()) {
                urlUpdater = user.getProfileUrlUpdater(Arrays.asList(getApp().getSharedData().getWebsites()));
                checkUpdateEntityUrl(em, urlUpdater);
            }
            
            em.persist(user);

            List<Url> urls = null;
            if (urlUpdater != null) {
                urls = updateUrlsFor(em, urlUpdater.getUrls());
            }
            
            em.getTransaction().commit();
            
            if (urls != null) {
                getApp().getSharedData().updateUrls(urls);
            }
            user.setWithUrlUpdate(false);
        } catch (Exception e) {
            silentlyRollbackEntityManager(em);
            throw new AppException("Unable to save user", e);
        }
    }
    
    
    protected <T extends ModelAbstract> T basicLoadEntity(Class<T> cls, Long id) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            T result = em.find(cls, id);
            if (result != null) {
                result.init(getApp());
            }
            return result;
        } catch (Exception e) {
            throw new AppException("Unable to load entity " + cls.getSimpleName(), e);
        }
    }

    protected <T extends ModelAbstract, V> T basicLoadEntityByField(
            Class<T> cls, SingularAttribute<? super T, V> field, V value) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(cls);
            Root<T> root = cq.from(cls);
            cq.where(cb.equal(root.get(field), value));
            cq.select(root);

            TypedQuery<T> q = em.createQuery(cq);
            List<T> items = q.getResultList();
            if (items.size() == 1) {
                T result = items.get(0);
                result.init(getApp());
                return result;
            }
        } catch (Exception e) {
            throw new AppException("Unable to load entity", e);
        }
        return null;
    }
    
    protected void initEntities(Collection<? extends ModelAbstract> entities) {
        for (ModelAbstract e : entities) {
            e.init(getApp());
        }
    }
    
    protected void basicSaveEntity(Object entity) throws AppException {
        EntityManager em = getDedicatedEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            silentlyRollbackEntityManager(em);
            throw new AppException("Unable to save entity " + entity.getClass().getSimpleName(), e);
        }
    }
    
    protected void checkUpdateEntityUrl(EntityManager em, EntityUrlUpdater updater) throws Exception {
        boolean continueCheck = true;
        while (continueCheck) {
            continueCheck = false;
            List<Url> urls = updater.getUrls();
            for (Url url : urls) {
                String identifier = url.getIdentifier();
                String requestPath = url.getRequestPath();

                if (requestPath == null) {
                    throw new IllegalArgumentException("Invalid url to check");
                }
                
                getLogger().debug("Checking if request path '{}' exists", requestPath);
                Url existingUrl = findUrlForRequestPath(em, requestPath);
                
                // If request path exists for something else
                if (existingUrl != null 
                        && (identifier == null 
                            || !identifier.equals(existingUrl.getIdentifier()))) {
                    // Updates entity to next base path, stop checking if instructed so
                    if (updater.nextBasePath()) {
                        getLogger().debug("Path existing for something else, trying another base path...");
                        continueCheck = true;
                    } else {
                        getLogger().debug("Stop checking, will fail at save");
                    }
                    break;
                }
            }
        }
    }
    
    protected Url findUrlForRequestPath(EntityManager em, String requestPath) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Url> cq = cb.createQuery(Url.class);
        Root<Url> root = cq.from(Url.class);
        cq.where(cb.equal(root.get(Url_.requestPath), requestPath));

        cq.select(root);

        TypedQuery<Url> q = em.createQuery(cq);
        List<Url> urls = q.getResultList();
        if (urls.size() == 1) {
            return urls.get(0);
        }
        return null;
    }


    protected List<Url> updateUrlsFor(EntityManager em, List<Url> inputUrls) throws Exception {
        List<Url> urls = new ArrayList<>();
        for (Url url : inputUrls) {
            urls.addAll(updateUrlsFor(em, url));
        }
        return urls;
    }
        
    protected List<Url> updateUrlsFor(EntityManager em, Url inputUrl) throws Exception {
        
        String identifier = inputUrl.getIdentifier();
        String requestPath = inputUrl.getRequestPath();
        String altRequestPath = inputUrl.getAltRequestPath();
        String destPath = inputUrl.getDestPath();
        if (identifier == null || requestPath == null || destPath == null || inputUrl.isHistory()) {
            throw new IllegalArgumentException("Invalid input url");
        }
        
        getLogger().debug("Update urls for url {} / '{}' / '{}'", identifier, requestPath, destPath);

        List<Url> urls = new ArrayList<>(findUrlsForIdentifier(em, identifier));

        boolean foundCurrent = false;
        boolean foundAlt = false;
        for (Url url : urls) {
            if (requestPath.equals(url.getRequestPath())) {
                url.setDestPath(destPath);                
                url.setHistory(false);
                foundCurrent = true;
            } else {
                url.setDestPath(requestPath);
                url.setHistory(true);
            }

            if (altRequestPath != null && altRequestPath.equals(url.getRequestPath())) {
                foundAlt = true;
            }
            
            persistUrl(em, url);
        }
        if (altRequestPath != null && !foundAlt) {
            Url altUrl = new Url();
            altUrl.setIdentifier(identifier);
            altUrl.setRequestPath(altRequestPath);
            altUrl.setDestPath(requestPath);
            altUrl.setHistory(true);
            urls.add(altUrl);
            persistUrl(em, altUrl);
        }
        if (!foundCurrent) {
            Url currentUrl = new Url();
            currentUrl.setIdentifier(identifier);
            currentUrl.setRequestPath(requestPath);
            currentUrl.setDestPath(destPath);
            urls.add(currentUrl);
            persistUrl(em, currentUrl);
        }
        return urls;
    }
    
    protected void persistUrl(EntityManager em, Url url) {
        getLogger().debug("Saving url : identifier {}, id {}, path '{}', dest '{}', history {}", 
                url.getIdentifier(), url.getUrlId(), url.getRequestPath(), 
                url.getDestPath(), url.isHistory());
        em.persist(url);
    }

    protected List<Url> findUrlsForIdentifier(EntityManager em, String identifier) throws Exception {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Url> cq = cb.createQuery(Url.class);
        Root<Url> root = cq.from(Url.class);
        cq.where(cb.equal(root.get(Url_.identifier), identifier));
        cq.select(root);

        TypedQuery<Url> q = em.createQuery(cq);
        return q.getResultList();
    }
    


}
