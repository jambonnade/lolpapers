package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.app.App;
import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.lang.Languages;
import de.jambonna.lolpapers.core.persistence.MainDao;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for updating base articles from news feed sources
 */
public class ContentUpdate {
    private static final Logger logger = LoggerFactory.getLogger(ContentUpdate.class);

    private App app;
    private MainDao mainDao;
    private Languages languages;

    
    public App getApp() {
        if (app == null) {
            throw new IllegalStateException("No app");
        }
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public MainDao getMainDao() {
        if (mainDao == null) {
            throw new IllegalStateException("No dao");
        }
        return mainDao;
    }

    public void setMainDao(MainDao mainDao) {
        this.mainDao = mainDao;
    }

    public Languages getLanguages() {
        if (languages == null) {
            languages = new Languages();
        }
        return languages;
    }
    
    protected List<NewsFeed> getAllNewsFeeds() throws AppException {
        List<NewsFeed> allNf = getMainDao().getAllNewsFeeds();
        // Force load existing feed entries to avoid lazy loading errors
        for (NewsFeed nf : allNf) {
            nf.getItems();
        }
        return allNf;
    }
    
    public void updateNewsFeeds() throws AppException {
        List<NewsFeed> allNf = getAllNewsFeeds();
        
        for (NewsFeed nf : allNf) {
            logger.info("Updating feed \"{}\" ({})...", 
                    nf.getTitle(), nf.getNewsFeedId());
            try {
                nf.update();
                getMainDao().saveNewsFeed(nf);
            } catch (Exception e) {
                logger.error("Error while updating", e);
            }
        }
    }
    
    public void createNewsFeedArticles() throws AppException {
        List<NewsFeed> allNf = getAllNewsFeeds();

        for (NewsFeed nf : allNf) {
            logger.info("Creating articles for feed \"{}\" ({})...", 
                    nf.getTitle(), nf.getNewsFeedId());
            try {
                createArticles(nf);
            } catch (Exception e) {
                logger.error("Error during article creation", e);
            }
        }
    }
    
    
    public void createArticles(NewsFeed nf) throws AppException {
        int nb = 0;
        for (NewsFeedItem nfi : nf.getItems()) {
            createArticle(nfi);
            nb++;
//            if (nb >= 3) {
//                break;
//            }
        }
    }
    
    public void createArticle(NewsFeedItem nfi) throws AppException {
        String identifier = nfi.getArticleIdentifier();

        BaseArticle ba = getMainDao().findBaseArticleByIdentifier(identifier);
        if (ba == null) {
            logger.info("Creating article {}", identifier);
            ba = new BaseArticle();
            ba.init(getApp());
            ba.setLanguages(getLanguages());
            ba.setCreatedAt(new Date());
            ba.setIdentifier(identifier);
            try {
                ba.initFromNewsFeedItem(nfi);
                ba.prepare();
                getMainDao().saveBaseArticle(ba);
            } catch (Exception e) {
                logger.error("Error processing the article", e);
                
                // Make an empty article with this identifier to not try again.
                // Hoping the error is not in setting the identifier or in
                // the persisting operation
                ba = new BaseArticle();
                ba.setCreatedAt(new Date());
                ba.setIdentifier(identifier);
                ba.setStatus(BaseContent.Status.ON_ERROR);
                getMainDao().saveBaseArticle(ba);
            }
        } else {
            logger.info("Article {} already exists", identifier);
        }
    }
    
    public void outdateArticles() throws AppException {
        logger.info("Outdating articles...");

        List<NewsFeed> allNf = getAllNewsFeeds();
        
        List<BaseArticle> availableArticles = getMainDao().getAvailableBaseArticles(null);
        for (BaseArticle ba : availableArticles) {
            boolean found = false;
            for (NewsFeed nf : allNf) {
                for (NewsFeedItem nfi : nf.getItems()) {
                    if (!found && nfi.getArticleIdentifier().equals(ba.getIdentifier())) {
                        found = true;
                    }
                }
            }
            if (!found) {
                logger.info("Outdating article {} ({})", 
                        ba.getIdentifier(), ba.getBaseContentId());
                try {
                    ba.setStatus(BaseContent.Status.OUTDATED);
                    getMainDao().saveBaseArticle(ba);
                } catch (Exception e) {
                    logger.error("Error updating article", e);
                }
            }
        }
    }
}
