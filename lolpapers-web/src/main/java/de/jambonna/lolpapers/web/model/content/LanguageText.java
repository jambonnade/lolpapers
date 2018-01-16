package de.jambonna.lolpapers.web.model.content;

import de.jambonna.lolpapers.core.model.lang.Language;
import de.jambonna.lolpapers.core.model.lang.SyntagmeAttribute;
import de.jambonna.lolpapers.core.model.lang.SyntagmeFlag;
import de.jambonna.lolpapers.core.model.lang.SyntagmeType;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tool to fetch SyntagmeType related text in the WebApp's ResourceBundle.
 * Has a fallback feature to avoid repititions in the ResourceBundle
 */
public class LanguageText {
    public static final String DEFAULT_BASE_KEY = "lang";
    
    private static final Logger logger = LoggerFactory.getLogger(LanguageText.class);

    private final Language language;
    private final ResourceBundle resourceBundle;
    private final Map<String, String> sTypeFallbacks;

    public LanguageText(ResourceBundle resourceBundle, Language language) {
        this.resourceBundle = resourceBundle;
        this.language = language;
        sTypeFallbacks = new HashMap<>();
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public Language getLanguage() {
        return language;
    }
    
    
    public String getSTypeBaseKey(String baseKey, String typeCode) {
        if (baseKey == null) {
            baseKey = DEFAULT_BASE_KEY;
        }
        return baseKey + "." + getLanguage().getCode() + ".st." + typeCode;
    }
    
    protected String getFallbackSType(String typeCode) {
        if (!sTypeFallbacks.containsKey(typeCode)) {
            String fallbackStCodeKey = getSTypeBaseKey(null, typeCode) + ".fallback";
            String fallbackStCode = null;
            if (getResourceBundle().containsKey(fallbackStCodeKey)) {
                fallbackStCode = getResourceBundle().getString(fallbackStCodeKey);
            }
            sTypeFallbacks.put(typeCode, fallbackStCode);
        }
        return sTypeFallbacks.get(typeCode);
    }
    
    protected String getInfo(String[] baseKeys, String typeCode, String subKey) {        
        ResourceBundle rb = getResourceBundle();
        for (String baseKey : baseKeys) {
            if (baseKey.length() > 0) {
                String key = getSTypeBaseKey(baseKey, typeCode) + subKey;
                logger.debug("Trying key {} ...", key);
                if (rb.containsKey(key)) {
                    return rb.getString(key);
                }
            }
        }
        return null;
    }
    protected String getInfo(String typeCode, String subKey, String info, String specialCase, String... baseKeys) {
        String result = null;
        
        if (specialCase.length() > 0) {
            String finalSubKey = subKey + "." + specialCase + "." + info;
            result = getInfo(baseKeys, typeCode, finalSubKey);
        }
        
        if (result == null) {
            result = getInfo(baseKeys, typeCode, subKey + "." + info);
        }
        
        if (result == null) {
            String fallbackSType = getFallbackSType(typeCode);
            if (fallbackSType != null) {
                result = getInfo(fallbackSType, subKey, info, specialCase, baseKeys);
            }
        }
        return result;
    }
    
    public String getTypeInfo(String specialBaseKey, SyntagmeType type, String info, String specialCase) {
        return getInfo(type.getCode(), "", info, specialCase, specialBaseKey, DEFAULT_BASE_KEY);
    }
    
    public String[] getTypeEx(String specialBaseKey, SyntagmeType type, String specialCase) {
        return getInfoEx(getTypeInfo(specialBaseKey, type, "ex", specialCase));
    }
    
    public String getTypeMainEx(String specialBaseKey, SyntagmeType type, String specialCase) {
        String[] ex = getTypeEx(specialBaseKey, type, specialCase);
        return ex != null && ex.length > 0 ? ex[0] : null;
    }

    public String getAttrInfo(String specialBaseKey, SyntagmeType type, SyntagmeAttribute attr, String info, String specialCase) {
        String subKey = ".attr." + attr.getCode();
        return getInfo(type.getCode(), subKey, info, specialCase, specialBaseKey, DEFAULT_BASE_KEY);
    }
    
    public String getAttrGrpInfo(String specialBaseKey, SyntagmeType type, String groupCode, String info, String specialCase) {
        String subKey = ".agrp." + groupCode;
        return getInfo(type.getCode(), subKey, info, specialCase, specialBaseKey, DEFAULT_BASE_KEY);
    }
    
    public String getFlagInfo(String specialBaseKey, SyntagmeType type, SyntagmeFlag flag, String info, String specialCase) {
        String subKey = ".flag." + flag.getCode();
        String result = getInfo(type.getCode(), subKey, info, specialCase, specialBaseKey, DEFAULT_BASE_KEY);
        if (result == null && "name".equals(info)) {
            result = flag.getStandaloneGeneralLabel();
        }
        return result;
    }
    
    public String[] getFlagEx(String specialBaseKey, SyntagmeType type, SyntagmeFlag flag, String specialCase) {
        return getInfoEx(getFlagInfo(specialBaseKey, type, flag, "ex", specialCase));
    }
    
    public String getFlagMainEx(String specialBaseKey, SyntagmeType type, SyntagmeFlag flag, String specialCase) {
        String[] ex = getFlagEx(specialBaseKey, type, flag, specialCase);
        return ex != null && ex.length > 0 ? ex[0] : null;
    }
    
    protected String[] getInfoEx(String info) {
        if (info != null && info.length() > 0) {
            return info.split(",");
        }
        return null;
    }


}
