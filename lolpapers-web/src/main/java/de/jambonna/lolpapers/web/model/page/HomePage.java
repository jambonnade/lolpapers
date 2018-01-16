package de.jambonna.lolpapers.web.model.page;

import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.FinalArticle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HomePage extends Page {

    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);
    
    private int lastArtNb = 5;
    private List<FinalArticle> lastArticles;
    private int topArtNb = 3;
    private int nbDaysTopArts = 30;
    private List<FinalArticle> topArticles;
    
    @Override
    public String getCurPageTitle() {
        return getWebsite().getPageName();
    }
    
    @Override
    public String getCurPageUrl() {
        // Removes the link in breadcrumbs
        return null;
    }

    
    public int getLastArtNb() {
        return lastArtNb;
    }

    public void setLastArtNb(int lastArtNb) {
        this.lastArtNb = lastArtNb;
    }
    
    public List<FinalArticle> getLastArticles() throws AppException {
        if (lastArticles == null) {
            lastArticles = getRequestContext().getMainDao()
                    .getLastArticles(getWebsiteEntity(), null, getLastArtNb());
        }
        return lastArticles;
    }


    public int getTopArtNb() {
        return topArtNb;
    }

    public void setTopArtNb(int topArtNb) {
        this.topArtNb = topArtNb;
    }

    public int getNbDaysTopArts() {
        return nbDaysTopArts;
    }

    public void setNbDaysTopArts(int nbDaysTopArts) {
        this.nbDaysTopArts = nbDaysTopArts;
    }
    

    public List<FinalArticle> getTopArticles() throws AppException {
        if (topArticles == null) {
            Date limitDate = new Date(new Date().getTime() - getNbDaysTopArts() * 3600 * 24 * 1000);
            
            topArticles = getRequestContext().getMainDao()
                    .getTopArticles(getWebsiteEntity(), null, 
                            getTopArtNb() + getLastArtNb(), limitDate);
            topArticles = filterNotLastArticles(topArticles, getTopArtNb());
            int missingNb = getTopArtNb() - topArticles.size();
            if (missingNb > 0) {
                topArticles = getRequestContext().getMainDao()
                        .getTopArticles(getWebsiteEntity(), null, 
                                missingNb + getLastArtNb(), null);
                topArticles = filterNotLastArticles(topArticles, getTopArtNb());
            }
        }
        return topArticles;
    }

    protected List<FinalArticle> filterNotLastArticles(
            List<FinalArticle> inputArticles, int maxNb) throws AppException {
        List<FinalArticle> result = new ArrayList<>(inputArticles.size());
        List<FinalArticle> lastArts = getLastArticles();
        for (FinalArticle fa : inputArticles) {
            if (!lastArts.contains(fa)) {
                result.add(fa);
            }
            if (result.size() >= maxNb) {
                break;
            }
        }
        return result;
    }
    

}
