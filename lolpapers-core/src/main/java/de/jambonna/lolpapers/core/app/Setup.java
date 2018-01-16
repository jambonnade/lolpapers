package de.jambonna.lolpapers.core.app;

import de.jambonna.lolpapers.core.model.Category;
import de.jambonna.lolpapers.core.model.NewsFeed;
import de.jambonna.lolpapers.core.model.Urls;
import de.jambonna.lolpapers.core.model.WebsiteEntity;
import de.jambonna.lolpapers.core.persistence.MainDao;
import java.util.Date;

/**
 * Responsible for setting up initial data when a fresh new database is detected
 */
public class Setup {
    public void setupInitialData(App app) throws AppException {
        MainDao dao = app.createDaoObject(MainDao.class);
        try {
            doSetupInitialData(app, dao);
        } finally {
            dao.close();
        }
    }
    
    protected void doSetupInitialData(App app, MainDao dao) throws AppException {
        
        WebsiteEntity fr = new WebsiteEntity();
        fr.setCode("fr");
        fr.setUrlKey("fr");
        fr.setName("LolPapers FR");
        fr.setPageName("À la une");
        fr.setLocaleCode("fr_FR");
        fr.setDefaultTimezoneCode("Europe/Paris");
        dao.saveWebsite(fr);
        
        Category cActuFr = new Category();
        cActuFr.setWebsite(fr);
        cActuFr.setCode("fr_actu");
        cActuFr.setUrlKey("actu");
        cActuFr.setTitle("Actualités");
        dao.saveCategory(cActuFr);
        
        Category cTechFr = new Category();
        cTechFr.setWebsite(fr);
        cTechFr.setCode("fr_tech");
        cTechFr.setUrlKey("tech");
        cTechFr.setTitle("Tech.");
        dao.saveCategory(cTechFr);
        
        Category cCuisineFr = new Category();
        cCuisineFr.setWebsite(fr);
        cCuisineFr.setCode("fr_cuisine");
        cCuisineFr.setUrlKey("cuisine");
        cCuisineFr.setTitle("Cuisine");
        dao.saveCategory(cCuisineFr);
        
        Category cSportFr = new Category();
        cSportFr.setWebsite(fr);
        cSportFr.setCode("fr_sport");
        cSportFr.setUrlKey("sport");
        cSportFr.setTitle("Sport");
        dao.saveCategory(cSportFr);
        
        Urls urls = Urls.getInstance();
        dao.updateUrlsFor(urls.createWebsiteSubUrl(fr, Urls.USER_CONNECT_URL_ID, "user/connect", "user/connect", null));
        dao.updateUrlsFor(urls.createWebsiteSubUrl(fr, Urls.USER_CONNECT_TWITTER_URL_ID, "user/connect/twitter", "user/connect/twitter", null));
        dao.updateUrlsFor(urls.createWebsiteSubUrl(fr, Urls.USER_DISCONNECT_URL_ID, "user/disconnect", "user/disconnect", null));
        dao.updateUrlsFor(urls.createWebsiteSubUrl(fr, Urls.ARTICLE_TEMPLATE_NEW_URL_ID, "article/template/new", "article/template/new", null));
        dao.updateUrlsFor(urls.createWebsiteSubUrl(fr, Urls.ARTICLE_TEMPLATE_EDIT_URL_ID, "article/template", "article/template", null));
        dao.updateUrlsFor(urls.createWebsiteSubUrl(fr, Urls.FILL_REPLACEMENT_URL_ID, "replacement", "replacement", null));
        dao.updateUrlsFor(urls.createWebsiteSubUrl(fr, Urls.UPVOTE_CONTENT_URL_ID, "upvote", "upvote", null));
        dao.updateUrlsFor(urls.createWebsitePageUrl(fr, "about", "about-fr", "a-propos", null));
        dao.updateUrlsFor(urls.createWebsitePageUrl(fr, "help", "help-fr", "aide", null));
        
        NewsFeed nf;
        nf = new NewsFeed();
        nf.setUrl("http://www.lemonde.fr/rss/une.xml");
        nf.setCategory(cActuFr);
        nf.setCode("lemonde");
        nf.setActive(true);
        nf.setTitle(nf.getCode());
        nf.setUpdatedAt(new Date());
        dao.saveNewsFeed(nf);
        
        nf = new NewsFeed();
        nf.setUrl("http://www.20minutes.fr/feeds/rss-france.xml");
        nf.setCategory(cActuFr);
        nf.setCode("20min");
        nf.setActive(true);
        nf.setTitle(nf.getCode());
        nf.setUpdatedAt(new Date());
        dao.saveNewsFeed(nf);
        
        nf = new NewsFeed();
//        nf.setUrl("http://www.slate.fr/rss.xml");
        nf.setUrl("http://www.huffingtonpost.fr/feeds/index.xml");
        nf.setCategory(cActuFr);
        nf.setCode("lehuff");
        nf.setActive(true);
        nf.setItemLimit(25);
        nf.setTitle(nf.getCode());
        nf.setUpdatedAt(new Date());
        dao.saveNewsFeed(nf);
        
        nf = new NewsFeed();
        nf.setUrl("http://www.clubic.com/articles.rss");
        nf.setCategory(cTechFr);
        nf.setCode("clubic");
        nf.setActive(true);
        nf.setTitle(nf.getCode());
        nf.setUpdatedAt(new Date());
        dao.saveNewsFeed(nf);
        
        nf = new NewsFeed();
//        nf.setUrl("http://feeds.feedburner.com/KorbensBlog-UpgradeYourMind");
        nf.setUrl("http://www.journaldugeek.com/rss.php");
        nf.setCategory(cTechFr);
//        nf.setCode("korben");
        nf.setCode("jdg");
        nf.setActive(true);
        nf.setTitle(nf.getCode());
        nf.setUpdatedAt(new Date());
        dao.saveNewsFeed(nf);
        
        nf = new NewsFeed();
        nf.setUrl("http://www.marmiton.org/rss/recettes_nouveautes.xml.cfm");
        nf.setCategory(cCuisineFr);
        nf.setCode("marmiton");
        nf.setActive(true);
        nf.setTitle(nf.getCode());
        nf.setUpdatedAt(new Date());
        dao.saveNewsFeed(nf);
        
        nf = new NewsFeed();
        nf.setUrl("https://www.lequipe.fr/rss/actu_rss.xml");
        nf.setCategory(cSportFr);
        nf.setCode("lequipe");
        nf.setActive(true);
        nf.setTitle(nf.getCode());
        nf.setUpdatedAt(new Date());
        dao.saveNewsFeed(nf);
    }
}
