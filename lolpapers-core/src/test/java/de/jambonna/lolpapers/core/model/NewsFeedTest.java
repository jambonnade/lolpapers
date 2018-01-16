package de.jambonna.lolpapers.core.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.ConstraintViolationException;
import org.junit.Test;
import static org.junit.Assert.*;

public class NewsFeedTest {
        
    @Test
    public void testEmptyNewsFeed() {
        NewsFeed f = new NewsFeed();
        assertEquals(dumpNewsFeed(f),
                "title 'null'\n" +
                "url 'null'\n" +
                "updated false\n");
    }
    
    @Test
    public void testRealWorldLemonde() throws IOException {
        NewsFeed f = new NewsFeed();
        InputStream s = getNewsFeedDataStream("lemonde-une-2017-05-29.xml");
        f.setFeedStream(s);
        f.setActive(true);
        f.update();
        assertNewsFeedEqualsFile(f, "lemonde-une-2017-05-29-output.txt");
        
        // Check that another update with the same entity overrides well the
        // existing entries
        s = getNewsFeedDataStream("lemonde-une-2017-05-29-2.xml");
        f.setFeedStream(s);
        f.update();
        assertNewsFeedEqualsFile(f, "lemonde-une-2017-05-29-2-output.txt");
    }
    
    @Test
    public void testNewsFeedValidations() {
        NewsFeed f = new NewsFeed();
        String tooLong = "too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long";
        f.setTitle(tooLong + "");
        assertEquals(f.getTitle(), "too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too long too ...");
        f.setTitle(null);
        assertNull(f.getTitle());
        
        NewsFeedItem item = new NewsFeedItem();
        item.setTitle("ok");
        try {
            item.setTitle(null);
            fail("Exception not thrown");
        } catch (ConstraintViolationException e) {
            assertEquals("title: may not be null", e.getMessage());
        }
        try {
            item.setTitle(tooLong + "");
            fail("Exception not thrown");
        } catch (ConstraintViolationException e) {
            assertEquals("title: size must be between 0 and 250", e.getMessage());
        }
        assertEquals("ok", item.getTitle());
        
        String tooLongUrl = "http://abcdefghi.aetaet.com/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong/toolong";
        item.setUrl("https://xxxx");
        try {
            item.setUrl(null);
            fail("Exception not thrown");
        } catch (ConstraintViolationException e) {
            assertEquals("url: may not be null", e.getMessage());
        }
        try {
            item.setUrl("xxx");
            fail("Exception not thrown");
        } catch (ConstraintViolationException e) {
            assertEquals("url: must be a valid URL", e.getMessage());
        }
        try {
            item.setUrl(tooLongUrl + "");
            fail("Exception not thrown");
        } catch (ConstraintViolationException e) {
            assertEquals("url: size must be between 0 and 250", e.getMessage());
        }
        assertEquals("https://xxxx", item.getUrl());
                
        item.setUid("http://abc");
        try {
            item.setUid(null);
            fail("Exception not thrown");
        } catch (ConstraintViolationException e) {
            assertEquals("uid: may not be null", e.getMessage());
        }
        try {
            item.setUid(tooLongUrl + "");
            fail("Exception not thrown");
        } catch (ConstraintViolationException e) {
            assertEquals("uid: size must be between 0 and 250", e.getMessage());
        }
        assertEquals("http://abc", item.getUid());
                
    }
    
    public InputStream getNewsFeedDataStream(String filename) throws IOException {
        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("newsfeed/" + filename);
        if (is == null) {
            throw new IOException("resource invalide " + filename);
        }
        return is;
    }
    
    public String getNewsFeedDataContent(String filename) throws IOException {
        InputStream ins = getNewsFeedDataStream(filename);
        InputStreamReader insr = new InputStreamReader(ins, "UTF-8");
        BufferedReader br = new BufferedReader(insr);
        StringBuilder sb = new StringBuilder();
        
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public String dumpNewsFeed(NewsFeed f) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:S Z");
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("title '%s'\n", f.getTitle()));
        sb.append(String.format("url '%s'\n", f.getUrl()));
        sb.append(String.format("updated %b\n", f.getUpdatedAt()));
        int num = 0;
        for (NewsFeedItem i : f.getItems()) {
            sb.append(String.format("\t%d) title '%s'\n", num, i.getTitle()));
            sb.append(String.format("\t%d) url '%s'\n", num, i.getUrl()));
            sb.append(String.format("\t%d) uid '%s'\n", num, i.getUid()));
            Date d = i.getPubDate();
            sb.append(String.format("\t%d) pubDate '%s'\n", num, d != null ? df.format(d) : null));
            num++;
        }
//        System.out.println(sb.toString());
        return sb.toString();
    }
    
    public void assertNewsFeedEqualsFile(NewsFeed f, String filename) throws IOException {
        String content = getNewsFeedDataContent(filename);
        assertEquals(content, dumpNewsFeed(f));
    }
}
