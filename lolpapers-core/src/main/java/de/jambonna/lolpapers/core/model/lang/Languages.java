package de.jambonna.lolpapers.core.model.lang;

import de.jambonna.lolpapers.core.model.lang.fr.FrLanguage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Repository of Language objects 
 */
public class Languages {
    private static final Logger logger = LoggerFactory.getLogger(Languages.class);

    
    private static Languages instance;
    
    /**
     * Gets singleton instance. For single-thread contexts only
     * @return Languages
     */
    public static Languages getInstance() {
        if (instance == null) {
            instance = new Languages();
        }
        return instance;
    }
    

    private final Map<String, Language> languages;
    private final Map<Locale, Language> languagesByLocale;

    
    public Languages() {
        // Log creations to see if minimum of instances is created by thread
        logger.debug("Creation");
        
        Map<Locale, Language> tmpLanguagesByL = new HashMap<>();
        Map<String, Language> tmpLanguages = new HashMap<>();
        Language l;
        l = new FrLanguage();
        tmpLanguages.put(l.getCode(), l);
        tmpLanguagesByL.put(l.getLocale(), l);
        languages = Collections.unmodifiableMap(tmpLanguages);
        languagesByLocale = Collections.unmodifiableMap(tmpLanguagesByL);
    }
    
    public Language getByLocale(Locale locale) {
        Language l = languagesByLocale.get(locale);
        if (l == null) {
            throw new IllegalArgumentException("Unmanaged locale " + locale);
        }
        return l;
    }
    
    public Language getFr() {
        return languages.get(FrLanguage.CODE);
    }
}
