package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.app.FakeApp;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.Ignore;

public class BaseArticleTest {
    
    @Test
    @Ignore("Manual quick testing")
    public void testGetArticleImage() throws AppException, IOException {
        Map<String, String> config = new HashMap<>();
        config.put("fakeAppRealVarPath", "/var/local/lolpapers");
        FakeApp app = new FakeApp(config);
        BaseArticle ba = new BaseArticle();
        ba.init(app);
        ba.setArticleFilesBaseName("ceb6079a-b701-4446-b0b3-8cc9c119e6b1");
        ba.setArticleImageOk(true);
        Path p;
        p = ba.getArticleImage(400, 100, true);
        System.out.println(p);
    }
    
    @Test
    @Ignore("Manual quick testing")
    public void testSetFromUrl() throws IOException {
        BaseArticle ba;
        WebsiteEntity w = new WebsiteEntity();
        w.setLocaleCode("fr_FR");
        Category c = new Category();
        c.setWebsite(w);
        ba = new BaseArticle();
        ba.setCategory(c);
        ba.setFromUrl("http://feedproxy.google.com/~r/KorbensBlog-UpgradeYourMind/~3/jOfBASZQaiQ/creer-chatbot.html");
        System.out.println(ba.getArticleCanonicalUrl());
        System.out.println(ba.getArticleSrcUrl());
        System.out.println(ba.getArticleTitle());
        System.out.println(ba.getArticleContent());
        ba = new BaseArticle();
        ba.setCategory(c);
        ba.setFromUrl("http://www.turbo.fr/actualite-automobile/837254-yamaha-autonomie-robot-rivaliser-rossi/?blabla=blu");
        System.out.println(ba.getArticleCanonicalUrl());
        System.out.println(ba.getArticleSrcUrl());
        System.out.println(ba.getArticleTitle());
        System.out.println(ba.getArticleContent());

    }
    
}
