package de.jambonna.lolpapers.web.components.front.content;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.jambonna.lolpapers.core.model.ArticleTemplate;
import de.jambonna.lolpapers.core.model.BaseArticle;
import de.jambonna.lolpapers.core.model.BaseContent;
import de.jambonna.lolpapers.core.model.TemplatePlaceholder;
import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.core.model.UserException;
import de.jambonna.lolpapers.web.components.front.FrontServletAbstract;
import de.jambonna.lolpapers.web.model.RequestContext;
import de.jambonna.lolpapers.web.model.content.ArticleTemplateResponse;
import de.jambonna.lolpapers.web.model.content.Placeholder;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Article Template", urlPatterns = { "/internal/article/template" })
public class ArticleTemplateServlet extends FrontServletAbstract {

    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {        
        ArticleTemplate t = getArticleTemplate(rc);
        if (t != null) {
            rc.getRequest().setAttribute("articleTemplate", t);
            executeJsp(rc, response, "content/article-template.jsp");
        } else {
            responseNotFound(response);
        }
    }

    @Override
    protected void postAction(RequestContext rc, HttpServletResponse response) throws Exception {
        String action = rc.getParam("action");
        ArticleTemplate t = getArticleTemplate(rc);
        if (t != null) {
            String redirectUrl = rc.getUrl(
                    t.getBaseContent().getCategory().getWebsite().getRequestPath());
            
            Long sleepSec = rc.getLongParam("sleep");
            if (sleepSec != null && rc.isDevMode()) {
                Thread.sleep(Math.min(sleepSec, 10) * 1000L);
            }
            
            switch (action) {
                case "save":                    
                    ArticleTemplateResponse r = new ArticleTemplateResponse();
                    
                    String blocksToRemoveParam = rc.getParam("blocks_to_remove");
                    if (blocksToRemoveParam != null) {
                        t.setArticleContentRemovedBlocks(blocksToRemoveParam);
                        t.setArticleContentRemovedBlockIds(t.getArticleContentRemovedBlockIds());
                    }
                    
                    String descrRejectedParam = rc.getParam("description_rejected");
                    if (descrRejectedParam != null) {
                        t.setArticleDescriptionRejected("1".equals(descrRejectedParam));
                    }
                    
                    updateArticleTemplateUserConfFromRequest(rc, t);
                    
                    try {
                        updateArticleTemplatePlaceholdersFromRequest(rc, t);
                        
                        if (rc.getParam("finish") != null) {
                            t.finish();
                            
                            r.setRedirectTo(redirectUrl);
                        }
                        t.setLastSaveAt(new Date());
                        rc.getMainDao().saveArticleTemplate(t);
                        
                        // Provide an updated version of template data 
                        // (for instance : placeholders contains now ids)
                        r.setPlaceholders(t);
                        r.setRemovedBlocks(t.getArticleContentRemovedBlocks());
                        
                    } catch (UserException ex) {
                        r.setErrorCode(ex.getCode());
                    }
                    
                    jsonResponse(response, r);
                    break;
                case "delete":
                    t.setToRemove(true);
                    
                    BaseArticle ba = t.getBaseArticle();
                    if (rc.getParam("reject") != null) {
                        ba.setStatus(BaseContent.Status.REJECTED);
                        ba.setArticleRejectionReason(BaseArticle.RejectionReason.USER_WRONG_CONTENT);
                    } else {
                        ba.setStatus(BaseContent.Status.AVAILABLE);
                    }
                    ba.setToSave(true);
                    rc.getMainDao().saveArticleTemplate(t);
                    
                    response.sendRedirect(redirectUrl);
                    break;
            }
        } else {
            responseNotFound(response);
        }
    }
    
    protected ArticleTemplate getArticleTemplate(RequestContext rc) throws Exception {
        User user = rc.getUser();
        Long id = rc.getLongParam("id");
        ArticleTemplate t = rc.getMainDao().findArticleTemplate(id);
        if (t != null && user.equals(t.getUser()) && t.canUserAccess() 
                && rc.isWebsiteMatchingParam(t.getWebsite())) {
            rc.setWebsiteEntity(t.getWebsite());
            return t;
        }
        return null;
    }
   
    protected Collection<Placeholder> unserializePlaceholders(String placeholdersJson) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Placeholder>>(){}.getType();
        Collection<Placeholder> placeholders = gson.fromJson(placeholdersJson, collectionType);
        return placeholders;
    }
    
    protected void updateArticleTemplatePlaceholdersFromRequest(
            RequestContext rc, ArticleTemplate t) throws UserException {
        String placeholdersJson = rc.getParam("placeholders");
        if (placeholdersJson != null && placeholdersJson.length() > 0) {
            Collection<Placeholder> placeholders = unserializePlaceholders(placeholdersJson);
            for (Placeholder inputP: placeholders) {                    
                TemplatePlaceholder p = t.getPlaceholder(inputP.getId());
                if (p == null) {
                    p = new TemplatePlaceholder();
                    p.setContentTemplate(t);
                    p.setCreatedBy(t.getUser());
                    p.setCreatedAt(new Date());
                    t.getPlaceholders().add(p);
                }
                inputP.updateTemplatePlaceholder(p);

                if (inputP.isRemoved()) {
                    t.getPlaceholders().remove(p);
                } else {
                    p.validatePlaceholder();
                }
            }
            t.validatePlaceholderReferences();
            // Todo : check placeholders dont overlap
        }
    }
    
    protected void updateArticleTemplateUserConfFromRequest(
            RequestContext rc, ArticleTemplate t) {
        User u = t.getUser();
        String tooltipsDisabledParam = rc.getParam("user_tooltips_disabled");
        if (tooltipsDisabledParam != null) {
            u.setSdefTooltipsDisabled("1".equals(tooltipsDisabledParam));
            u.setToSave(true);
        }
    }
}