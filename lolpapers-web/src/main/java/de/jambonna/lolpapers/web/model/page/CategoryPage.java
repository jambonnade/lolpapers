package de.jambonna.lolpapers.web.model.page;

import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.Category;
import de.jambonna.lolpapers.core.model.FinalArticle;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CategoryPage extends Page {
    private static final Logger logger = LoggerFactory.getLogger(CategoryPage.class);

    private Category category;
    private List<FinalArticle> articles;
    private int artMaxNb = 5;

    public Category getCategory() {
        if (category == null) {
            category = (Category)getServletRequest().getAttribute("category");
            if (category == null) {
                throw new IllegalStateException("No category");
            }
        }
        return category;
    }
    
    @Override
    public String getCurPageUrl() {
        return getUrl(getCategory().getUrlRequestPath());
    }

    @Override
    public String getCurPageTitle() {
        return getCategory().getTitle();
    }

    public int getArtMaxNb() {
        return artMaxNb;
    }

    public void setArtMaxNb(int artMaxNb) {
        this.artMaxNb = artMaxNb;
    }

    public List<FinalArticle> getArticles() throws AppException {
        if (articles == null) {
            articles = getRequestContext().getMainDao()
                    .getLastArticles(null, getCategory(), getArtMaxNb());
        }
        return articles;
    }
    
}
