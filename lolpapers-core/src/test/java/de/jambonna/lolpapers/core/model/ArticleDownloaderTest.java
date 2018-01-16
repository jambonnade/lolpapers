package de.jambonna.lolpapers.core.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class ArticleDownloaderTest {
    
    @Test
    public void testExtractCharsetFromContentType() throws IOException {
        ArticleDownloader d = new ArticleDownloader();

        assertEquals(StandardCharsets.UTF_8, d.extractCharsetFromContentType("text/html; charset=utf8"));
        assertEquals("windows-1252", d.extractCharsetFromContentType("text/plain; charset=CP1252").toString());
        assertNull(d.extractCharsetFromContentType("text/html"));
        try {
            d.extractCharsetFromContentType("text/html; charset=zboub");
            fail();
        } catch (UnsupportedCharsetException ex) {
        }
    }
    
    @Test
    public void testTetPageContentFromData() throws IOException {
        assertTestContentBodyEquals("Héhé", "ad-utf8-02.txt", "text/html; charset=utf-8");
        assertTestContentBodyEquals("Héhé", "ad-utf8-02.txt", "text/html");
        assertTestContentBodyEquals("Héhé", "ad-utf8-02.txt", null);
        assertTestContentBodyEquals("Héhé", "ad-utf8-03.txt", null);
        assertTestContentBodyEquals("Héhé", "ad-utf8-01.txt", "text/html; charset=utf-8");
        assertTestContentBodyEquals("HÃ©hÃ©", "ad-utf8-01.txt", "text/html; charset=latin1");
        try {
            assertTestContentBodyEquals("Héhé", "ad-utf8-01.txt", "text/html");
            fail();
        } catch (IOException ex) {
        }

        assertTestContentBodyEquals("Héhé", "ad-cp1252-02a.txt", null);
        assertTestContentBodyEquals("H\uFFFDh\uFFFD", "ad-cp1252-02a.txt", "text/html; charset=utf-8");
        assertTestContentBodyEquals("H\uFFFDh\uFFFD", "ad-cp1252-02b.txt", null);
        assertTestContentBodyEquals("Héhé", "ad-cp1252-02b.txt", "text/html; charset=cp1252");
        
        assertTestContentBodyEquals("Héhé", "ad-cp1252-03a.txt", null);
        assertTestContentBodyEquals("H\uFFFDh\uFFFD", "ad-cp1252-03a.txt", "text/html; charset=utf-8");
        assertTestContentBodyEquals("H\uFFFDh\uFFFD", "ad-cp1252-03b.txt", null);
        assertTestContentBodyEquals("Héhé", "ad-cp1252-03b.txt", "text/html; charset=latin1");
        
        assertTestContentBodyEquals("Héhé", "ad-cp1252-01.txt", "text/html; charset=latin1");        
        assertTestContentBodyEquals("H\uFFFDh\uFFFD", "ad-cp1252-01.txt", "text/html; charset=utf-8");        
        try {
            assertTestContentBodyEquals("Héhé", "ad-cp1252-01.txt", "text/html");
            fail();
        } catch (IOException ex) {
        }

        
    }
    
    public void assertTestContentBodyEquals(String body, String filename, 
            String contentTypeHeader) throws IOException {
        byte[] data = getTestFileContent(filename);
        
        ArticleDownloader d = new ArticleDownloader();
        String content = d.getPageContentFromData(data, contentTypeHeader);
        Document doc = Jsoup.parse(content);
        String bodyText = doc.body().text();
        assertEquals(body, bodyText);
    }
    
    @Test
    @Ignore("Manual quick testing")
    public void testGetPageContent() throws Exception {

        String lequipe1 = "https://www.lequipe.fr/Tennis/Actualites/Alize-cornet-est-en-quarts-de-finale-a-moscou-apres-l-abandon-de-magdalena-rybarikova/843436#xtor=RSS-1";
        String korbenurl1 = "http://feedproxy.google.com/~r/KorbensBlog-UpgradeYourMind/~3/jOfBASZQaiQ/creer-chatbot.html";
        String turbo1 = "http://www.turbo.fr/actualite-automobile/837254-yamaha-autonomie-robot-rivaliser-rossi/";
        String korbenurl2 = "http://feedproxy.google.com/~r/KorbensBlog-UpgradeYourMind/~3/KV54Kw91lbU/dintimite-lhistorique-bash.html";

        ArticleDownloader d;
        ArticleDownloader.Result result;
        
        d = new ArticleDownloader();
//        result = d.request(korbenurl2);
//        System.out.println(result.getStatusCode());
//        System.out.println(result.getLastUrl());
//        Files.write(Paths.get("/tmp/korben.html"), result.getData());
        
//        result = d.getPageContent(lequipe1);
//        System.out.println("\"" + result + "\"");

//        d = new ArticleDownloader();
//        result = d.requestPage(korbenurl1);
//        System.out.println("\"" + result.getLastUrl() + "\"");
//        System.out.println("\"" + result.getContent() + "\"");
//
//        byte[] imgData = d.getResourceContent("https://korben.info/wp-content/uploads/2017/10/1421737133276.jpg");
//        Files.write(Paths.get("/tmp/zboub.jpg"), imgData);
//
//        
//        d = new ArticleDownloader();
//        result = d.getPageContent(turbo1);
//        System.out.println("\"" + result + "\"");
//        
//        d = new ArticleDownloader();
//        result = d.getPageContent(turbo1 + "ll");
//        System.out.println("\"" + result + "\"");
    }
    
    
    public byte[] getTestFileContent(String filename) throws IOException {
        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("article/article-downloader/" + filename);
        
        if (is == null) {
            throw new IOException("resource invalide " + filename);
        }
        
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[10];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }

}
