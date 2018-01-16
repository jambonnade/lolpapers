package de.jambonna.lolpapers.core.model.text;

import de.jambonna.lolpapers.core.model.lang.Language;
import java.util.List;
import java.util.Locale;

/**
 * A CharSequence with language-related operations
 */
public class Text {
    private final Language language;
    private final CharSequence charSequence;
    private String text;
    private String lcText;
    private String ucText;
    private Boolean isFirstUc;
    private Boolean hasRemainingUc;
    private Boolean hasRemainingLc;
    private Boolean hasUc;
    private List<TextRange> words;

    
    public Text(Language language, CharSequence charSequence) {
        if (charSequence == null) {
            throw new IllegalArgumentException("Invalid text");
        }
        
        this.language = language;
        this.charSequence = charSequence;
    }

    public Language getLanguage() {
        return language;
    }
    
    public Locale getLocale() {
        return getLanguage().getLocale();
    }

    public CharSequence getCharSequence() {
        return charSequence;
    }
    
    public int length() {
        return getCharSequence().length();
    }

    public String getText() {
        if (text == null) {
            text = getCharSequence().toString();
        }
        return text;
    }
    
    @Override
    public String toString() {
        return getText();
    }
    
    
    public String getLcText() {
        if (lcText == null) {
            lcText = getText().toLowerCase(getLocale());
        }
        return lcText;
    }
    
    public String getUcText() {
        if (ucText == null) {
            ucText = getText().toUpperCase(getLocale());
        }
        return ucText;
    }
    
    public CharSequence getFirstLetter() {
        if (length() > 0) {
            return getText().subSequence(0, 1);
        }
        return "";
    }
    
    public CharSequence getLcFirstLetter() {
        if (length() > 0) {
            return getLcText().subSequence(0, 1);
        }
        return "";
    }
    
    public CharSequence getUcFirstLetter() {
        if (length() > 0) {
            return getUcText().subSequence(0, 1);
        }
        return "";
    }

    public CharSequence getRemainingText() {
        if (length() > 0) {
            return getText().subSequence(1, length());
        }
        return "";
    }

    public CharSequence getLcRemainingText() {
        if (length() > 0) {
            return getLcText().subSequence(1, length());
        }
        return "";
    }
    
    public CharSequence getUcRemainingText() {
        if (length() > 0) {
            return getUcText().subSequence(1, length());
        }
        return "";
    }
        
    public boolean isFirstUc() {
        if (isFirstUc == null) {
            isFirstUc = false;
            if (length() > 0) {
                isFirstUc = !getFirstLetter().equals(getLcFirstLetter());
            }
        }
        return isFirstUc;
    }
    
    public boolean hasRemainingUc() {
        if (hasRemainingUc == null) {
            hasRemainingUc = false;
            if (length() > 0) {
                hasRemainingUc = !getLcRemainingText().equals(getRemainingText());
            }
        }
        return hasRemainingUc;
    }
    
    public boolean hasRemainingLc() {
        if (hasRemainingLc == null) {
            hasRemainingLc = false;
            if (length() > 0) {
                hasRemainingLc = !getUcRemainingText().equals(getRemainingText());
            }
        }
        return hasRemainingLc;
    }
    
    public boolean hasOnlyFirstUc() {
        return isFirstUc() && !hasRemainingUc();
    }
    
    public boolean hasUc() {
        if (hasUc == null) {
            if ((isFirstUc != null && isFirstUc) 
                    || (hasRemainingUc != null &&  hasRemainingUc)) {
                hasUc = true;
            } else {
                hasUc = !getLcText().equals(getText());
            }
        }
        return hasUc;
    }
    
    public void addTransformedTextTo(StringBuilder sb, Boolean firstToUc, Boolean remainingToUc) {
        if (firstToUc != null) {
            sb.append(firstToUc ? getUcFirstLetter() : getLcFirstLetter());
        } else {
            sb.append(getFirstLetter());
        }
        if (remainingToUc != null) {
            sb.append(remainingToUc ? getUcRemainingText(): getLcRemainingText());
        } else {
            sb.append(getRemainingText());
        }
    }
    
    
    public List<TextRange> getWords() {
        if (words == null) {
            words = getLanguage().extractWords(getCharSequence());
        }
        return words;
    }
    
    public TextRange getFirstWord() {
        List<TextRange> wlist = getWords();
        return wlist.isEmpty() ? null : wlist.get(0);
    }
    
    public String transformFirstWordLetter(boolean toUc) {
        TextRange first = getFirstWord();
        if (first != null) {
            StringBuilder sb = new StringBuilder(length());
            sb.append(getCharSequence().subSequence(0, first.getStart()));
            first.addTransformedTextTo(sb, toUc, null);
            sb.append(getCharSequence().subSequence(first.getEnd(), length()));
            return sb.toString();
        }
        return getText();
    }
}
