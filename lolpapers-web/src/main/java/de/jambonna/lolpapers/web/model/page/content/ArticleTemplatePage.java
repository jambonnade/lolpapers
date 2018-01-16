package de.jambonna.lolpapers.web.model.page.content;

import com.google.gson.Gson;
import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.ArticleTemplate;
import de.jambonna.lolpapers.core.model.BaseArticle;
import de.jambonna.lolpapers.core.model.TemplatePlaceholder;
import de.jambonna.lolpapers.core.model.Urls;
import de.jambonna.lolpapers.core.model.lang.Language;
import de.jambonna.lolpapers.core.model.lang.SyntagmeAttribute;
import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinition;
import de.jambonna.lolpapers.core.model.lang.SyntagmeType;
import de.jambonna.lolpapers.web.model.content.LanguageText;
import de.jambonna.lolpapers.web.model.content.Placeholder;
import de.jambonna.lolpapers.web.model.page.Page;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ArticleTemplatePage extends Page {
    private static final Logger logger = LoggerFactory.getLogger(ArticleTemplatePage.class);

    private ArticleTemplate articleTemplate;
    private SyntagmeType curST;
    private SyntagmeDefinition curSTDef;
    private LanguageText languageText;

    public ArticleTemplate getArticleTemplate() {
        if (articleTemplate == null) {
            articleTemplate = 
                    (ArticleTemplate)getServletRequest().getAttribute("articleTemplate");
        }
        return articleTemplate;
    }
    
    public String getFormActionUrl() {
        return getRequestContext().getUrl(Urls.ARTICLE_TEMPLATE_EDIT_URL_ID, getWebsite());
    }
    
    public Language getLanguage() {
        return getArticleTemplate().getLanguage();
    }
    public String getLanguageJSON() {
        return getLanguage().toJson(false);
    }
    
    public String getPlaceholdersJSON() {
        List<Placeholder> placeholders = 
                new ArrayList<>(getArticleTemplate().getPlaceholders().size());
        for (TemplatePlaceholder tp : getArticleTemplate().getPlaceholders()) {
            Placeholder p = new Placeholder();
            p.setFromTemplatePlaceholder(tp);
            placeholders.add(p);
        }
        
        Gson gson = new Gson();
        return gson.toJson(placeholders);
    }
    
    public int getPlaceholderTextMaxLg() {
        return new TemplatePlaceholder().getOrigTextMaxLg();
    }
    
    public int getMinWordsCount() {
        return getRequestContext().getApp().getConfig().getAppArticleMinWc();
    }
    public int getMinPlaceholderCount() {
        return getRequestContext().getApp().getConfig().getAppArticleTemplateMinPlaceholder();
    }
    public int getMaxWordsByPlaceholder() {
        return getRequestContext().getApp().getConfig().getAppArticleTemplateMaxWordsByPlaceholder();
    }
    public String getSDefReusableAttrCode() {
        return Language.ATTR_COMMON_REUSABLE;
    }
    public String getSDefReusableFlagCode() {
        return Language.FLAG_COMMON_REUSABLE_ON;
    }
    public String getSDefNotReusableFlagCode() {
        return Language.FLAG_COMMON_REUSABLE_OFF;
    }
    
    public boolean isTutoNeeded() throws AppException {
        return getUser().getFinishedArticleCount() == 0;
    }

    
    public void setCurST(SyntagmeType curST) {
        this.curST = curST;
        curSTDef = null;
    }

    public SyntagmeType getCurST() {
        SyntagmeType st = curST;
        if (st == null) {
            throw new IllegalStateException("No current stype");
        }
        return st;
    }

    public SyntagmeDefinition getCurSTDef() {
        SyntagmeDefinition sd = curSTDef;
        if (sd == null) {
            sd = new SyntagmeDefinition(getCurST()); 
            curSTDef = sd;
        }
        return sd;
    }
    
    public List<SyntagmeAttribute> getCurSTCtxAttributes() {
        SyntagmeDefinition sd = getCurSTDef();
        List<SyntagmeAttribute> result = 
                new ArrayList<>(sd.getType().getAttributes().size());
        for (SyntagmeAttribute attr : sd.getType().getAttributes().values()) {
            if (sd.isAttributeAllowed(attr) && attr.isContext()) {
                result.add(attr);
            }
        }
        return result;
    }
    
    public List<SyntagmeAttribute> getCurSTDefAttributes() {
        SyntagmeDefinition sd = getCurSTDef();
        List<SyntagmeAttribute> result = 
                new ArrayList<>(sd.getType().getAttributes().size());
        for (SyntagmeAttribute attr : sd.getType().getAttributes().values()) {
            if (sd.isAttributeAllowed(attr) && !attr.isContext() && !attr.isVirtual()
                    && !getSDefReusableAttrCode().equals(attr.getCode())) {
                result.add(attr);
            }
        }
        return result;
    }
    public List<SyntagmeAttribute> getCurSTReplAttributes() {
        SyntagmeDefinition sd = getCurSTDef();
        List<SyntagmeAttribute> result = 
                new ArrayList<>(sd.getType().getAttributes().size());
        for (SyntagmeAttribute attr : sd.getType().getAttributes().values()) {
            if (sd.getReplacementDefinition().isAttributeAllowed(attr) && !attr.isVirtual()) {
                result.add(attr);
            }
        }
        return result;
    }
    
    public boolean isCurSTDefAttributeRequired(SyntagmeAttribute attr) {
        SyntagmeDefinition sd = getCurSTDef();
        return sd.isAttributeRequired(attr);
    }
    public boolean isCurSTReplAttributeRequired(SyntagmeAttribute attr) {
        SyntagmeDefinition sd = getCurSTDef();
        return sd.getReplacementDefinition().isAttributeRequired(attr);
    }

    public LanguageText getLanguageText() {
        if (languageText == null) {
            languageText = new LanguageText(
                    getRequestContext().getMessageBundle(), getLanguage());
        }
        return languageText;
    }
    
    public String getArticleImageUrl(int width, int height) {
        BaseArticle ba = getArticleTemplate().getBaseArticle();
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
