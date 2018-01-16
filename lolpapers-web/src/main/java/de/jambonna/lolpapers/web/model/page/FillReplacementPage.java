package de.jambonna.lolpapers.web.model.page;

import de.jambonna.lolpapers.core.model.TemplatePlaceholder;
import de.jambonna.lolpapers.core.model.lang.Language;
import de.jambonna.lolpapers.core.model.lang.SyntagmeAttribute;
import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinition;
import de.jambonna.lolpapers.core.model.lang.SyntagmeType;
import de.jambonna.lolpapers.web.model.content.LanguageText;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class FillReplacementPage extends Page {
    private TemplatePlaceholder placeholder;
    private LanguageText languageText;


    public TemplatePlaceholder getPlaceholder() {
        if (placeholder == null) {
            placeholder = 
                    (TemplatePlaceholder)getServletRequest().getAttribute("templatePlaceholder");
        }
        return placeholder;
    }
    
    public String getFormActionUrl() {
        return getFillReplacementUrl();
    }
    
    public Language getLanguage() {
        return getPlaceholder().getContentTemplate().getLanguage();
    }
    public String getLanguageJSON() {
        return getLanguage().toJson(false);
    }
    
    public SyntagmeType getSyntagmeType() {
        return getPlaceholder().getDefinitionSD().getType();
    }

    public Set<String> getOrigContextFlags() {
        return getPlaceholder().getDefinitionSD().getFlagsOn(true);
    }
    public String getStrOrigContextFlags() {
        return String.join(",", getOrigContextFlags());
    }
    
    public Set<String> getOrigReplFlags() {
        return getPlaceholder().getDefinitionSD().getReplacementDefinition().getFlagsOn();
    }
    public String getStrOrigReplFlags() {
        return String.join(",", getOrigReplFlags());
    }

    public String getReplHTMLInfo() {
        return getLanguage().getSDefReplacementHTMLInfo(
                    getPlaceholder().getDefinitionSD().getReplacementDefinition())
                .outerHtml();
    }
    public String getContextHTMLInfo() {
        return getLanguage().getSDefContextHTMLInfo(
                    getPlaceholder().getDefinitionSD())
                .outerHtml();
    }
    
    public List<SyntagmeAttribute> getPossiblyAskedAttributes() {
        SyntagmeDefinition sd = getPlaceholder().getDefinitionSD();
        SyntagmeType st = sd.getType();
        List<SyntagmeAttribute> result = 
                new ArrayList<>(st.getAttributes().size());
        for (SyntagmeAttribute attr : st.getAttributes().values()) {
            if (sd.isAttributeAllowed(attr) && attr.isDefinitionOnly() 
                    && !attr.isVirtual() && sd.isAttributeRequired(attr)) {
                result.add(attr);
            }
        }
        return result;
    }

    

    public int getReplacementTextMaxLg() {
        return new TemplatePlaceholder().getReplacementTextMaxLg();
    }
    
    
    public LanguageText getLanguageText() {
        if (languageText == null) {
            languageText = new LanguageText(
                    getRequestContext().getMessageBundle(), getLanguage());
        }
        return languageText;
    }

}
