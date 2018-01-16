package de.jambonna.lolpapers.core.app;

import de.jambonna.lolpapers.core.model.Category;
import de.jambonna.lolpapers.core.model.Url;
import de.jambonna.lolpapers.core.model.WebsiteEntity;
import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SharedDataTest {
    
    public SharedDataTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }


    @Test
    public void testUrlsGeneral() {
        SharedData sd = new SharedData();
        Url a = createUrl("url-a", "chemin/url-a.html", "a.jsp");
        Url b = createUrl("url-b", "mon-url-b.html", "b.jsp");
        
        String infosA = "url-a, chemin/url-a.html, a.jsp, false";
        String infosB = "url-b, mon-url-b.html, b.jsp, false";
        
        sd.resetUrls(Arrays.asList(a, b));
        
        assertUrlEquals(infosA, sd.getUrlForRequestPath("chemin/url-a.html"));
        assertUrlEquals(infosA, sd.getUrlForIdentifier("url-a"));

        Url rb = sd.getUrlForRequestPath("mon-url-b.html");
        assertUrlEquals(infosB, rb);
        
        // Changing returned object doesn't change the url registry
        rb.setDestPath("zzz");        
        assertUrlEquals(infosB, sd.getUrlForRequestPath("mon-url-b.html"));

        // Changing input object doesn't change the url registry
        b.setDestPath("xxx");
        assertUrlEquals(infosB, sd.getUrlForRequestPath("mon-url-b.html"));
        assertUrlEquals(infosB, sd.getUrlForIdentifier("url-b"));

        // Update urls, add new ones, add history ones
        b =         createUrl("url-b", "new-url-b.html", "b.jsp");
        Url b2 =    createUrl("url-b", "mon-url-b.html", "new-url-b.html");
        b2.setHistory(true);
        Url c =     createUrl("url-c", "chemin/c/", "c.jsp");
        Url c2 =    createUrl("url-c", "chemin/c", "chemin/c/");
        c2.setHistory(true);

        infosB =            "url-b, new-url-b.html, b.jsp, false";
        String infosB2 =    "url-b, mon-url-b.html, new-url-b.html, true";
        String infosC =     "url-c, chemin/c/, c.jsp, false";
        String infosC2 =    "url-c, chemin/c, chemin/c/, true";

        
        sd.updateUrls(Arrays.asList(b, b2, c, c2));
        
        assertUrlEquals(infosA, sd.getUrlForRequestPath("chemin/url-a.html"));
        assertUrlEquals(infosA, sd.getUrlForIdentifier("url-a"));
        
        assertUrlEquals(infosB, sd.getUrlForRequestPath("new-url-b.html"));
        assertUrlEquals(infosB, sd.getUrlForIdentifier("url-b"));
        assertUrlEquals(infosB2, sd.getUrlForRequestPath("mon-url-b.html"));

        assertUrlEquals(infosC, sd.getUrlForRequestPath("chemin/c/"));
        assertUrlEquals(infosC, sd.getUrlForIdentifier("url-c"));
        assertUrlEquals(infosC2, sd.getUrlForRequestPath("chemin/c"));
        
        // Update url entry data (like destination)
        sd.updateUrls(Arrays.asList(createUrl("url-b", "new-url-b.html", "bbb.jsp")));
        infosB = "url-b, new-url-b.html, bbb.jsp, false";
        
        assertUrlEquals(infosB, sd.getUrlForRequestPath("new-url-b.html"));
        assertUrlEquals(infosB, sd.getUrlForIdentifier("url-b"));
        assertUrlEquals(infosB2, sd.getUrlForRequestPath("mon-url-b.html"));

        assertUrlEquals(infosA, sd.getUrlForRequestPath("chemin/url-a.html"));
        assertUrlEquals(infosA, sd.getUrlForIdentifier("url-a"));

    }


    
    public Url createUrl(String identifier, String requestPath, String destPath) {
        Url url = new Url();
        url.setIdentifier(identifier);
        url.setRequestPath(requestPath);
        url.setDestPath(destPath);
        return url;
    }
    
    public void assertUrlEquals(String expected, Url u) {
        assertEquals(expected, 
                String.format("%s, %s, %s, %b", 
                    u.getIdentifier(), u.getRequestPath(), u.getDestPath(), u.isHistory()));
    }
    
    @Test
    public void testWebsitesGeneral() {
        SharedData sd = new SharedData();
        
        WebsiteEntity w1 = new WebsiteEntity();
        w1.setWebsiteId(1L);
        w1.setCode("w1");
        w1.setName("Website 1");
        w1.setPageName("Site1");
        w1.setUrlKey("w1");
        w1.setLocaleCode("fr_FR");
        
        Category c1 = new Category();
        c1.setWebsite(w1);
        c1.setCategoryId(1L);
        c1.setCode("c1");
        c1.setTitle("Category 1");
        c1.setUrlKey("category-1");
        
        Category c2 = new Category();
        c2.setWebsite(w1);
        c2.setCategoryId(2L);
        c2.setCode("c2");
        c2.setTitle("Category 2");
        c2.setUrlKey("category-2");
        
        WebsiteEntity w2 = new WebsiteEntity();
        w2.setWebsiteId(2L);
        w2.setCode("w2");
        w2.setName("Website 2");
        w2.setPageName("Site2");
        w2.setUrlKey("w2");
        w2.setLocaleCode("en_US");
        
        Category c3 = new Category();
        c3.setWebsite(w2);
        c3.setCategoryId(3L);
        c3.setCode("c3");
        c3.setTitle("Category 3");
        c3.setUrlKey("category-3");
        
        sd.updateWebsites(Arrays.asList(c1, c2, c3));

        assertEquals("Website 1", sd.getWebsite(1L).getName());
        assertEquals("Site1", sd.getWebsite("w1").getPageName());
        assertEquals("w1/category-2/", sd.getWebsite("w1").getCategories()[1].getRequestPath());
        assertEquals("Site2", sd.getWebsite(2L).getPageName());
        assertEquals("Website 2", sd.getWebsite("w2").getName());
    }
}
