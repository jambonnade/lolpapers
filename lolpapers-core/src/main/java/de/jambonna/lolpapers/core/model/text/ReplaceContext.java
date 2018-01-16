package de.jambonna.lolpapers.core.model.text;

import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinition;
import java.util.List;

/**
 * Result/context of Language.alterReplacementText()
 */
public class ReplaceContext {
    private final SyntagmeDefinition replacement;
    private final List<TextRange> precedingWords;
    private String newPrecedingText;
    private TextRange precedingWordToReplaceFrom;
    private String finalReplacementText;

    public ReplaceContext(SyntagmeDefinition replacement, List<TextRange> precedingWords) {
        if (replacement == null || replacement.getText() == null) {
            throw new IllegalArgumentException("Invalid replacement");
        }
        if (precedingWords == null) {
            throw new IllegalArgumentException("Invalid preceding words");
        }
        
        this.replacement = replacement;
        this.precedingWords = precedingWords;
        this.finalReplacementText = replacement.getText();
    }

    public SyntagmeDefinition getReplacement() {
        return replacement;
    }

    public List<TextRange> getPrecedingWords() {
        return precedingWords;
    }
    
    public boolean hasPrecedingWords() {
        return !precedingWords.isEmpty();
    }
    
    public TextRange getFirstPrecedingWord() {
        return precedingWords.isEmpty() ? null : precedingWords.get(0);
    }
    
    public TextRange getNthLastPrecedingWord(int posFromTheEnd) {
        int s = precedingWords.size();
        
        return s > 0 ? precedingWords.get(Math.max(0, s - posFromTheEnd)) : null;
    }

    public String getNewPrecedingText() {
        return newPrecedingText;
    }

    public TextRange getPrecedingWordToReplaceFrom() {
        return precedingWordToReplaceFrom;
    }
    
    public void setNewPrecedingText(String newPrecedingText, TextRange precedingWordToReplaceFrom) {
        if (!precedingWords.contains(precedingWordToReplaceFrom)) {
            throw new IllegalArgumentException("Invalid word to replace from");
        }
        this.precedingWordToReplaceFrom = precedingWordToReplaceFrom;
        this.newPrecedingText = newPrecedingText;
    }
    
    public String getNewTotalPrecedingText() {
        if (newPrecedingText != null && precedingWordToReplaceFrom != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(precedingWordToReplaceFrom.getOrigSequence()
                    .subSequence(0, precedingWordToReplaceFrom.getStart()));
            sb.append(newPrecedingText);
            return sb.toString();
        }
        return null;
    }

    public String getFinalReplacementText() {
        return finalReplacementText;
    }

    public void setFinalReplacementText(String finalReplacementText) {
        this.finalReplacementText = finalReplacementText;
    }
    
    
}
