package de.jambonna.lolpapers.web.model.page.content;

import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.BaseArticle;
import de.jambonna.lolpapers.core.model.Category;
import de.jambonna.lolpapers.core.model.FinalArticle;
import de.jambonna.lolpapers.core.model.FinalContentParticipant;
import de.jambonna.lolpapers.core.model.Urls;
import de.jambonna.lolpapers.web.model.page.Breadcrumb;
import de.jambonna.lolpapers.web.model.page.Page;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FinalArticlePage extends Page {
    private static final Logger logger = LoggerFactory.getLogger(FinalArticlePage.class);

    private FinalArticle article;
    
    public FinalArticle getArticle() {
        if (article == null) {
            article = (FinalArticle)getServletRequest().getAttribute("finalArticle");
            if (article == null) {
                throw new IllegalStateException("No article");
            }
        }
        return article;
    }
    
    @Override
    public String getCurPageUrl() {
        return getUrl(getArticle().getArticleUrlRequestPath());
    }

    @Override
    public String getCurPageTitle() {
        return getArticle().getArticleTitle();
    }

    public Category getCategory() {
        Category c = getArticle().getBaseArticle().getCategory();
        if (c == null) {
            throw new IllegalStateException("No article category");
        }
        return c;
    }
    
    @Override
    public List<Breadcrumb> getBreadcrumbs() {
        List<Breadcrumb> breadcrumbs = super.getBreadcrumbs();
        if (breadcrumbs.size() > 0) {
            Category c = getCategory();
            breadcrumbs.add(breadcrumbs.size() - 1, 
                    newBreadcrumb(c.getUrlRequestPath(), c.getTitle()));
        }
        return breadcrumbs;
    }

    @Override
    public Map<String, String> getMetaProperties() {
        Map<String, String> props = new HashMap<>();

        props.put("og:title", getArticle().getArticleTitle());
        props.put("og:description", getArticle().getArticleDescription());
        props.put("og:type", "article");
        props.put("og:locale", getWebsite().getLocale().toString());
        props.put("og:site_name", getWebsite().getName());
        String imageUrl = getArticleImageUrl();
        if (imageUrl != null) {
            props.put("og:image", imageUrl);
        }
        
        return props;
    }
    

    public String getArticleImageUrl(int width, int height) {
        BaseArticle ba = getArticle().getBaseArticle();
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
    
    public String getArticleImageUrl() {
        BaseArticle ba = getArticle().getBaseArticle();
        if (ba.isArticleImageOk()) {
            try {
                Path path = ba.getArticleFullImage();
                return getMediaBaseUrl() + path;
            } catch (Exception ex) {
                logger.error("Error getting article image", ex);
            }
        }
        return null;
    }
    
    public FinalContentParticipant getCreator() {
        for (FinalContentParticipant fcp : getArticle().getParticipants()) {
            if (fcp.isInitiator()) {
                return fcp;
            }
        }
        return null;
    }
    public List<FinalContentParticipant> getReplaceParticipants() {
        List<FinalContentParticipant> result = new ArrayList<>();
        for (FinalContentParticipant fcp : getArticle().getParticipants()) {
            if (fcp.getReplacementCount() > 0) {
                result.add(fcp);
            }
        }
        return result;
    }

    public String getParticipantUrl(FinalContentParticipant fcp) {
        if (fcp != null && fcp.getUser() != null && fcp.getUser().isActive()) {
            return getUrl(fcp.getUser().getProfileRequestPath(getWebsite()));
        }
        return null;
    }
    
    public boolean isUpvotedAlready() throws AppException {
        return getArticle().getUpvoteUsers().contains(getUser());
    }
    
    public String getUpvoteUrl() {
        return getRequestContext().getUrl(Urls.UPVOTE_CONTENT_URL_ID, getWebsite());
    }
}
