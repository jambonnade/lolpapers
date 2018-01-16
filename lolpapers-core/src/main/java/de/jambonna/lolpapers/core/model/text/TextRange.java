package de.jambonna.lolpapers.core.model.text;

import de.jambonna.lolpapers.core.model.lang.Language;


/**
 * Same as Text but for a subsquence of a given text
 */
public class TextRange extends Text {
    private final CharSequence origSequence;
    private final int start;
    private final int end;

    
    public TextRange(Language language, CharSequence origSequence, int start, int end) {
        // Note : subSequence call will check already boundary validity
        super(language, origSequence.subSequence(start, end));
        
        this.origSequence = origSequence;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public CharSequence getOrigSequence() {
        return origSequence;
    }

    public CharSequence getTextFrom() {
        return getOrigSequence().subSequence(getStart(), getOrigSequence().length());
    }

    public CharSequence getTextAfter() {
        return getOrigSequence().subSequence(getEnd(), getOrigSequence().length());
    }
    public CharSequence getTextAfter(TextRange beforeWord) {
        if (beforeWord == null) {
            return getTextAfter();
        }
        return getOrigSequence().subSequence(getEnd(), beforeWord.getStart());
    }
}
