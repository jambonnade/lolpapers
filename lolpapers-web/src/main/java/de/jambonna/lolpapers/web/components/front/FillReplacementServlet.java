package de.jambonna.lolpapers.web.components.front;

import de.jambonna.lolpapers.core.model.ArticleTemplate;
import de.jambonna.lolpapers.core.model.ContentTemplate;
import de.jambonna.lolpapers.core.model.FinalArticle;
import de.jambonna.lolpapers.core.model.TemplatePlaceholder;
import de.jambonna.lolpapers.core.model.TwitterAccount;
import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.core.model.Website;
import de.jambonna.lolpapers.web.model.RequestContext;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@WebServlet(name = "Fill Replacement", urlPatterns = { "/internal/replacement" })
public class FillReplacementServlet extends FrontServletAbstract {

    private static final Logger logger = LoggerFactory.getLogger(FillReplacementServlet.class);

    @Override
    protected void postAction(RequestContext rc, HttpServletResponse response) throws Exception {
        rc.loadWebsiteDataFromParam();
        if (redirectToLoginIfNeeded(rc, response)) {
            return;
        }
        
        Long id = rc.getLongParam("id");
        TemplatePlaceholder tp = rc.getMainDao().findTemplatePlaceholder(id);
        if (tp == null || tp.isReplacementFilled()) {
            responseNotFound(response);
            return;
        }
        
        User user = rc.getUser();
        if (!user.canDoReplacement()) {
            addSessionError(rc, "fillReplacement.cantDoReplacement");
            redirectReferer(rc, response);
            return;
        }
        
        if (!user.equals(tp.getReplacementBy())) {
            addSessionError(rc, "fillReplacement.ownershipLost");
            redirectReferer(rc, response);
            return;
        }
        
        String textParam = rc.getParam("text");
        String flagsParam = rc.getParam("flags");

//        Collection<String> inputFlags = Collections.emptyList();
//        if (flagsParam != null && flagsParam.length() > 0) {
//            String[] tabInputFlags = flagsParam.split(",");
//            inputFlags = Arrays.asList(tabInputFlags);
//        }
        tp.setReplacementDefinitionFlags(flagsParam);
        tp.setReplacementText(textParam);
        tp.normalizeReplacementText();
//        tp.setReplacement(textParam, inputFlags);
        
        try {
            tp.validateReplacement();
        } catch (Exception ex) {
            addSessionError(rc, "fillReplacement.invalidReplacement");
            logger.warn("Invalid placeholder", ex);
            redirectReferer(rc, response);
            return;
        }
        
//        tp.updateSerializedReplacement();
        tp.setReplacementAt(new Date());
        
        user.setReplacementCount(user.getReplacementCount() + 1);
        user.setToSave(true);
        
        ContentTemplate tc = tp.getContentTemplate();
        if (tc.areAllPlaceholdersFilled()) {
            if (!(tc instanceof ArticleTemplate)) {
                throw new IllegalStateException("Unmanaged content type");
            }
            ArticleTemplate t = (ArticleTemplate)tc;
            
            FinalArticle fa = t.toFinalArticle();
            tc.setToSave(true);
            
            rc.getMainDao().saveFinalArticle(fa);
            
            String url = rc.getUrl(fa.getArticleUrlRequestPath());
            if (rc.isFullBaseUrl()) {
                try {
                    TwitterAccount th = TwitterAccount.mainAccount(rc.getApp());
                    th.tweetLink(url, fa.getArticleTitle());
                } catch (Exception ex) {
                    logger.error("Error tweeting final article", ex);
                }
            } else {
                logger.debug("No tweet since no external url");
            }
            
            response.sendRedirect(url);
        } else {
            rc.getMainDao().saveTemplatePlaceholder(tp);
            redirectReferer(rc, response);
        }        

    }

    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        Website w = rc.loadWebsiteDataFromParam();
        
        if (redirectToLoginIfNeeded(rc, response)) {
            return;
        }
        
        User user = rc.getUser();
        if (user.canDoReplacement()) {
            List<TemplatePlaceholder> unfilled = 
                    rc.getMainDao().getOrderedUnfilledTemplatePlaceholders(rc.getWebsiteEntity());
            List<TemplatePlaceholder> candidates = user.getPlaceholdersToFill(unfilled);
            TemplatePlaceholder tp = user.getStartedBySelfPlaceholderToFill(candidates);
            if (tp == null) {
                if (candidates.size() > 0) {
                    tp = candidates.get(0);
                    user.takePlaceholderToFill(tp);
                    rc.getMainDao().saveTemplatePlaceholder(tp);
                }
            }
            rc.getRequest().setAttribute("templatePlaceholder", tp);
        }
        
        executeJsp(rc, response, "replacement.jsp");

    }
    
}
