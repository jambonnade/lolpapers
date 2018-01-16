package de.jambonna.lolpapers.ci.webdriver;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BasicStuffTest extends WebdriverTestAbstract {
    private static final Logger logger = LoggerFactory.getLogger(BasicStuffTest.class);
    
    @Before
    public void setDefaults() {
        setDefaultLanguageHeader();
    }

    @Test
    public void testSimple() throws IOException {
        PhantomJSDriver d;
        
        d = createPhantomJSDriver();
        try {
            initBrowserForLolpapers(d);
            d.get(getAppBaseUrl());
            
            screenshot(d, "simple01");
            
            assertEquals("fr/", getCurrentRelUrl(d));
            
            assertEquals("Bienvenue à LolPapers FR.",
                    d.findElementByCssSelector("h1").getText());
            assertEquals("Derniers articles",
                    d.findElementsByCssSelector("h2").get(0).getText());
            assertEquals("Connexion",
                    d.findElementsByCssSelector(".navbar-nav > li").get(1).getText());

            d.findElementByCssSelector(".navbar-nav > li:first-child a").click();
            screenshot(d, "simple02");
            
//            logger.debug("clicking cat menu");
            d.findElementByCssSelector(".navbar .dropdown.open li:nth-child(2) a").click();
//            logger.debug("clicked");
            assertEquals("fr/actu/", getCurrentRelUrl(d));

            screenshot(d, "simple03");
            
            assertEquals("Actualités", d.findElementByCssSelector(".breadcrumb li:last-child a").getText());
            
            // Url not written with / => redirects
            d.get(getAppBaseUrl() + "fr/tech");
            assertEquals("fr/tech/", getCurrentRelUrl(d));
            
            screenshot(d, "simple04");

            assertEquals("Tech.", d.findElementByCssSelector(".breadcrumb li:last-child a").getText());
        } finally {
            d.quit();
        }
    }
    
    @Test
    public void testDevUserCantConnect() throws IOException {
        PhantomJSDriver d;
                
        d = createPhantomJSDriver();
        try {
            initBrowserForLolpapers(d);
            d.get(getAppBaseUrl());
            
            devUserConnect(d, "2");
                        
            screenshot(d, "devusercantconnect01");

            assertEquals("404 Resource non trouvée",
                    d.findElementByCssSelector("h1").getText());
            
        } finally {
            d.quit();
        }
    }
    
    @Test
    public void testDevUserConnect() throws IOException {
        PhantomJSDriver d;
        
        setWithDevHeader(true);
                
        d = createPhantomJSDriver();
        try {
            initBrowserForLolpapers(d);
            d.get(getAppBaseUrl());
            
            d.get(getAppBaseUrl() + "fr/tech/");
            
            devUserConnect(d, "2");

            // Returns to last visited page
            assertEquals("fr/tech/", getCurrentRelUrl(d));

            screenshot(d, "devuserconnect01");
            
            // Shows a success message
            assertEquals("Connecté à \"Utilisateur 2\".",
                    d.findElementByCssSelector(".alert.alert-success").getText());
            
            // 2nd menu is now "my account"
            assertEquals("Mon compte",
                    d.findElementsByCssSelector(".navbar-nav > li").get(1).getText());
            
            d.navigate().refresh();
            
            // Success message is now gone
            assertEquals(0, d.findElementsByCssSelector(".alert").size());
            
            screenshot(d, "devuserconnect02");

        } finally {
            d.quit();
        }
    }
    
    @Test
    public void testBrowserLanguages() throws IOException {
        PhantomJSDriver d;
        
        // Same result for unmanaged languages and english specifically
        String[] languageHeaders = {
            "es-ES,es;q=0.8,de-DE;q=0.5,de;q=0.3",
            "en-US,en;q=0.5"
        };
        int loopNum = 0;
        for (String languageHeader : languageHeaders) {
            setLanguageHeader(languageHeader);
            d = createPhantomJSDriver();
            try {
                initBrowserForLolpapers(d);
                d.get(getAppBaseUrl());

                assertEquals("fr/", getCurrentRelUrl(d));
                assertEquals("Welcome to LolPapers FR.",
                        d.findElementByCssSelector("h1").getText());
                assertEquals("Last articles",
                        d.findElementsByCssSelector("h2").get(0).getText());
                assertEquals("Login",
                        d.findElementsByCssSelector(".navbar-nav > li").get(1).getText());

                screenshot(d, "browser-languages-" + loopNum + "-01");

            } finally {
                d.quit();
            }
            loopNum++;
        }
    }
}
