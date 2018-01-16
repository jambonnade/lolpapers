package de.jambonna.lolpapers.ci.webdriver;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Scenario1Test extends WebdriverTestAbstract {
    private static final Logger logger = LoggerFactory.getLogger(Scenario1Test.class);

    private PhantomJSDriver d;
    private String step;
    private int screenCount;

    
    @Test
    public void testScenario1() throws Exception {
        setDefaultLanguageHeader();
        setWithDevHeader(true);
        setRngSeedHeader(1234L);

        d = createPhantomJSDriver();
        try {
            initBrowserForLolpapers(d);
            
            goTo("");
            devUserConnect(d, "2");
            
            tryUser2Replacement();

            final String editUrl = "fr/article/template?id=3";
            goTo("fr/article/template/new");
            assertEquals(editUrl, getCurrentRelUrl(d));
//            goTo(editUrl);

            prepareUser2Article();
            continueUser2Article();
            replacementsUser2();
            createTemplatesUser2();

        } finally {
            d.quit();
        }
        
        d = createPhantomJSDriver();
        try {
            initBrowserForLolpapers(d);
            
            goTo("");
            devUserConnect(d, "1");
            
            replaceUser1();
            checkArticles();
            user1Votes();
        } finally {
            d.quit();
        }

        d = createPhantomJSDriver();
        try {
            initBrowserForLolpapers(d);
            
            goTo("");
            devUserConnect(d, "2");
            
            user2Votes();
        } finally {
            d.quit();
        }

    }
    
    protected void tryUser2Replacement() throws Exception {
        setStep("00user2tryreplace");
        goTo("fr/replacement");
        screen();
        assertEquals(
                "Vous ne pouvez plus effectuer de remplacements. Créer un article pour pouvoir effectuer de nouveaux remplacements.",
                findElement(".container.main .well p:first-child").getText());
    }
    
    protected void prepareUser2Article() throws Exception {
        
        WebElement e;
        
        setStep("01user2prepare");
        
        screen();        

        // It's the right article
        assertEquals("toilettes", findElement("a[data-wid='87']").getText());
        
        // Quickstart guide displayed
        assertTrue(findElement(".article-template-tuto").isDisplayed());
        findElement(".article-template-tuto a.tuto-close").click();
        assertFalse(findElement(".article-template-tuto").isDisplayed());

        // Placeholders count
        assertEquals("0", findElement("nav.navbar .placeholders-count").getText());
        assertEquals("14", findElement("nav.navbar .placeholders-count-min").getText());
        
        // Save menu disabled because nothing changed
        assertTrue(elementExists("nav.navbar .save-menu.disabled"));
        // Finish menu disabled because not enough placeholders
        assertTrue(elementExists("nav.navbar .finish-menu.disabled"));
        
        
        
        // Click on "Harry" in title part
        assertFalse(elementExists(".selection-popover"));
        findElement("a[data-wid='6']").click();
        waitPopoverAnim();
        screen();
        assertTrue(findElement(".selection-popover").isDisplayed());
        // Check type popover help
        moveMouseTo(".selection-popover .st-choice[data-st='sn']");
        waitPopoverAnim();
        screen();
        // Expand selection to the left
        findElement("a[data-wid='4']").click();
        screen();
        // Create placeholder
        assertFalse(findElement(".template-edit-placeholder").isDisplayed());
        findElement(".selection-popover .st-choice[data-st='sn']").click();
        waitPopoverAnim();
        screen();
        assertFalse(elementExists(".selection-popover"));
        assertTrue(findElement(".template-edit-placeholder").isDisplayed());
        // Check a few flags and elements in the placeholder popup
        assertEquals("le prince Harry", findElement(".template-edit-placeholder blockquote.orig-text").getText());
        assertTrue(findElement(".template-edit-placeholder .context-samples .invalid-msg").isDisplayed());
        assertTrue(elementExists(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.context .sdef-flag.active[data-flag='cn0']"));
        assertTrue(elementExists(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.context .sdef-flag.active[data-flag='cg0']"));
        assertFalse(elementExists(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.replacement .sdef-attr.attr-type .sdef-flag.active"));
        // Set all required attributes and show a flag popover
        findElement(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.context .sdef-flag[data-flag='cnp']").click();
        findElement(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.definition .sdef-flag[data-flag='ti']").click();
        findElement(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.definition .sdef-flag[data-flag='kmj']").click();
        moveMouseTo(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.context .sdef-flag[data-flag='cc1']");
        // ^ can't make work the hover, whatever
        waitPopoverAnim();
        screen();
        assertFalse(elementExists(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.context .sdef-flag.active[data-flag='cn0']"));
        assertTrue(elementExists(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.context .sdef-flag.active[data-flag='cnp']"));
        assertTrue(elementExists(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.replacement .sdef-flag.active[data-flag='ti']"));
        assertFalse(findElement(".template-edit-placeholder .context-samples .invalid-msg").isDisplayed());
        assertEquals("(...) sont tombé(e)s par terre", findElement(".template-edit-placeholder .context-samples li").getText());
        
        findElement(".template-edit-placeholder .btn[data-dismiss='modal']").click();
        waitPopoverAnim();
        screen();
        assertFalse(findElement(".template-edit-placeholder").isDisplayed());
        assertEquals("Meghan Markle et #1 SN P TI étaient en France pour le jour de l’ an",
                findElement(".title-part .template-edit-part").getText());
        
        // Save menu enabled because something changed
        assertFalse(elementExists("nav.navbar .save-menu.disabled"));
        
        // Placeholder #2 (voyagé)
        findElement("a[data-wid='18']").click();
        findElement(".selection-popover .st-choice[data-st='v']").click();
        waitPopoverAnim();
        findElement(".template-edit-placeholder .placeholder-sdef.st-v .sdef.context .sdef-flag[data-flag='cs3p']").click();
        findElement(".template-edit-placeholder .placeholder-sdef.st-v .sdef.context .sdef-flag[data-flag='cppaa']").click();
        screen();
        findElement(".template-edit-placeholder .btn[data-dismiss='modal']").click();

        // Placeholder #3 (le prince Harry)
        findElement("a[data-wid='25']").click();
        findElement("a[data-wid='27']").click();
        // Selection outside sentence : ignored
        findElement("a[data-wid='45']").click();
        findElement("a[data-wid='20']").click();
        waitPopoverAnim();
        findElement(".selection-popover .use-placeholder .btn.dropdown-toggle").click();
        screen();
        // Use placeholder #1
        e = findElement(".selection-popover .use-placeholder .dropdown-menu li:first-child a");
        assertEquals("#1 le prince Harry", e.getText());
        e.click();
        waitPopoverAnim();
        screen();
        
        // Finish menu still disabled
        assertTrue(elementExists("nav.navbar .finish-menu.disabled"));
        assertEquals("3", findElement("nav.navbar .placeholders-count").getText());
        
        // Erase mode
        findElement("nav.navbar .erase-mode-menu a").click();
        moveMouseTo("a[data-wid='135']");
        screen();
        // Removing the block where this word is
        findElement("a[data-wid='135']").click();
        // Clicking on the block directly (not on the text) works too
        findElement("p[data-blkid='70']").click();
        screen();
        
        // Min placeholder count decreased
        assertEquals("8", findElement("nav.navbar .placeholders-count-min").getText());

        // Save
        assertFalse(elementExists("nav.navbar .save-menu.disabled"));
        findElement("nav.navbar .save-menu a").click();
        wait(500);
        screen();
        (new WebDriverWait(d, 10))
             .until(ExpectedConditions.invisibilityOfElementLocated(
                     By.cssSelector(".main-overlay")));
        wait(300);
        screen();
        assertTrue(elementExists("nav.navbar .save-menu.disabled"));
        
        d.navigate().refresh();
    }
    
    protected void continueUser2Article() throws Exception {
        setStep("02user2continue");
        
        screen();
        
        assertEquals("3", findElement("nav.navbar .placeholders-count").getText());
        assertEquals("8", findElement("nav.navbar .placeholders-count-min").getText());
        assertTrue(elementExists("nav.navbar .finish-menu.disabled"));

        findElement("nav.navbar .erase-mode-menu a").click();
        findElement("p[data-blkid='66']").click();
        findElement("p[data-blkid='72']").click();
        findElement("nav.navbar .erase-mode-menu a").click();
        screen();
        
        // Placeholder #4 (couple princier)
        findElement("a[data-wid='54']").click();
        findElement("a[data-wid='55']").click();
        findElement(".selection-popover .st-choice[data-st='n']").click();
        waitPopoverAnim();
        screen(); // show every preselected flags
        assertTrue(elementExists(".template-edit-placeholder .placeholder-sdef.st-n .sdef.context .sdef-flag.active[data-flag='cns']"));
        assertTrue(elementExists(".template-edit-placeholder .placeholder-sdef.st-n .sdef.context .sdef-flag.active[data-flag='cgm']"));
        assertTrue(elementExists(".template-edit-placeholder .placeholder-sdef.st-n .sdef.context .sdef-flag.active[data-flag='cdd']"));
        findElement(".template-edit-placeholder .placeholder-sdef.st-n .sdef.definition .sdef-flag[data-flag='ta']").click();
        findElement(".template-edit-placeholder .placeholder-sdef.st-n .sdef.definition .sdef-flag[data-flag='c1']").click();
        screen();
        findElement(".template-edit-placeholder .btn[data-dismiss='modal']").click();
        
        // Placeholder #5 (dans le Sud Est)
        findElement("a[data-wid='37']").click();
        findElement("a[data-wid='39']").click();
        findElement(".selection-popover .st-choice[data-st='cc']").click();
        findElement(".template-edit-placeholder .reusable-def-flag").click();
        findElement(".template-edit-placeholder .placeholder-sdef.st-cc .sdef.replacement .sdef-flag[data-flag='ttps']").click();
        findElement(".template-edit-placeholder .placeholder-sdef.st-cc .sdef.replacement .sdef-flag[data-flag='tother']").click();
        waitPopoverAnim();
        screen();
        findElement(".template-edit-placeholder .btn[data-dismiss='modal']").click();
        
        screen();        
        assertFalse(elementExists("nav.navbar .finish-menu.disabled"));
        
        findElement("nav.navbar .finish-menu a").click();
        
        // #2 invalid
        waitPopoverAnim();
        screen();
        assertTrue(findElement(".template-edit-placeholder .alert-attr-not-set").isDisplayed());
        assertEquals("Emplacement #2", findElement(".template-edit-placeholder .modal-title").getText());
        findElement(".template-edit-placeholder .placeholder-sdef.st-v .sdef.context .sdef-flag[data-flag='ccod0']").click();
        // Changing flags removes validation alerts
        assertFalse(findElement(".template-edit-placeholder .alert-attr-not-set").isDisplayed());

        // Editing #3 (#1) by clicking in article view
        findElement(".template-edit-placeholder .article-view .placeholder", 2).click();
        screen();
        assertEquals("Emplacement #3 basé sur #1", findElement(".template-edit-placeholder .modal-title").getText());
        findElement(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.context .sdef-flag[data-flag='cns']").click();
        findElement(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.definition .sdef-flag[data-flag='ta']").click();
        findElement(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.definition .sdef-flag[data-flag='dd']").click();
        findElement(".template-edit-placeholder .placeholder-sdef.st-sn .sdef.replacement .sdef-flag[data-flag='ti']").click();
        findElement(".template-edit-placeholder .btn[data-dismiss='modal']").click();

        screen();
        assertEquals("5", findElement("nav.navbar .placeholders-count").getText());
        assertEquals("5", findElement("nav.navbar .placeholders-count-min").getText());
        assertFalse(elementExists("nav.navbar .finish-menu.disabled"));

        findElement("nav.navbar .finish-menu a").click();
        wait(500);
        screen();
        // Wait for back to home
        (new WebDriverWait(d, 10))
             .until(ExpectedConditions.textToBePresentInElementLocated(
                     By.cssSelector("h1"), "Bienvenue à LolPapers FR."));
    }
    
    protected void replacementsUser2() throws Exception {
        setStep("03user2replace");
        // Badge indicating the number
        assertEquals("Remplacer 4", findElement(".navbar-nav > li", 3).getText());
        
        goTo("fr/replacement");
        for (int n = 0; n < 4; n++) {
            screen();
            assertEquals("Remplacer " + (4 - n), findElement(".navbar-nav > li", 3).getText());
            findElement("input[name='user_text']").sendKeys("zzz");
            findElement("form.fill-replacement-form").submit();
        }
        screen();
        assertEquals("Remplacer", findElement(".navbar-nav > li", 3).getText());
        assertEquals(
            "Vous ne pouvez plus effectuer de remplacements. Créer un article pour pouvoir effectuer de nouveaux remplacements.",
            findElement(".container.main .well p:first-child").getText());
    }
    
    protected void createTemplatesUser2() throws Exception {
        setStep("04user2create");

        goTo("fr/article/template/new");
        screen();
        assertEquals("fr/article/template?id=4", getCurrentRelUrl(d));
        goTo("fr/article/template/new");
        screen();
        assertEquals("fr/article/template?id=5", getCurrentRelUrl(d));
        goTo("fr/article/template/new");
        screen();
        assertEquals("Vous ne pouvez pas créer un article pour le moment.",
            findElement(".alert.alert-danger").getText());
    }

    protected void replaceUser1() throws Exception {
        setStep("05user1replace");
        screen();
        // The 4 real placeholders in the template created by user 2
        assertEquals("Remplacer 4", findElement(".navbar-nav > li", 3).getText());
        
        goTo("fr/replacement");
        screen();
                
        // Check content
        assertEquals(
                "Veuillez saisir un syntagme nominal avec les caractéristiques suivantes :",
                findElement(".fill-text-msg").getText());
        assertEquals(
                "Attributs :\n" +
                "De type animé ou inanimé\n" +
                "Déterminant défini\n" +
                "Avec ou sans compléments\n" +
                "Contexte :\n" +
                "Indications genre : aucune\n" +
                "Indications nombre : singulier\n" +
                "Référents potentiels : aucun",
                findElement(".fill-text-details").getText());
        assertEquals(
                "Mise en situation :\n" +
                "(...) est tombé·e par terre",
                findElement(".ctx-samples").getText());
        
        // Check popovers
        moveMouseTo(".fill-text-msg .st-sn");
        waitPopoverAnim();
        screen();
        assertEquals(
                "Nom avec déterminant inclus ou tout ce qui peut constituer un sujet ou COD\n" +
                "ex : un cheval, la dame à la buche, Marilyn Monroe", 
                findElement(".popover .popover-content").getText());
        moveMouseTo(".fill-text-details .flag-c1");
        waitPopoverAnim();
        screen();
        assertEquals(
                "Contient des compléments\n" +
                "ex : une voiture rouge, le petit bonhomme en mousse qui rate le plongeoir",
                findElement(".popover .popover-content").getText());
        moveMouseTo(".fill-text-details .attr-cref");
        waitPopoverAnim();
        screen();
        assertEquals(
                "Éléments pouvant être référencés dans le texte à remplacer (pronoms, accords, déterminants possessifs...)",
                findElement(".popover .popover-content").getText());
        
        assertTrue(elementExists(".submit-btn.disabled"));
        WebElement textInput = findElement("input[name='user_text']");
        WebElement form = findElement("form.fill-replacement-form");
        form.submit();
        textInput.sendKeys("   , ,! - .- ");
        assertTrue(elementExists(".submit-btn.disabled"));
        form.submit();
        textInput.clear();
        textInput.sendKeys("  le  pape  ");
        screen();
        assertFalse(elementExists(".submit-btn.disabled"));
        assertEquals(
                "Mise en situation :\n" +
                "le pape est tombé·e par terre",
                findElement(".ctx-samples").getText());

        assertFalse(findElement(".placeholder-sdef .sdef-attr.attr-kmj").isDisplayed());
        assertFalse(findElement(".placeholder-sdef .sdef-attr.attr-ha").isDisplayed());
        textInput.clear();
        textInput.sendKeys("  Josiane Balasko  ");
        screen();
        assertTrue(findElement(".placeholder-sdef .sdef-attr.attr-kmj").isDisplayed());
        assertTrue(elementExists(".placeholder-sdef .sdef-flag.active[data-flag='kmj']"));
        assertFalse(findElement(".placeholder-sdef .sdef-attr.attr-ha").isDisplayed());
        assertFalse(elementExists(".submit-btn.disabled"));
        // Check attr flags selection works
        findElement(".placeholder-sdef .sdef-flag[data-flag='nomj']").click();
        screen();
        assertFalse(elementExists(".submit-btn.disabled"));
        assertFalse(elementExists(".placeholder-sdef .sdef-flag.active[data-flag='kmj']"));
        assertTrue(elementExists(".placeholder-sdef .sdef-flag.active[data-flag='nomj']"));
        findElement(".placeholder-sdef .sdef-flag[data-flag='nomj']").click();
        screen();
        assertFalse(elementExists(".placeholder-sdef .sdef-flag.active[data-flag='nomj']"));
        assertTrue(elementExists(".submit-btn.disabled"));
        form.submit();
        findElement(".placeholder-sdef .sdef-flag[data-flag='kmj']").click();
        textInput.clear();
        textInput.sendKeys("  Homer  Simpson  ");
        screen();
        assertTrue(findElement(".placeholder-sdef .sdef-attr.attr-kmj").isDisplayed());
        assertTrue(findElement(".placeholder-sdef .sdef-attr.attr-ha").isDisplayed());
        assertTrue(elementExists(".submit-btn.disabled"));
        findElement(".placeholder-sdef .sdef-flag[data-flag='hm']").click();
        screen();
        assertFalse(elementExists(".submit-btn.disabled"));
        form.submit();

        
        screen();

        assertEquals(
                "Veuillez saisir un verbe/syntagme verbal avec les caractéristiques suivantes :",
                findElement(".fill-text-msg").getText());
        assertEquals(
                "Attributs :\n" +
                "Au participe passé\n" +
                "Sans marques de négation\n" +
                "Sans compléments circonstanciels\n" +
                "Contexte :\n" +
                "Sujet : animé pluriel\n" +
                "Pas de COD dans le contexte\n" +
                "Contexte participe passé : temps composé voix active (aux. avoir)\n" +
                "Autres référents potentiels : aucun",
                findElement(".fill-text-details").getText());
        assertEquals(
                "Mise en situation :\n" +
                "il·elle·s ont (...)",
                findElement(".ctx-samples").getText());

        // Text input is focused, so send keys directly. \n submits the form
        d.getKeyboard().sendKeys("mangé des chips\n");
        
        screen();

        assertEquals(
                "Veuillez saisir un complément circonstanciel avec les caractéristiques suivantes :",
                findElement(".fill-text-msg").getText());
        assertEquals(
                "Attributs :\n" +
                "De lieu\n" +
                "Contexte :\n" +
                "Référents potentiels : aucun",
                findElement(".fill-text-details").getText());
        assertFalse(findElement(".ctx-samples").isDisplayed());

        d.getKeyboard().sendKeys("au Parc Asterix\n");
        
        screen();

        assertEquals(
                "Veuillez saisir un nom commun avec les caractéristiques suivantes :",
                findElement(".fill-text-msg").getText());
        assertEquals(
                "Attributs :\n" +
                "De type animé\n" +
                "Avec compléments\n" +
                "Contexte :\n" +
                "Indications genre : masculin\n" +
                "Indications nombre : singulier\n" +
                "Nom introduit par un article défini\n" +
                "Référents potentiels : aucun",
                findElement(".fill-text-details").getText());
        assertEquals("le", findElement(".fill-text-part .ctx-prefix").getText());
        assertEquals(
                "Mise en situation :\n" +
                "le (...) est décédé",
                findElement(".ctx-samples").getText());

        // Single word will fail server side
        d.getKeyboard().sendKeys("pape\n");
        
        screen();
        assertEquals("Remplacement invalide", findElement(".alert.alert-danger").getText());
        
        // Elision in context
        d.getKeyboard().sendKeys("étudiant en lettres");
        screen();
        assertEquals("l'", findElement(".fill-text-part .ctx-prefix").getText());
        assertEquals(
                "Mise en situation :\n" +
                "l'étudiant en lettres est décédé",
                findElement(".ctx-samples").getText());
        d.getKeyboard().sendKeys("\n");
        
        screen();
        assertEquals(
                "fr/actu/meghan-markle-et-homer-simpson-etaient-en-france-pour-le-jour-de-l-an",
                getCurrentRelUrl(d));
        
        
        // No more placeholders
        goTo("fr/replacement");
        screen();
        assertEquals(
            "Il n'y a plus d'emplacements à remplacer. Les utilisateurs doivent préparer de nouveaux articles.",
            findElement(".container.main .well p:first-child").getText());
        
    }
    
    protected void checkArticles() throws Exception {
        final String artTitle = "Meghan Markle et Homer Simpson étaient en France pour le jour de l’ an";
        final String artUrl = 
                "fr/actu/meghan-markle-et-homer-simpson-etaient-en-france-pour-le-jour-de-l-an";
        final String artDescr = "Ils ont mangé des chips en classe éco…  ";

        setStep("06articles");
        
        goTo("fr/");
        screen();
        assertEquals(artTitle, findElement(".article-list div h3").getText());
        assertEquals(artDescr, findElement(".article-list div .descr-text").getText());
        assertFalse(elementExists(".article-list div .infos-line .label"));
        // "Top articles" empty
        assertEquals(0, findElement(".article-list", 1).findElements(By.tagName("div")).size());
        
        findElement(".article-list div h3 a").click();        
        screen();
        assertEquals(artUrl, getCurrentRelUrl(d));
        assertEquals(artTitle, findElement("h1").getText());
        assertEquals(artDescr, findElement(".article-descr").getText());
        assertEquals(
                "Meghan Markle et Homer Simpson ont fêté le passage à 2018 en France, précisément au Parc Asterix. Et comme le précise le Daily Mail, c’est en classe éco que l'étudiant en lettres a voyagé ! Oui, pas de jet privé ni de classe affaire pour les futurs époux, mais le dernier rang d’un avion de ligne de la British Airways à côté des toilettes, comme le précise la publication.",
                findElement(".article-body").getText());
        assertTrue(findElement(".article-infos").getText()
                .startsWith("Créé par Utilisateur 2 , complété par Utilisateur 1 ,"));
        
        WebElement crumbItem;
        crumbItem = findElement(".breadcrumb li", 2);
        assertEquals("Actualités", crumbItem.getText());
        crumbItem.findElement(By.tagName("a")).click();
        
        // Category has article
        screen();
        assertEquals(artTitle, findElement(".article-list div h3").getText());
        assertEquals(artDescr, findElement(".article-list div .descr-text").getText());

        // Category still empty
        goTo("fr/tech/");
        screen();
        assertFalse(elementExists(".article-list div"));
    }
    
    protected void user1Votes() throws Exception {
        setStep("07user1votes");
        goTo("fr/actu/meghan-markle-et-homer-simpson-etaient-en-france-pour-le-jour-de-l-an");
        assertFalse(elementExists(".upvote-btn.active"));
        findElement(".upvote-btn").click();
        screen();
        assertTrue(elementExists(".upvote-btn.active"));

        goTo("fr/");
        screen();
        assertEquals("1", findElement(".article-list div .infos-line .label").getText());
    }
    
    protected void user2Votes() throws Exception {
        final String artUrl = "fr/actu/meghan-markle-et-homer-simpson-etaient-en-france-pour-le-jour-de-l-an";
        
        setStep("08user2votes");
        
        goTo(artUrl);
        assertFalse(elementExists(".upvote-btn.active"));
        findElement(".upvote-btn").click();
        screen();
        assertTrue(elementExists(".upvote-btn.active"));

        goTo("fr/");
        screen();
        assertEquals("2", findElement(".article-list div .infos-line .label").getText());
        
        goTo(artUrl);
        assertTrue(elementExists(".upvote-btn.active"));
        findElement(".upvote-btn").click();
        screen();
        assertFalse(elementExists(".upvote-btn.active"));

        goTo("fr/");
        screen();
        assertEquals("1", findElement(".article-list div .infos-line .label").getText());
    }
    
    protected WebElement findElement(String cssSelector) {
        return d.findElementByCssSelector(cssSelector);
    }
    protected WebElement findElement(String cssSelector, int num) {
        return d.findElementsByCssSelector(cssSelector).get(num);
    }
    protected boolean elementExists(String cssSelector) {
        return d.findElementsByCssSelector(cssSelector).size() > 0;
    }
    protected void moveMouseTo(String cssSelector) {
        (new Actions(d)).moveToElement(findElement(cssSelector)).perform();
    }
    
    protected void goTo(String relPath) {
        d.get(getAppBaseUrl() + relPath);
    }
    
    protected void setStep(String name) {
        step = name;
        screenCount = 0;
    }
    
    protected void screen() throws IOException {
        screenshot(d, String.format("scenario1-%s-%02d", step, ++screenCount));
    }
    
}
