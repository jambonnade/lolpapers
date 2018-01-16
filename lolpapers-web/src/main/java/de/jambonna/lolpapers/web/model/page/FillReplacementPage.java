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
//    private Map<String, Boolean> possibleFlags;


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

    
//    public Map<String, Boolean> getPossibleFlags() {
//        if (possibleFlags == null) {
//            Set<String> flagsOn = getPlaceholder().getDefinitionSD().getReplacementDefinition().getFlagsOn();
//            possibleFlags = new HashMap<>(flagsOn.size());
//            for (String flag : flagsOn) {
//                possibleFlags.put(flag, Boolean.TRUE);
//            }
//            // Definition only flags always available
//            for (SyntagmeAttribute attr : getSyntagmeType().getAttributes().values()) {
//                if (attr.isDefinitionOnly()) {
//                    for (SyntagmeFlag flag : attr.getFlags()) {
//                        possibleFlags.put(flag.getCode(), Boolean.TRUE);
//                    }
//                }
//            }
//        }
//        return possibleFlags;
//    }
//    public String getStrPossibleFlags() {
//        return String.join(",", getPossibleFlags().keySet());
//    }

    public int getReplacementTextMaxLg() {
        return new TemplatePlaceholder().getReplacementTextMaxLg();
    }
    
//    public String getReplacementDescriptionHtml() {
//        return getLanguage().getHTMLSyntagmeMainDescription(
//                getPlaceholder().getDefinitionSD().getReplacementDefinition()).html();
//    }
    
    public LanguageText getLanguageText() {
        if (languageText == null) {
            languageText = new LanguageText(
                    getRequestContext().getMessageBundle(), getLanguage());
        }
        return languageText;
    }

//    public String getHelpTextJSON(String baseTrKey, String specialCase) {
//        Map<String, Object> helpText = new HashMap<>();
//        
//        for (SyntagmeType st : getLanguage().getSyntagmeTypes().values()) {
//            String curBaseKey = "st." + st.getCode();
//            helpText.put(curBaseKey + ".help", 
//                    getLanguageText().getTypeInfo(baseTrKey, st, "help", specialCase));
//            helpText.put(curBaseKey + ".ex", 
//                    getLanguageText().getTypeEx(baseTrKey, st, specialCase));
//            
//            for (SyntagmeAttribute attr : st.getAttributes().values()) {
//                String attrBaseKey = curBaseKey + ".attr." + attr.getCode();
//                helpText.put(attrBaseKey + ".help", 
//                        getLanguageText().getAttrInfo(attrBaseKey, st, attr, "help", specialCase));
//                
//                
//                for (SyntagmeFlag flag : attr.getFlags()) {
//                    String flagBaseKey = curBaseKey + ".flag." + flag.getCode();
//                    helpText.put(flagBaseKey + ".help", 
//                            getLanguageText().getFlagInfo(flagBaseKey, st, flag, "help", specialCase));
//                    helpText.put(flagBaseKey + ".ex", 
//                            getLanguageText().getFlagEx(flagBaseKey, st, flag, specialCase));
//                }
//            }
//        }
//        
//        GsonBuilder gsonb = new GsonBuilder();
//        Gson gson = gsonb.create();
//        return gson.toJson(helpText);
//    }
}
