package de.jambonna.lolpapers.web.model.page;

import de.jambonna.lolpapers.core.app.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelpPage extends Page {

    private static final Logger logger = LoggerFactory.getLogger(HelpPage.class);
    
    public Config getConfig() {
        return getRequestContext().getApp().getConfig();
    }
    
    public int getNbArticleTemplateByDay() {
        return getConfig().getAppUserArticleTemplateLimit();
    }
    
    public int getMinArticleWc() {
        return getConfig().getAppArticleMinWc();
    }
    
    public int getMinArticleTemplatePlaceholders() {
        return getConfig().getAppArticleTemplateMinPlaceholder();
    }
    public int getMaxWordsByArticleTemplatePlaceholder() {
        return getConfig().getAppArticleTemplateMaxWordsByPlaceholder();
    }
}
