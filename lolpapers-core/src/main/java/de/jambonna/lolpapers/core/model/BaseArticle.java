package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.model.text.ContentProcessor;
import de.jambonna.lolpapers.core.validation.BeanValidator;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base article entity type, will be used to create ArticleTemplate entities
 */
@Entity
public class BaseArticle extends BaseContent {
    
    private static final Logger logger = LoggerFactory.getLogger(BaseArticle.class);

    
    @Size(max = 250)
    @URL
    private String articleSrcUrl;
    
    @Size(max = 250)
    @URL
    private String articleCanonicalUrl;
    
    @Size(max = 250)
    private String articleTitle;
    
    @Size(max = 2500)
    private String articleDescription;
    
    @Size(max = 65000)
    private String articleContent;
    
    @Transient
    private Element articleContentElement;
    
    private String articleOrigHtml;
    
    @Size(max = 250)
    @URL
    private String articleImageSrcUrl;
    
    @Transient
    private BufferedImage articleSrcImage;
    
    @Transient
    private ArticleDownloader articleDownloader;

    private boolean articleImageOk;

    @Size(max = 250)
    private String articleFilesBaseName;
    
    @Enumerated(EnumType.STRING)
    private RejectionReason articleRejectionReason;


    public String getArticleSrcUrl() {
        return articleSrcUrl;
    }

    public void setArticleSrcUrl(String articleSrcUrl) {
        BeanValidator.getInstance().validatePropertyValue(BaseArticle.class, "articleSrcUrl", articleSrcUrl);
        this.articleSrcUrl = articleSrcUrl;
    }

    public String getArticleCanonicalUrl() {
        return articleCanonicalUrl;
    }

    public void setArticleCanonicalUrl(String articleCanonicalUrl) {
        BeanValidator.getInstance().validatePropertyValue(BaseArticle.class, "articleCanonicalUrl", articleCanonicalUrl);
        this.articleCanonicalUrl = articleCanonicalUrl;
    }

    
    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        if (!getLanguage().hasText(articleTitle)) {
            articleTitle = null;
        }

        BeanValidator.getInstance().validatePropertyValue(BaseArticle.class, "articleTitle", articleTitle);
        this.articleTitle = articleTitle;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        if (!getLanguage().hasText(articleDescription)) {
            articleDescription = null;
        }
        
        BeanValidator.getInstance().validatePropertyValue(BaseArticle.class, "articleDescription", articleDescription);
        this.articleDescription = articleDescription;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        BeanValidator.getInstance().validatePropertyValue(BaseArticle.class, "articleContent", articleContent);
        this.articleContent = articleContent;
    }
    
    public void setArticleContentElement(Element articleContent) {
        Element articleContentCopy = articleContent.clone();
        setArticleContent(articleContentCopy.outerHtml());
        articleContentElement = articleContentCopy;
    }
    
    public Element getArticleContentElement() {
        if (articleContentElement == null) {
            if (articleContent != null) {
                Document articleContentDoc = Jsoup.parseBodyFragment(articleContent);
                articleContentElement = articleContentDoc.body().children().first();
            }
        }
        return articleContentElement;
    }

    public String getArticleOrigHtml() {
        return articleOrigHtml;
    }

    public void setArticleOrigHtml(String articleOrigHtml) {
        this.articleOrigHtml = articleOrigHtml;
    }

    public String getArticleImageSrcUrl() {
        return articleImageSrcUrl;
    }

    public void setArticleImageSrcUrl(String articleImageSrcUrl) {
        this.articleImageSrcUrl = articleImageSrcUrl;
        setArticleImageOk(articleImageSrcUrl != null && !"".equals(articleImageSrcUrl));
        articleSrcImage = null;
    }

    public boolean isArticleImageOk() {
        return articleImageOk;
    }

    public void setArticleImageOk(boolean articleImageOk) {
        this.articleImageOk = articleImageOk;
    }

    public String getArticleFilesBaseName() {
        if (articleFilesBaseName == null) {
            articleFilesBaseName = UUID.randomUUID().toString();
        }
        return articleFilesBaseName;
    }

    public void setArticleFilesBaseName(String articleFilesBaseName) {
        this.articleFilesBaseName = articleFilesBaseName;
    }
    
    public Path getArticleFileRelPath(String fileNameEnd) {
        String baseName = getArticleFilesBaseName();
        Path path = Paths.get("article", baseName.substring(0, 2), 
                baseName + "-" + fileNameEnd);
        return path;
    }
    public Path getArticleImageRelPath(String version) {
        // Note : image articles always jpeg
        return getArticleFileRelPath(version + ".jpg");
    }
    public Path getArticleSrcImagePath() {
        return getApp().getVarFiles().getFinalFilePath(getArticleImageRelPath("src"), false);
    }
    

    public BufferedImage getArticleSrcImage() throws IOException {
        if (articleSrcImage == null && isArticleImageOk()) {
            Path srcImgPath = getArticleSrcImagePath();
            if (!Files.exists(srcImgPath)) {
                ArticleDownloader.Result adr = 
                        getArticleDownloader().request(getArticleImageSrcUrl());
                if (adr.getData() == null || !adr.isStatusOk()) {
                    throw new IOException("Can't download " + adr.getUrl() + ": " + adr.getStatusCode());
                }
                byte[] imgData = adr.getData();
                
                getApp().getVarFiles().prepareDirectories(srcImgPath.getParent());
                
                Files.write(srcImgPath, imgData);
            } 
            
            articleSrcImage = new ImageHandler().loadImage(srcImgPath);
        }
        return articleSrcImage;
    }
    
    public Path getArticleImage(int width, int height, boolean fit) throws IOException {
        if (!isArticleImageOk()) {
            return null;
        }
        
        String version = String.format("%dx%d%s", width, height, fit ? "-fit" : "");
        
        Path relPath = getArticleImageRelPath(version);
        Path finalPath = getApp().getVarFiles().getFinalFilePath(
                getArticleImageRelPath(version), true);
        if (!Files.exists(finalPath)) {
            BufferedImage srcImage = getArticleSrcImage();
            if (srcImage == null) {
                throw new IOException("No src image");
            }
            
            getApp().getVarFiles().prepareDirectories(finalPath.getParent());
            
            ImageHandler ih = new ImageHandler();
            BufferedImage outImage;
            if (fit) {
                outImage = ih.resizeFit(srcImage, width, height);
            } else {
                throw new UnsupportedOperationException();
            }
            
            ih.saveJpeg(finalPath, outImage, 90);
        }
        return relPath;
    }
    
    public Path getArticleFullImage() throws IOException {
        if (!isArticleImageOk()) {
            return null;
        }
        String version = "full";        
        Path relPath = getArticleImageRelPath(version);
        Path finalPath = getApp().getVarFiles().getFinalFilePath(
                getArticleImageRelPath(version), true);
        if (!Files.exists(finalPath)) {
            BufferedImage srcImage = getArticleSrcImage();
            if (srcImage == null) {
                throw new IOException("No src image");
            }
            
            getApp().getVarFiles().prepareDirectories(finalPath.getParent());
            
            ImageHandler ih = new ImageHandler();
            ih.saveJpeg(finalPath, srcImage, 90);
        }
        return relPath;
    }
    
    public RejectionReason getArticleRejectionReason() {
        return articleRejectionReason;
    }

    public void setArticleRejectionReason(RejectionReason articleRejectionReason) {
        this.articleRejectionReason = articleRejectionReason;
        setStatus(Status.REJECTED);
    }
    

    public void setFromUrl(String srcUrl) throws IOException {
        ArticleDownloader.Result r = getArticleDownloader().requestPage(srcUrl);
        if (r.getContent() == null || !r.isStatusOk()) {
            throw new IOException("Can't download " + r.getUrl() + ": " + r.getStatusCode());
        }
        
        String canonicalUrl = r.getLastUrl();
        int pos = canonicalUrl.indexOf("#");
        if (pos != -1) {
            canonicalUrl = canonicalUrl.substring(0, pos);
        }
        pos = canonicalUrl.indexOf("?");
        if (pos != -1) {
            canonicalUrl = canonicalUrl.substring(0, pos);
        }
        
        setArticleSrcUrl(srcUrl);
        setArticleCanonicalUrl(canonicalUrl);
        setArticleOrigHtml(r.getContent());

        setFromOrigHtml();
    }
    
    public void setFromOrigHtml() {
        String content = getArticleOrigHtml();
        if (content == null) {
            throw new IllegalStateException("No article orig html content");
        }
        String url = getArticleCanonicalUrl();
        if (url == null) {
            throw new IllegalStateException("No article url");
        }
        
        Document doc = Jsoup.parse(content, url);
        setDataFromOpenGraph(doc);
        
        setContentFromDocument(doc);
    }
    
    public void setContentFromDocument(Document doc) {
        ContentProcessor p = getLanguage().getContentProcessor();
        Element mainContainer = p.getArticleMainContentContainer(doc);
        setArticleContentElement(mainContainer);
    }
            
    public void setDataFromOpenGraph(Document doc) {
        Element el = doc.select("meta[property='og:title']").first();
        if (el == null) {
            // 20minutes uses now this tag instead
            el = doc.select("meta[name='og:title']").first();
        }
        if (el != null) {
            setArticleTitle(el.attr("content"));
        }
        
        el = doc.select("meta[property='og:description']").first();
        if (el == null) {
            el = doc.select("meta[name='og:description']").first();
        }
        if (el != null) {
            setArticleDescription(el.attr("content"));
        }
        
        el = doc.select("meta[property='og:image']").first();
        if (el == null) {
            el = doc.select("meta[name='og:image']").first();
        }
        if (el != null) {
            setArticleImageSrcUrl(el.attr("content"));
        }
    }
    
    public void initFromNewsFeedItem(NewsFeedItem nfi) throws IOException {
        setCategory(nfi.getNewsFeed().getCategory());
        
        setArticleDownloader(nfi.getNewsFeed().getArticleDownloader());
        
        setFromUrl(nfi.getUrl());
    }
    
    public ArticleDownloader getArticleDownloader() {
        if (articleDownloader == null) {
            articleDownloader = new ArticleDownloader();
        }
        return articleDownloader;
    }

    public void setArticleDownloader(ArticleDownloader articleDownloader) {
        this.articleDownloader = articleDownloader;
    }

    
    public void prepare() {
        autoReject();
        if (getStatus() == null) {
            setStatus(BaseContent.Status.AVAILABLE);
            try {
                getArticleFullImage();
            } catch (IOException e) {
                logger.warn("Error fetching/creating article image", e);
                setArticleImageOk(false);
            }
        }
    }
    
    public void autoReject() {
        if (getArticleTitle() == null) {
            setArticleRejectionReason(RejectionReason.NO_TITLE);
            return;
        }
        
        int minWc = getApp().getConfig().getAppArticleMinWc();
        int maxWc = getApp().getConfig().getAppArticleMaxWc();
        
        ContentProcessor p = getLanguage().getContentProcessor();
        int wc = p.getArticleTotalWc(getArticleContentElement());
        if (wc < minWc) {
            setArticleRejectionReason(RejectionReason.TOO_SHORT);
        } else if (wc > maxWc) {
            setArticleRejectionReason(RejectionReason.TOO_LONG);
        }
    }
    

    public static enum RejectionReason {
        NO_TITLE,
        TOO_SHORT,
        TOO_LONG,
        USER_WRONG_CONTENT
    }
}
