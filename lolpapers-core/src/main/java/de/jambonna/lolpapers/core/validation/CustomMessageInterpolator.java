package de.jambonna.lolpapers.core.validation;

import java.util.Locale;
import javax.validation.MessageInterpolator;

/**
 * Custom message interpolator to be able to choose the default locale
 */
public class CustomMessageInterpolator implements MessageInterpolator {

    private MessageInterpolator baseMi;
    private Locale locale;

    
    public CustomMessageInterpolator() {
        locale = Locale.ROOT;
    }
    
    public MessageInterpolator getBaseMi() {
        return baseMi;
    }

    public void setBaseMi(MessageInterpolator baseMi) {
        this.baseMi = baseMi;
    }
    

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    
    @Override
    public String interpolate(String string, Context cntxt) {
        return getBaseMi().interpolate(string, cntxt, locale);
    }

    @Override
    public String interpolate(String string, Context cntxt, Locale locale) {
        return getBaseMi().interpolate(string, cntxt, locale);
    }

}
