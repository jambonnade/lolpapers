package de.jambonna.lolpapers.core.model.lang;

import de.jambonna.lolpapers.core.model.text.ContentProcessor;
import de.jambonna.lolpapers.core.model.text.ReplaceContext;
import de.jambonna.lolpapers.core.model.text.Sentence;
import de.jambonna.lolpapers.core.model.text.TextRange;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.jsoup.nodes.Element;

/**
 * Contains all language-specific code. Not meant to be thread safe
 */
public interface Language {
    // Common syntagme type attribute / flaggs
    public static final String ATTR_COMMON_PHASE = "phase";
    public static final String FLAG_COMMON_PHASE_DEF = "def";
    public static final String FLAG_COMMON_PHASE_REPL = "repl";

    public static final String ATTR_COMMON_REUSABLE = "reu";
    public static final String FLAG_COMMON_REUSABLE_OFF = "reu0";
    public static final String FLAG_COMMON_REUSABLE_ON = "reu1";

    
    /**
     * An identifying code for the Language. May be similar to the 
     * locale/language code but not necessarily
     * 
     * @return the Language code
     */
    public String getCode();
    
    /**
     * Returns the associated Locale
     * 
     * @return the associated Locale
     */
    public Locale getLocale();
    
    /**
     * Returns the "syntagme types" for this language. The keys are the type codes.
     * 
     * @return the syntagme types for this language
     */
    public Map<String, SyntagmeType> getSyntagmeTypes();
    
    /**
     * Convinience method to get a SyntagmeType by code
     * 
     * @param code the SyntagmeType code
     * @return the SyntagmeType
     */
    public SyntagmeType getSyntagmeType(String code);
    
    /**
     * Serialize all data related to this language into a JSON document
     * 
     * @param pretty set true to get a formtted output
     * @return the JSON document
     */
    public String toJson(boolean pretty);
    
    /**
     * The ContentProcessor object associated to this language
     * 
     * @return a ContentProcessor object
     */
    public ContentProcessor getContentProcessor();
    
    /**
     * Extracts the words in the given text according to the language rules.
     * The created TextRanges have the given CharSequence as parent and positions
     * are related to this CharSequence
     * 
     * @param text a CharSequence
     * @return a list of TextRange. Can be empty
     */
    public List<TextRange> extractWords(CharSequence text);
    
    /**
     * Returns the text after the given word that ends the current sentence (if any).
     * The text is the one following the TextRange in its parent CharSequence
     * 
     * @param word a TextRange matching a word, like provided by extractWords()
     * @return a subsequence of the TextRange parent CharSequence 
     *      that ends the sentence (can be one or more characters) 
     *      or null if not followed by a sentence end mark.
     */
    public CharSequence getSentenceEndMarkFollowingWord(TextRange word);
    
    /**
     * Convenience method extracting words and sentences in the given text.
     * getSentenceEndMarkFollowingWord() is used to delimitate sentences, the
     * sentence boundaries include the sentence end marks (= it includes puncts).
     * 
     * @param text the input text
     * @return an ordered list of Sentence objects using the 
     *      given text as parent CharSequence
     */
    public List<Sentence> extractSentences(CharSequence text);
    
    /**
     * To know if the input string is considered to have some real text or 
     * if it is only space and special characters.
     * 
     * @param text the input text
     * @return true if the text seem to have at least one word
     */
    public boolean hasText(CharSequence text);
    
    /**
     * Removes diacritical marks (accents).
     * 
     * @param text the input text
     * @return the text without diacritical marks
     */
    public String removeTextDiacritricalMarks(CharSequence text);
    
    /**
     * Returns the text associated with this SyntagmeDefinition, normalized so
     * that it can be reused in another context.
     * 
     * @param sd the SyntagmeDefinition
     * @return the normalized text
     */
    public String normalizeSyntagmeText(SyntagmeDefinition sd);
    
    /**
     * Does the text changes (preceding text and replacement text) 
     * in the placeholder replacement process.
     * 
     * @param ctx holds the context and the result of this process
     */
    public void alterReplacementText(ReplaceContext ctx);
    
    
//    public Element getHTMLSyntagmeMainDescription(SyntagmeDefinitionAbstract sd);
    
    /**
     * Gets the replacement infos as an HTML list to be presented to the final user,
     * already localized.
     * 
     * @param sdr the related SyntagmeReplacementDefinition
     * @return the root Element of the HTML list (ul)
     */
    public Element getSDefReplacementHTMLInfo(SyntagmeReplacementDefinition sdr);
    
    /**
     * Gets the context infos as an HTML list to be presented to the final user,
     * already localized.
     * 
     * @param sd the related SyntagmeDefinition
     * @return the root Element of the HTML list (ul)
     */
    public Element getSDefContextHTMLInfo(SyntagmeDefinition sd);
}
