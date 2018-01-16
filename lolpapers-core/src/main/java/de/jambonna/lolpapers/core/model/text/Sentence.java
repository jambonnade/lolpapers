package de.jambonna.lolpapers.core.model.text;

import java.util.LinkedList;
import java.util.List;

/**
 * Sentence data, for the needs of ContentProcessor
 */
public class Sentence {
    private final CharSequence origCS;
    private final List<TextRange> words;
    private Integer fromPos;
    private Integer toPos;
    private boolean withFinalPunct;


    public Sentence(CharSequence cs) {
        if (cs == null) {
            throw new IllegalArgumentException("Invalid orig sequence");
        }
        
        origCS = cs;
        words = new LinkedList<>();
    }

    public CharSequence getOrigCS() {
        return origCS;
    }

    public int getFromPos() {
        if (fromPos != null) {
            return fromPos;
        }
        if (words.size() > 0) {
            return words.get(0).getStart();
        }
        return 0;
    }

    public void setFromPos(int fromPos) {
        if (fromPos < 0 || fromPos > origCS.length()) {
            throw new IndexOutOfBoundsException("Invalid pos");
        }
        
        if (words.size() > 0) {
            if (fromPos > words.get(0).getStart()) {
                throw new IllegalArgumentException("Invalid pos");
            }
        }
        this.fromPos = fromPos;
    }

    public int getToPos() {
        if (toPos != null) {
            return toPos;
        }
        if (words.size() > 0) {
            return words.get(words.size() - 1).getEnd();
        }
        return origCS.length();
    }

    public void setToPos(int toPos) {
        if (toPos < 0 || toPos > origCS.length()) {
            throw new IndexOutOfBoundsException("Invalid pos");
        }
        
        if (words.size() > 0) {
            if (toPos < words.get(words.size() - 1).getEnd()) {
                throw new IllegalArgumentException("Invalid pos");
            }
        }
        this.toPos = toPos;
    }
    
    public CharSequence getSentenceSeqence() {
        return origCS.subSequence(getFromPos(), getToPos());
    }
    
    public void addWord(TextRange word) {
        if (word.getOrigSequence() != origCS) {
            throw new IllegalArgumentException("Invalid word");
        }
        words.add(word);
    }
    
    public List<TextRange> getWordList() {
        return words;
    }
    
    public int getWordCount() {
        return words.size();
    }

    public int getLcWordCount() {
        int num = 0;
        for (TextRange word : words) {
            if (!word.hasUc()) {
                num++;
            }
        }
        return num;
    }

    public boolean hasInitialUC() {
        if (words.size() > 0) {
            TextRange firstWord = words.get(0);
            return firstWord.hasOnlyFirstUc();
        }
        return false;
    }
    

    public boolean isWithFinalPunct() {
        return withFinalPunct;
    }

    public void setWithFinalPunct(boolean withFinalPunct) {
        this.withFinalPunct = withFinalPunct;
    }
    
    
    
    @Override
    public String toString() {
        return String.format("wc: %d, lwc: %d, in: %d, pt: %d", 
                getWordCount(), getLcWordCount(), 
                hasInitialUC() ? 1 : 0, isWithFinalPunct() ? 1 : 0);
    }
}
