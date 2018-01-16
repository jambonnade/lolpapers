package de.jambonna.lolpapers.web.components.front.content;

import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.ArticleTemplate;
import de.jambonna.lolpapers.core.model.BaseArticle;
import de.jambonna.lolpapers.core.model.BaseContent;
import de.jambonna.lolpapers.core.model.Category;
import de.jambonna.lolpapers.core.model.Urls;
import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.web.components.front.FrontServletAbstract;
import de.jambonna.lolpapers.web.model.RequestContext;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@WebServlet(name = "Article Template New", urlPatterns = { "/internal/article/template/new" })
public class ArticleTemplateNewServlet extends FrontServletAbstract {

    @Override
    protected boolean preProcess(RequestContext rc, HttpServletResponse response) throws Exception {
        rc.loadWebsiteDataFromParam();

        return !redirectToLoginIfNeeded(rc, response);
    }

    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        if (isWithChoicePage(rc)) {
            executeJsp(rc, response, "content/article-template-new.jsp");
        } else {
            if (!startTemplateFromRandomArticle(rc, response)) {
                redirectHome(rc, response);
            }
        }
    }

    @Override
    protected void postAction(RequestContext rc, HttpServletResponse response) throws Exception {
        if (isWithChoicePage(rc)) {
            boolean okay = false;
            
            User user = rc.getUser();
            if (user.canStartArticleTemplate()) {
                if (rc.getParam("from_url") != null) {
                    String inputUrl = rc.getParam("url");
                    Long categoryId = rc.getLongParam("category_id");

                    BaseArticle ba = createBaseArticle(rc, inputUrl, categoryId);
                    if (ba != null) {
                        startTemplate(rc, response, ba);
                        okay = true;
                    }
                } else {
                    okay = startTemplateFromRandomArticle(rc, response);
                }
            } else {
                addSessionError(rc, "articleTemplate.new.cantStartArticleError");
            }
            if (!okay) {
                redirectReferer(rc, response);
            }
        } else {
            getAction(rc, response);
        }
    }
    
    protected boolean isWithChoicePage(RequestContext rc) throws AppException {
        return rc.getUser().isTrusted();
    }
    
    protected BaseArticle createBaseArticle(RequestContext rc, String url, Long categoryId) throws AppException, IOException {
        Category category = rc.getMainDao().findCategory(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Invalid category id");
        }
        BaseArticle ba = new BaseArticle();
        ba.init(rc.getApp());
        ba.setCreatedAt(new Date());
        ba.setCategory(category);
        ba.setUser(rc.getUser());
        ba.setFromUrl(url);
        if (rc.getMainDao().findBaseArticleByCanonicalUrl(ba.getArticleCanonicalUrl()) != null) {
            addSessionError(rc, "articleTemplate.new.baseArticleExistsError");
            return null;
        }
        ba.setIdentifier("direct:" + ba.getArticleCanonicalUrl());
        ba.prepare();
        rc.getMainDao().saveBaseArticle(ba);
        if (ba.getStatus() != BaseContent.Status.AVAILABLE) {
            addSessionError(rc, "articleTemplate.new.baseArticleInvalidError");
            return null;
        }
        return ba;
    }
    
    protected boolean startTemplateFromRandomArticle(RequestContext rc, HttpServletResponse response) throws Exception {
        User user = rc.getUser();
        if (user.canStartArticleTemplate()) {
            BaseArticle ba = null;
            List<BaseArticle> baseArticles = 
                    rc.getMainDao().getAvailableBaseArticles(rc.getWebsiteEntity());
            if (baseArticles.size() > 0) {
                Random r = rc.getRng();
                ba = baseArticles.get(r.nextInt(baseArticles.size()));
            }

            if (ba == null) {
                addSessionError(rc, "articleTemplate.new.noArticleError");
            } else {
                startTemplate(rc, response, ba);
                return true;
            }
        } else {
            addSessionError(rc, "articleTemplate.new.cantStartArticleError");
        }
        return false;
    }
    
    protected void startTemplate(RequestContext rc, HttpServletResponse response, BaseArticle ba) throws Exception {
        User user = rc.getUser();
        ArticleTemplate t = user.startArticleTemplate(ba);
        rc.getMainDao().saveArticleTemplate(t);
        String url = rc.getUrl(Urls.ARTICLE_TEMPLATE_EDIT_URL_ID, rc.getWebsiteData()) 
                + Urls.query().param("id", t.getContentTemplateId());
        response.sendRedirect(url);
    }
}