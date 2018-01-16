package de.jambonna.lolpapers.core.model;

import org.junit.Test;
import static org.junit.Assert.*;


public class UrlsTest {
    

    @Test
    public void testNormalizeForPubRequestPath() {
        Urls urls = Urls.getInstance();
        
        assertEquals("", urls.normalizeForPubRequestPath(""));
        assertEquals("abc", urls.normalizeForPubRequestPath("aBc"));
        assertEquals("heho", urls.normalizeForPubRequestPath("héhô"));
        assertEquals("abc-def34-ghi_jkl-mno", urls.normalizeForPubRequestPath("abc def34:ghi_jkl-mno"));
        assertEquals("bla34-bla", urls.normalizeForPubRequestPath(" ( bla34, ... blÀ ..)!  "));
        assertEquals("", urls.normalizeForPubRequestPath("  !!?, ("));
    }

    @Test
    public void testGetFinalPubRequestPath() {
        Urls urls = Urls.getInstance();

        assertEquals("abc-def", urls.getFinalPubRequestPath("abc-def", "", 30));
        assertEquals("abc-def-1", urls.getFinalPubRequestPath("abc-def", "-1", 30));
        assertEquals("abc-def-1", urls.getFinalPubRequestPath("abc-def", "-1", 9));
        assertEquals("abc-de-1", urls.getFinalPubRequestPath("abc-def", "-1", 8));
        assertEquals("abc-d-1", urls.getFinalPubRequestPath("abc-def", "-1", 7));
        assertEquals("abc-1", urls.getFinalPubRequestPath("abc-def", "-1", 6));
        assertEquals("abc-d", urls.getFinalPubRequestPath("abc-def", "", 5));
        assertEquals("abc", urls.getFinalPubRequestPath("abc-def", "", 4));
        assertEquals("abc", urls.getFinalPubRequestPath("abc-def", "", 3));
        assertEquals("-wx", urls.getFinalPubRequestPath("abc-def", "-wxyz", 3));
    }

    
}
