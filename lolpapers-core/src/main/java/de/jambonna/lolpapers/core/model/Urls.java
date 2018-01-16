package de.jambonna.lolpapers.core.model;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tools about the WebApp URL structure (both internal and external urls)
 */
public class Urls {
    public static final String INTERNAL_PATH_PREFIX = "internal/";
    public static final String PAGE_INTERNAL_PATH = "page";
    public static final String COMMON_WEBSITE_ID_PARAM = "website_id";
    
    public static final String USER_CONNECT_URL_ID = "user-connect";
    public static final String USER_CONNECT_TWITTER_URL_ID = "user-connect-twitter";
    public static final String USER_DISCONNECT_URL_ID = "user-disconnect";
    public static final String ARTICLE_TEMPLATE_NEW_URL_ID = "article-template-new";
    public static final String ARTICLE_TEMPLATE_EDIT_URL_ID = "article-template-edit";
    public static final String FILL_REPLACEMENT_URL_ID = "fill-replacement";
    public static final String UPVOTE_CONTENT_URL_ID = "content-upvote";
    
    
    private static Urls instance;
    public static Urls getInstance() {
        if (instance == null) {
            instance = new Urls();
        }
        return instance;
    }
    
    
    private final Pattern removeDiacriticalMarks;
    private final Pattern removeNonWord;
    
    public Urls() {
        removeDiacriticalMarks = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        removeNonWord = Pattern.compile("\\W+");
    }
    
    
    public String getIdentifier(String identifier, Website website) {
        if (website != null) {
            return String.format("%s-%s", website.getCode(), identifier);
        }
        return identifier;
    }
    
    public String getInternalRequestPath(String path) {
        return INTERNAL_PATH_PREFIX + path;
    }
    
    public String getInternalRequestPath(String path, QueryBuilder queryString) {
        return getInternalRequestPath(path) + query(queryString);
    }
    
    public String getInternalRequestPath(String path, Website website, QueryBuilder queryString) {
        return getInternalRequestPath(path, 
                query(queryString).param(COMMON_WEBSITE_ID_PARAM, website.getId()));
    }
        
    public String getInternalPageRequestPath(String page, Website website, 
            QueryBuilder queryString) {
        return getInternalRequestPath(PAGE_INTERNAL_PATH, website, 
                query(queryString).param("page", page));
    }
        
    public Url createWebsitePageUrl(Website website, String baseId, String page, String pubRequestPath,
            QueryBuilder internalPathQS) {
        Url url = new Url();
        url.setIdentifier(getIdentifier(baseId, website));
        url.setDestPath(getInternalPageRequestPath(page, website, internalPathQS));
        url.setRequestPath(website.getRequestPath() + pubRequestPath);
        return url;
    }
    public Url createWebsiteSubUrl(Website website, String identifier, String pubRequestPath,
            String internalRequestPath, QueryBuilder internalPathQS) {
        Url url = new Url();
        url.setIdentifier(getIdentifier(identifier, website));
        url.setDestPath(getInternalRequestPath(internalRequestPath, website, internalPathQS));
        url.setRequestPath(website.getRequestPath() + pubRequestPath);
        return url;
    }
    
//    public String getPubPath(SharedData sd, Website website, String identifier) {
//        String realIdentifier = getIdentifierInWebsite(identifier, website);
//        Url url = sd.getUrlForIdentifier(realIdentifier);
//        if (url == null) {
//            throw new IllegalArgumentException("Invalid url id : \"" + realIdentifier + "\"");
//        }
//        return url.getRequestPath();
//    }
//    
//    public String getUserConnectPubPath(SharedData sd, Website website) {
//        return getPubPath(sd, website, USER_CONNECT_URL_ID);
//    }
    
    
    public CharSequence normalizeForPubRequestPath(CharSequence input) {
        if (input == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Matcher matcher = removeDiacriticalMarks.matcher(normalized);
        normalized = matcher.replaceAll("").toLowerCase();
        matcher = removeNonWord.matcher(normalized);
        CharSequence result = matcher.replaceAll("-");
        if (result.length() > 0 && result.charAt(0) == '-') {
            result = result.subSequence(1, result.length());
        }
        result = removePubRequestPathTrailingDash(result);
        return result;
    }
    
    protected CharSequence removePubRequestPathTrailingDash(CharSequence normalized) {
        int lg = normalized.length();
        if (lg > 0 && normalized.charAt(lg - 1) == '-') {
            normalized = normalized.subSequence(0, lg - 1);
        }
        return normalized;
    }
    
    public String getFinalPubRequestPath(CharSequence normalizedBasePath, 
            CharSequence suffix, int maxLength) {
        StringBuilder sb = new StringBuilder(maxLength);
        int basePathLg = Math.min(normalizedBasePath.length(), 
                Math.max(0, maxLength - suffix.length()));
        CharSequence basePath = normalizedBasePath.subSequence(0, basePathLg);
        basePath = removePubRequestPathTrailingDash(basePath);
        sb.append(basePath);
        int remainingLg = maxLength - sb.length();
        sb.append(suffix.subSequence(0, Math.min(suffix.length(), remainingLg)));
        return sb.toString();
    }
    public String getFinalPubRequestPath(CharSequence normalizedBasePath, 
            int suffixNum, int maxLength) {
        String suffix = "";
        if (suffixNum > 0) {
            suffix = "-" + suffixNum;
        }
        return getFinalPubRequestPath(normalizedBasePath, suffix, maxLength);
    }

    
    
    public static QueryBuilder query() {
        return new QueryBuilder();
    }
    public static QueryBuilder query(QueryBuilder qb) {
        return qb != null ? qb : new QueryBuilder();
    }
    
    /**
     * Simple query string builder, for non-textual parameters only
     */
    public static class QueryBuilder {
        private final Map<String, String> params;
        private final Pattern checkParamPattern;

        public QueryBuilder() {
            params = new HashMap<>();
            checkParamPattern = Pattern.compile("[\\w\\-\\.,]*");
        }
        
        public QueryBuilder param(String param, String value) {
            if (param != null && param.length() > 0) {
                if (!checkParamPattern.matcher(param).matches()) {
                    throw new IllegalArgumentException("Invalid param");
                }
                if (value == null) {
                    value = "";
                }
                if (!checkParamPattern.matcher(value).matches()) {
                    throw new IllegalArgumentException("Invalid param value");
                }
                params.put(param, value);
            }
            return this;
        }
        
        public QueryBuilder param(String param, Long value) {
            return param(param, value == null ? "" : value.toString());
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (first) {
                    sb.append('?');
                    first = false;
                } else {
                    sb.append('&');
                }
                sb.append(param.getKey());
                sb.append('=');
                sb.append(param.getValue());
            }
            return sb.toString();
        }
        
    }
}
