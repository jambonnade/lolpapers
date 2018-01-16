package de.jambonna.lolpapers.core.model.text;

import de.jambonna.lolpapers.core.model.lang.Language;
import de.jambonna.lolpapers.core.model.lang.Languages;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TextTest {
    
    private static Language fr;
    
    
    @BeforeClass
    public static void setUpClass() {
        fr = Languages.getInstance().getFr();
    }


    @Test
    public void testCaseFlags() {
        checkCaseFlags("Abc", true, false, true, true);
        checkCaseFlags("abc", false, false, true, false);
        checkCaseFlags("abC", false, true, true, true);
        checkCaseFlags("ABC", true, true, false, true);
        
        checkCaseFlags("Àbê", true, false, true, true);
        checkCaseFlags("àbê", false, false, true, false);
        checkCaseFlags("àbÊ", false, true, true, true);
        checkCaseFlags("ÀBÊ", true, true, false, true);
        
        checkCaseFlags("A", true, false, false, true);
        checkCaseFlags("a", false, false, false, false);
        checkCaseFlags("Ô", true, false, false, true);
        checkCaseFlags("ô", false, false, false, false);
        checkCaseFlags("?", false, false, false, false);
        checkCaseFlags("", false, false, false, false);
        
        checkCaseFlags("-bc", false, false, true, false);
        checkCaseFlags("-bC", false, true, true, true);
        checkCaseFlags("A-!", true, false, false, true);
        checkCaseFlags("a-!", false, false, false, false);
        checkCaseFlags("_-!", false, false, false, false);
        
    }
    
    public void checkCaseFlags(String text, 
            boolean isFirstUc, boolean hasRemainingUc, boolean hasRemainingLc, boolean hasUc) {
        Text t = new Text(fr, text);
        assertEquals(isFirstUc, t.isFirstUc());
        assertEquals(hasRemainingUc, t.hasRemainingUc());
        assertEquals(hasRemainingLc, t.hasRemainingLc());
        assertEquals(hasUc, t.hasUc());
        
        // Check if hasUc still works if used first
        t = new Text(fr, text);
        assertEquals(hasUc, t.hasUc());
        
        
        // Same but with a textrange
        String globalText = "xx " + text + " xx";
        TextRange r = new TextRange(fr, globalText, 3, 3 + text.length());
        assertEquals(isFirstUc, r.isFirstUc());
        assertEquals(hasRemainingUc, r.hasRemainingUc());
        assertEquals(hasRemainingLc, r.hasRemainingLc());
        assertEquals(hasUc, r.hasUc());
        
        r = new TextRange(fr, globalText, 3, 3 + text.length());
        assertEquals(hasUc, r.hasUc());
    }
}
