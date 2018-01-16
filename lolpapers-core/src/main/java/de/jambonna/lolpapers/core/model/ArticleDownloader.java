package de.jambonna.lolpapers.core.model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility to access web pages and retrieve its content
 */
public class ArticleDownloader {
    private static final Logger logger = LoggerFactory.getLogger(ArticleDownloader.class);

    private CloseableHttpClient client;
    
    
    public CloseableHttpClient getClient() {
        if (client == null) {
            client = HttpClients.custom()
//                .setDefaultRequestConfig(globalConfig)
//                .setDefaultCookieStore(cookieStore)
//                .addInterceptorLast(new HttpRequestInterceptor() {
//                    @Override
//                    public void process(
//                            final HttpRequest request,
//                            final HttpContext context) throws HttpException, IOException {
//                        System.out.println("Request headers...");
//                        for (Header h : request.getAllHeaders()) {
//                            System.out.println(h.getName() + "='" + h.getValue() + "'");
//                        }
//                        System.out.println();
//                    }
//
//                })
//                .addInterceptorLast(new HttpResponseInterceptor() {
//                    @Override
//                    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
//                        System.out.println("Response headers...");
//                        for (Header h : response.getAllHeaders()) {
//                            System.out.println(h.getName() + "='" + h.getValue() + "'");
//                        }
//                        System.out.println();
//                    }
//                })
                .build();

        }
        return client;
    }
    
    public Result request(String url) throws IOException {
        Result r = new Result(url);
        

        HttpGet httpGet = new HttpGet(url);
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response = getClient().execute(httpGet, context);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            r.setStatusCode(statusCode);
            
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            try {
                URI location = URIUtils.resolve(httpGet.getURI(), target, redirectLocations);
                r.setLastUrl(location.toASCIIString());
            } catch (URISyntaxException ex) {
            }


            HttpEntity entity = response.getEntity();
            Header contentTypeHeader = response.getLastHeader("Content-Type");
            if (contentTypeHeader != null) {
                r.setContentType(contentTypeHeader.getValue());
            }

            byte[] data = EntityUtils.toByteArray(entity);
            r.setData(data);
            
        } finally {
            response.close();
        }
        return r;
    }

    public Result requestPage(String url) throws IOException {
        Result r = request(url);
        if (r.getData() != null) {
            String content = getPageContentFromData(r.getData(), r.getContentType());
            r.setContent(content);
        }
        return r;
    }
    
    public String getPageContentFromData(byte[] data, String contentTypeHeader) 
            throws IOException {
        
        if (contentTypeHeader != null) {
            Charset headerCharset = extractCharsetFromContentType(contentTypeHeader);
            if (headerCharset != null) {
                // Believe the server header if provided
                return new String(data, headerCharset);
            }
        }
        
        // Look in html document for meta tags
        // Use a 8 bit charset as default where all bytes are valid
        Charset cpWin = Charset.forName("CP1252");
        String html = new String(data, cpWin);
        Document doc = Jsoup.parse(html);
        
        for (Element meta : doc.select("meta[http-equiv]")) {
            if (meta.attr("http-equiv").toLowerCase().equals("content-type")) {
                Charset cs = extractCharsetFromContentType(meta.attr("content"));
                if (cs != null) {
                    return new String(data, cs);
                }
            }
        }
        
        Element meta = doc.select("meta[charset]").first();
        if (meta != null) {
            return new String(data, meta.attr("charset"));
        }
        
        throw new IOException("Unable to find charset");
    }
    
    public Charset extractCharsetFromContentType(String contentType)
            throws UnsupportedCharsetException {
        Charset cs = null;
        Pattern p = Pattern.compile("\\bcharset=([\\w-]+)\\b");
        Matcher m = p.matcher(contentType);
        if (m.find()) {
            String csName = m.group(1);
            cs = Charset.forName(csName);
            if (cs == null) {
                throw new UnsupportedCharsetException("Unknown charset " + csName);
            }
        }
        return cs;
    }
    
    
    public static class Result {
        private final String url;
        private int statusCode;
        private String contentType;
        private byte[] data;
        private String content;
        private String lastUrl;

        
        public Result(String url) {
            this.url = url;
            lastUrl = url;
        }

        public String getUrl() {
            return url;
        }
        
        
        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }
        
        public boolean isStatusOk() {
            return statusCode >= 200 && statusCode < 300;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLastUrl() {
            return lastUrl;
        }

        public void setLastUrl(String lastUrl) {
            this.lastUrl = lastUrl;
        }


    }
}
