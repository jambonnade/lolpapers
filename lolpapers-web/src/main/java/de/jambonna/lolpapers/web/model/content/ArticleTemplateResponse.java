package de.jambonna.lolpapers.web.model.content;

import de.jambonna.lolpapers.core.model.ArticleTemplate;
import de.jambonna.lolpapers.core.model.TemplatePlaceholder;
import java.util.ArrayList;
import java.util.List;

/**
 * Data returned to the article template javascript editor when saving
 */
public class ArticleTemplateResponse {
    private String errorCode;
    private List<Placeholder> placeholders;
    private String removedBlocks;
    private String redirectTo;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public List<Placeholder> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(List<Placeholder> placeholders) {
        this.placeholders = placeholders;
    }
    
    public void setPlaceholders(ArticleTemplate t) {
        placeholders = new ArrayList<>(t.getPlaceholders().size());
        for (TemplatePlaceholder tp : t.getPlaceholders()) {
            Placeholder p = new Placeholder();
            p.setFromTemplatePlaceholder(tp);
            placeholders.add(p);
        }
    }

    public String getRemovedBlocks() {
        return removedBlocks;
    }

    public void setRemovedBlocks(String removedBlocks) {
        this.removedBlocks = removedBlocks;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }
    
    
}
