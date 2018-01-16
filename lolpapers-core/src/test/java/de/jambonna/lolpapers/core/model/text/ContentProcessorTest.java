package de.jambonna.lolpapers.core.model.text;

import de.jambonna.lolpapers.core.model.TemplatePlaceholder;
import de.jambonna.lolpapers.core.model.lang.Languages;
import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinition;
import de.jambonna.lolpapers.core.model.lang.fr.FrLanguage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContentProcessorTest {
    
    @Test
    public void testExtractDirectTextParts() {
        String html = "<html><body>"
                + "<p id=\"case01\">Bla bla</p>"
                // inline elements don't break the content
                + "<p id=\"case02\"><strong>Bla bla</strong></p>"
                + "<p id=\"case02b\">Bla bla bla <em>Bla bla</em> aaetae aet</p>"
                + "<p id=\"case02c\">Bla bla bla <a href=\"#\">aetae <em>bla <sup>bla</sup></em> aaetae</a> aet</p>"
                // br break the content
                + "<p id=\"case03\">Bla bla<br />aet aeqsdiouo</p>"
                // img and br break the content but it's not a problem if at the beggining and at the end
                // + check if empty parts are added or not
                + "<p id=\"case04\"><img src=\"http://azeaze\"/>Bla bla aet aeqsdiouo<br /><br /></p>"
                // block elements break the content and are not part of the result
                // + check behaviour with leading/trailing spaces
                + "<div id=\"case05\">    <div>xxx xxx</div><ul><li>aaa</li></ul>\nesdoiu <em>dfiuyzer</em> oiuyd <p>zezez zeze</p> bla balbla. </div>"
                + "</body></html>";
        Document doc = Jsoup.parse(html);

        ContentProcessor tp = new ContentProcessor(Languages.getInstance().getFr());
        
        assertEquals(Arrays.asList("Bla bla"), tp.extractDirectTextParts(doc.select("#case01").first()));
        assertEquals(Arrays.asList("Bla bla"), tp.extractDirectTextParts(doc.select("#case02").first()));
        assertEquals(Arrays.asList("Bla bla bla Bla bla aaetae aet"), tp.extractDirectTextParts(doc.select("#case02b").first()));
        assertEquals(Arrays.asList("Bla bla bla aetae bla bla aaetae aet"), tp.extractDirectTextParts(doc.select("#case02c").first()));
        assertEquals(Arrays.asList("Bla bla", "aet aeqsdiouo"), tp.extractDirectTextParts(doc.select("#case03").first()));
        assertEquals(Arrays.asList("Bla bla aet aeqsdiouo"), tp.extractDirectTextParts(doc.select("#case04").first()));
        assertEquals(Arrays.asList(" ", " esdoiu dfiuyzer oiuyd ", " bla balbla. "), tp.extractDirectTextParts(doc.select("#case05").first()));
    }

    @Test
    public void testGetIntroText() {
        assertGetIntroTextEquals("Bla bla bla. Blu blu.", 20, "Bla bla bla. Blu blu.");
        assertGetIntroTextEquals("Bla bla bla. Blu blu.", 5, "Bla bla bla. Blu blu.");
        assertGetIntroTextEquals("Bla bla bla. Blu blu.", 3, "Bla bla bla.");
        assertGetIntroTextEquals("Bla bla bla. Blu blu.", 2, "Bla bla bla.");
        assertGetIntroTextEquals("<p>Bla bla bla.</p> <p>Blu blu.</p>", 20, "Bla bla bla. Blu blu.");
        assertGetIntroTextEquals("<p>Bla bla bla.</p> <p>Blu blu.</p>", 4, "Bla bla bla. Blu blu.");
        assertGetIntroTextEquals("<p>Bla bla bla.</p> <p>Blu blu.</p>", 3, "Bla bla bla.");
        // Sentence break is not detected in this case with current implementation
        // which we consider not a real problem atm
        assertGetIntroTextEquals("<p>Bla bla bla</p> <p>Blu blu.</p>", 2, "Bla bla bla Blu blu.");
        
    }
    
    public void assertGetIntroTextEquals(String html, int minWc, String result) {
        ContentProcessor p = new ContentProcessor(Languages.getInstance().getFr());
        
        Element elt = Jsoup.parseBodyFragment(html).body();
        assertEquals(result, p.getIntroText(elt, minWc));
    }
    
    @Test
    public void testGetArticleMainContentContainer() {
        String html = "<html><body>"
                + "<h1>Bla blab bal</h1>"
                + "<div><p>Bla blab bla.</p><ul>"
                + "<li>Blabla bla</li><li>Blu blu blu</li><li>Bli bli bli</li>"
                + "</ul></div>"
                + "<div>\n"
                // Inline elements kept, most attributes removed.
                + "<p><strong title=\"xxx xx\">Bla bla bla</strong>. Bla bla bla <span class=\"zzz\">bla</span> bla, bla bla bla bla bla.</p>"
                // <img> will be deleted, <a> will be replaced
                + "<p><img src=\"http://localhost/yyy\" />Bla bla <a href=\"http://localhost/zzz\" title=\"title\">bla</a> bla bla bla bla, blabla bla.</p>"
                // Deleted by jsoup
                + "<script>alert(\"hellooooo\");</script>"
                // Cleaned by jsoup
                + "<p><a href=\"javascript:alert('xxx');\">Ataet</a> aet <strong onclick=\"alert('aaa');\">aet aet</strong>.</p>"
                // This will be removed
                + "<ul><li>Bla bla</li><li>Blu blu</li></ul>"
                // Not well writtent sentence, so removed
                + "<p>bla bla</p>"
                // Not long enough to be an article sentence but well written, so kept
                + "<p>Bla bla.</p>"
                // The dots in the date does not constitue a sentence termination, thus this is not a well written sentence
                + "<p>Publié le 02.12.2017</p>"
                // This subdiv will be kept
                + "<div>Bla bla :<blockquote>Bla bla bla bla bla, bla bla bla bla bla.</blockquote></div>"
                + "</div></body></html>";
        Document doc = Jsoup.parse(html);

        ContentProcessor p = new ContentProcessor(Languages.getInstance().getFr());
        Element e = p.getArticleMainContentContainer(doc);
//        System.out.println(e.outerHtml());
        assertEquals(
                "<div> \n" +
                " <p data-blkid=\"6\"><strong>Bla bla bla</strong>. Bla bla bla <span>bla</span> bla, bla bla bla bla bla.</p>\n" +
                " <p data-blkid=\"7\">Bla bla <span class=\"orig-link\">bla</span> bla bla bla bla, blabla bla.</p>\n" +
                " <p data-blkid=\"8\"><span class=\"orig-link\">Ataet</span> aet <strong>aet aet</strong>.</p>\n" +
                " <p data-blkid=\"12\">Bla bla.</p>\n" +
                " <div data-blkid=\"14\">\n" +
                "  Bla bla :\n" +
                "  <blockquote data-blkid=\"15\">\n" +
                "   Bla bla bla bla bla, bla bla bla bla bla.\n" +
                "  </blockquote>\n" +
                " </div>\n" +
                "</div>",
                e.outerHtml());
    }
    
    @Test
    public void testGetArticleMainContentContainerRealWorld() throws IOException {
        String[] articleFileNames = {
            "lequipe01",
            "clubic01",
//            "slate01",
            "huffpost01",
            "marmiton02",
            "marmiton01", // <- fails, not enough punctuation
            "marmiton03", // <- outerHtml keeps container which is <ol>
            "20minutes01",
            "korben01",
            "lemonde01"
        };
        
        ContentProcessor p = new ContentProcessor(Languages.getInstance().getFr());
        
        for (String articleFileName : articleFileNames) {
            InputStream input = getArticleFileStream(articleFileName + ".html");
            String outputContent = getArticleFileContent(articleFileName + "-result.html");
            
            Document doc = Jsoup.parse(input, "UTF-8", "");
            Element e = p.getArticleMainContentContainer(doc);

            String result = e.outerHtml() + "\n";
            System.out.println("\"" + result + "\"");
            assertEquals(outputContent, result);
        }
    }
        
    public InputStream getArticleFileStream(String filename) throws IOException {
        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("article/" + filename);
        
        if (is == null) {
            throw new IOException("resource invalide " + filename);
        }
        return is;
    }
    
    public String getArticleFileContent(String filename) throws IOException {
        InputStream ins = getArticleFileStream(filename);
        
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

    @Test
    public void testWrapWords() {
        ContentProcessor p = new ContentProcessor(Languages.getInstance().getFr());
        ContentProcessor.WrapWordsContext ctx = new ContentProcessor.WrapWordsContext(p);

        // Direct text (no block hierarchy) + no trailing/leading spaces 
        // + one sentence end mark + one sentence breaking tag
        assertWrapWordsResultEquals("Bla bla. Blu blu<br />Blo blo", 
            "<a data-wid=\"1\" data-wsid=\"1\">Bla</a> \n" +
            "<a data-wid=\"2\" data-wsid=\"1\">bla</a>. \n" +
            "<a data-wid=\"3\" data-wsid=\"2\">Blu</a> \n" +
            "<a data-wid=\"4\" data-wsid=\"2\">blu</a>\n" +
            "<br>\n" +
            "<a data-wid=\"5\" data-wsid=\"3\">Blo</a> \n" +
            "<a data-wid=\"6\" data-wsid=\"3\">blo</a>", 
            ctx);

        assertWrapWordsResultEquals(
            // Block element hierarchy + space in opening these elements
            "<div>\n" +
            // Another leading space in this text + simple cases of inline element
            "<p> Bla blb <em>blc bld</em> ble<em> blf  </em>blg.</p>" +
            // Another level + cases of word inline tags breaking words
            "<div><p>Blh bli<em>jkl</em>mno <strong>blj</strong>klm bll<em>mno</em>.</p>" +
            // Muliple spaces + empty paragraph
            "\n <p>  \n  </p>\n  " +
            // Nested inline tag cases
            "<p><strong><em>blmnop</em> bln <em><u>blo</u></em></strong> blp<em>qrs <strong>blq</strong>rst</em></p>" +
            // Multiple ending spaces
            "\n</div>\n  </div> ",
            "<div>\n" +
            " <p><a data-wid=\"7\" data-wsid=\"4\">Bla</a> <a data-wid=\"8\" data-wsid=\"4\">blb</a> <em><a data-wid=\"9\" data-wsid=\"4\">blc</a> <a data-wid=\"10\" data-wsid=\"4\">bld</a></em> <a data-wid=\"11\" data-wsid=\"4\">ble</a><em> <a data-wid=\"12\" data-wsid=\"4\">blf</a> </em><a data-wid=\"13\" data-wsid=\"4\">blg</a>.</p>\n" +
            " <div>\n" +
            "  <p><a data-wid=\"14\" data-wsid=\"6\">Blh</a> <a data-wid=\"15\" data-wsid=\"6\">blijklmno</a><em></em> <strong><a data-wid=\"16\" data-wsid=\"6\">bljklm</a></strong> <a data-wid=\"17\" data-wsid=\"6\">bllmno</a><em></em>.</p>\n" +
            "  <p></p>\n" +
            "  <p><strong><em><a data-wid=\"18\" data-wsid=\"9\">blmnop</a></em> <a data-wid=\"19\" data-wsid=\"9\">bln</a> <em><u><a data-wid=\"20\" data-wsid=\"9\">blo</a></u></em></strong> <a data-wid=\"21\" data-wsid=\"9\">blpqrs</a><em> <strong><a data-wid=\"22\" data-wsid=\"9\">blqrst</a></strong></em></p>\n" +
            " </div>\n" +
            "</div>",
            ctx);
        
        assertWrapWordsResultEquals(
            // Case we won't manage since Element.text() doesn't insert a break after paragraph end
            "<div><p>bla bla bla</p>- Blu blu.</div>",
            "<div>\n" +
            " <p><a data-wid=\"23\" data-wsid=\"13\">bla</a> <a data-wid=\"24\" data-wsid=\"13\">bla</a> <a data-wid=\"25\" data-wsid=\"13\">bla-</a></p> \n" +
            " <a data-wid=\"26\" data-wsid=\"14\">Blu</a> \n" +
            " <a data-wid=\"27\" data-wsid=\"14\">blu</a>.\n" +
            "</div>",
            ctx);
    }
    
    public void assertWrapWordsResultEquals(String input, String expected,
            ContentProcessor.WrapWordsContext ctx) {
        
        Document doc = Jsoup.parse("<html><body>" + input + "</body></html>");

        Element output = ctx.getProcessor().wrapWords(doc.body(), ctx);
        String result = output.html();
        System.out.println("Result: \"" + result +  "\"");
        assertEquals(expected, result);

    }
    
    @Test
    public void testGetFinalArticleContent() {
        Document doc = Jsoup.parse(
                "<html><body>" +
                // Cas généraux, remplacements au milieu de phrase sans changement texte précédent
                "<p data-blkid=\"5\">Je veux des aaa et un bbb, cccc ddd, tout simplement. " +
                // Mot début de phrase + elision avec respect de casse
                "eeeee c'est ma grande passion. Que ffff soit fait avec respect de ggg hhh</p>" +
                // Block supprimé
                "<p data-blkid=\"6\">Blaa blaaaa blblbablabl.</p>" +
                // Remplacements avec tags + elision + utilisation d'une reference d'autre remplacement + remplacement qui dépasse des tags
                "<p data-blkid=\"7\"><strong>Ma iii</strong> est <em>TA</em> jjj, je <strong>kkk <em>lll</em></strong> mmm <em>comme ooo</em> ppp." +
                // Remplacement texte precedent séparé par des tags
                "<p data-blkid=\"8\"><strong><em>Le</em></strong> <em>qqq de</em> <strong>rrr</strong></p>" +
                "</body></html>");
        
        ContentProcessor p = new ContentProcessor(Languages.getInstance().getFr());
        ContentProcessor.WrapWordsContext ctx = new ContentProcessor.WrapWordsContext(p);
        Element wrapped = p.wrapWords(doc.body(), ctx);
        System.out.println("Wrapped:\n" + wrapped.html());
                
        List<TemplatePlaceholder> placeholders = new ArrayList<>();
        TemplatePlaceholder tp;
        
        tp = addTemplatePlaceholder(placeholders, 4, 1, 
                newSDef(FrLanguage.TYPE_NOM), "chips");
        
        tp = addTemplatePlaceholder(placeholders, 7, 3, 
                newSDef(FrLanguage.TYPE_NOM), "éléphant magique");
        String elephantRef = tp.getReference();
        
        tp = addTemplatePlaceholder(placeholders, 12, 1, 
                newSDef(FrLanguage.TYPE_VERBE), "ôter les points noirs");
        
        tp = addTemplatePlaceholder(placeholders, 19, 1, 
                newSDef(FrLanguage.TYPE_VERBE), "élever les poules");
        
        tp = addTemplatePlaceholder(placeholders, 25, 2, 
                newSDef(FrLanguage.TYPE_SNOMINAL), "le code");
                
        tp = addTemplatePlaceholder(placeholders, 31, 1, 
                newSDef(FrLanguage.TYPE_NOM, FrLanguage.FLAG_NOM_CTX_GENRE_FEMININ), "hallebarde");
        String refHallebarde = tp.getReference();

        tp = addTemplatePlaceholder(placeholders, 34, 1, null, null);
        tp.setUsePlaceholder(refHallebarde);

        tp = addTemplatePlaceholder(placeholders, 36, 3, 
                newSDef(FrLanguage.TYPE_VERBE), "aime monter les armoires");
        
        tp = addTemplatePlaceholder(placeholders, 40, 2, 
                newSDef(FrLanguage.TYPE_SNOMINAL), "l'ours brun");
        
        tp = addTemplatePlaceholder(placeholders, 43, 1, null, null);
        tp.setUsePlaceholder(elephantRef);
        
        tp = addTemplatePlaceholder(placeholders, 45, 1, 
                newSDef(FrLanguage.TYPE_SNOMINAL), "« Homer Simpson »", 
                FrLanguage.FLAG_COMMON_HMUET, FrLanguage.FLAG_COMMON_KEEP_MAJ_ALL);
        
        Element wrappedFiltered = p.removeBlockElements(wrapped, new int[] { 2, 6, 12 });
        Element output = p.getFinalArticleContent(wrappedFiltered, placeholders);
        System.out.println("Result: \"" + output.html() + "\"");
        assertEquals(
                "<p>Je veux des chips et un éléphant magique, tout simplement. Ôter les points noirs c'est ma grande passion. Qu'élever les poules soit fait avec respect du  code</p>\n" +
                "<p><strong>Mon hallebarde</strong> est <em>TON </em>hallebarde, j'<strong>aime monter les armoires<em></em></strong> <em>comme l'ours brun</em>.</p>\n" +
                "<p><strong><em>L'</em></strong><em>éléphant magique d'</em><strong>« Homer Simpson »</strong></p>",
                output.html());
    }
    
    public SyntagmeDefinition newSDef(String typeCode, String... flags) {
        SyntagmeDefinition sd = new SyntagmeDefinition(
                Languages.getInstance().getFr().getSyntagmeType(typeCode));
        sd.resetChosenFlags(Arrays.asList(flags));
        sd.update();
        return sd;
    }
    
    public TemplatePlaceholder addTemplatePlaceholder(List<TemplatePlaceholder> list, 
            int fromWord, int nbWords, SyntagmeDefinition origSd, 
            String text, String... flags) {
        TemplatePlaceholder tp = new TemplatePlaceholder();
        list.add(tp);
        tp.setFromWord(fromWord);
        tp.setNbWords(nbWords);
        if (origSd != null) {
            tp.setDefinitionSD(origSd);
            tp.setReplacementDefinitionFlags(String.join(",", flags));
            tp.setReplacementText(text);
        }
        tp.setReference("s" + list.size());
        return tp;
    }
    
    @Test
    public void testGetFinalArticleContent2() {
        Document doc = Jsoup.parse(
                "<html><body>" +
                "<p data-blkid=\"98\"><a data-wid=\"133\" data-wsid=\"9\">Pensez-vous</a> <a data-wid=\"134\" data-wsid=\"9\">que</a> <a data-wid=\"135\" data-wsid=\"9\">les</a> <a data-wid=\"136\" data-wsid=\"9\">dinosaures</a> <a data-wid=\"137\" data-wsid=\"9\">ont</a> <a data-wid=\"138\" data-wsid=\"9\">eu</a> <a data-wid=\"139\" data-wsid=\"9\">la</a> <a data-wid=\"140\" data-wsid=\"9\">chance</a> <a data-wid=\"141\" data-wsid=\"9\">d</a>’<a data-wid=\"142\" data-wsid=\"9\">assister</a> <a data-wid=\"143\" data-wsid=\"9\">à</a> <a data-wid=\"144\" data-wsid=\"9\">la</a> <a data-wid=\"145\" data-wsid=\"9\">formation</a> <a data-wid=\"146\" data-wsid=\"9\">des</a> <a data-wid=\"147\" data-wsid=\"9\">anneaux</a> <a data-wid=\"148\" data-wsid=\"9\">de</a> <span class=\"orig-link\"><a data-wid=\"149\" data-wsid=\"9\">Saturne</a></span> (<a data-wid=\"150\" data-wsid=\"9\">c</a>’<a data-wid=\"151\" data-wsid=\"9\">est</a> <a data-wid=\"152\" data-wsid=\"9\">évidemment</a> <a data-wid=\"153\" data-wsid=\"9\">une</a> <a data-wid=\"154\" data-wsid=\"9\">question</a> <a data-wid=\"155\" data-wsid=\"9\">rhétorique</a>).</p>\n" +
                "</body></html>");
        
        ContentProcessor p = new ContentProcessor(Languages.getInstance().getFr());

        List<TemplatePlaceholder> placeholders = new ArrayList<>();
        TemplatePlaceholder tp;
        
        tp = addTemplatePlaceholder(placeholders, 147, 3, 
                newSDef(FrLanguage.TYPE_NOM), "chips");
        placeholders.add(tp);
        
        Element output = p.getFinalArticleContent(doc.body(), placeholders);
        System.out.println("Result: \"" + output.html() + "\"");
        assertEquals("<p>Pensez-vous que les dinosaures ont eu la chance d’assister à la formation des chips<span class=\"orig-link\"></span> (c’est évidemment une question rhétorique).</p>",
                output.html());
    }
    
    
    @Test
    public void testGetWordRangeText() {
        ContentProcessor p = new ContentProcessor(Languages.getInstance().getFr());
        ContentProcessor.WrapWordsContext ctx = new ContentProcessor.WrapWordsContext(p);

        Document doc = Jsoup.parse("<p> aaa : bbb, <strong>ccc</strong>.</p>");
        Element wrapped1 = p.wrapWords(doc, ctx);

        doc = Jsoup.parse("<div><p>Ddd, <em>eee!</em>  fff</p>" +
                "<p> Ggg c'est « <em>la  fête</em> » hhh iii.</p>");
        Element wrapped2 = p.wrapWords(doc, ctx);
        System.out.println(wrapped2.html());
        
        List<Element> wrappedContainers = Arrays.asList(wrapped1, wrapped2);
        // Case where some words are into inline element + behaviour with multiple spaces
        assertEquals("c'est « la fête » hhh", p.getWordRangeText(wrappedContainers, 8, 5));
        // Case where start word is inline element (going up needed)
        assertEquals("eee! fff", p.getWordRangeText(wrappedContainers, 5, 2));
        // First container
        assertEquals("bbb, ccc", p.getWordRangeText(wrappedContainers, 2, 2));
        // One word
        assertEquals("eee", p.getWordRangeText(wrappedContainers, 5, 1));
        // No word
        assertEquals("", p.getWordRangeText(wrappedContainers, 9, 0));
        // Word not found
        assertNull(p.getWordRangeText(wrappedContainers, 30, 2));
        // Last word outside of sentence, this is not managed, but check it doesn't crash
        assertNotNull(p.getWordRangeText(wrappedContainers, 2, 3));
        assertNotNull(p.getWordRangeText(wrappedContainers, 6, 2));
        assertNotNull(p.getWordRangeText(wrappedContainers, 13, 3));
    }
}
