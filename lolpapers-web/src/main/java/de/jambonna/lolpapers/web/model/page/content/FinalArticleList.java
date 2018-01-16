package de.jambonna.lolpapers.web.model.page.content;

import de.jambonna.lolpapers.core.model.BaseArticle;
import de.jambonna.lolpapers.core.model.FinalArticle;
import de.jambonna.lolpapers.web.model.page.Page;
import java.nio.file.Path;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FinalArticleList extends Page {
    private static final Logger logger = LoggerFactory.getLogger(FinalArticleList.class);

    private List<FinalArticle> articles;
    private boolean emphaseFirst;

    public List<FinalArticle> getArticles() {
        if (articles == null) {
            throw new IllegalStateException("No article list");
        }
        return articles;
    }

    public void setArticles(List<FinalArticle> articles) {
        this.articles = articles;
    }

    public boolean isEmphaseFirst() {
        return emphaseFirst;
    }

    public void setEmphaseFirst(boolean emphaseFirst) {
        this.emphaseFirst = emphaseFirst;
    }
    
    public String getArticleImageUrl(FinalArticle fa, int width, int height) {
        BaseArticle ba = fa.getBaseArticle();
        if (ba.isArticleImageOk()) {
            try {
                Path path = ba.getArticleImage(width, height, true);
                return getMediaBaseUrl() + path;
            } catch (Exception ex) {
                logger.error("Error getting article image", ex);
            }
        }
        return null;
    }

}
