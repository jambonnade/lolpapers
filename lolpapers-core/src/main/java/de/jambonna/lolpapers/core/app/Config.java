package de.jambonna.lolpapers.core.app;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Holds app configuration. We use this instead of a Map to ensure thread safety
 */
public class Config {
    private String appBaseUrl;
    private String appVarPath;
    private Integer appArticleMinWc;
    private Integer appArticleMaxWc;
    private Integer appArticleAutoDescriptionMinWc;
    private Integer appArticleTemplateMinPlaceholder;
    private Integer appArticleTemplateMaxWordsByPlaceholder;
    private Integer appUserArticleTemplateLimit;
    private Integer appUserArticleTemplateResetDelay;
    private Integer appUserTemplatePlacholderFillMaxDuration;
    
    private String persistenceStorageType;
    private Integer persistenceSchemaVersion;
    
    private String datasourceUrl;
    private String datasourceUsername;
    private String datasourcePassword;
    
    private String twitterSigninConsumerKey;
    private String twitterSigninConsumerSecret;
    private String twitterMainConsumerKey;
    private String twitterMainConsumerSecret;
    private String twitterMainAccessToken;
    private String twitterMainAccessTokenSecret;
    
    public Config(Map<String, String> config) throws AppException {
        for (Map.Entry<String, String> conf : config.entrySet()) {
            String confName = conf.getKey();
            String confValue = conf.getValue();
            
            StringBuilder fieldNameSb = new StringBuilder();
            boolean first = true;
            for (String part : confName.split("\\.")) {
                if (first) {
                    fieldNameSb.append(part);
                    first = false;                
                } else {
                    fieldNameSb.append(part.substring(0, 1).toUpperCase());
                    fieldNameSb.append(part.substring(1));
                }
            }
            String fieldName = fieldNameSb.toString();
            try {
                Field f = Config.class.getDeclaredField(fieldName);
                if (f.getType().equals(Integer.class)) {
                    f.set(this, Integer.valueOf(confValue));
                } else {
                    f.set(this, confValue);
                }
                
            } catch (NoSuchFieldException e) {
                // Ok to provide additional config not used here
            } catch (Exception e) {
                throw new AppException(
                        "Unable to set config \"" + confName + "\" to value \"" + confValue + "\"", e);
            }

        }
    }

    public String getAppBaseUrl() {
        checkConfProvided("appBaseUrl", appBaseUrl);
        return appBaseUrl;
    }

    public String getAppVarPath() {
        checkConfProvided("appVarPath", appVarPath);
        return appVarPath;
    }

    public Integer getAppArticleMinWc() {
        checkConfProvided("appArticleMinWc", appArticleMinWc);
        return appArticleMinWc;
    }

    public Integer getAppArticleMaxWc() {
        checkConfProvided("appArticleMaxWc", appArticleMaxWc);
        return appArticleMaxWc;
    }

    public Integer getAppArticleAutoDescriptionMinWc() {
        checkConfProvided("appArticleAutoDescriptionMinWc", appArticleAutoDescriptionMinWc);
        return appArticleAutoDescriptionMinWc;
    }

    public Integer getAppArticleTemplateMinPlaceholder() {
        checkConfProvided("appArticleTemplateMinPlaceholder", appArticleTemplateMinPlaceholder);
        return appArticleTemplateMinPlaceholder;
    }

    public Integer getAppArticleTemplateMaxWordsByPlaceholder() {
        checkConfProvided("appArticleTemplateMaxWordsByPlaceholder", appArticleTemplateMaxWordsByPlaceholder);
        return appArticleTemplateMaxWordsByPlaceholder;
    }
    
    public Integer getAppUserArticleTemplateLimit() {
        checkConfProvided("appUserArticleNb", appUserArticleTemplateLimit);
        return appUserArticleTemplateLimit;
    }

    public Integer getAppUserArticleTemplateResetDelay() {
        checkConfProvided("appUserArticleResetDelay", appUserArticleTemplateResetDelay);
        return appUserArticleTemplateResetDelay;
    }

    public Integer getAppUserTemplatePlacholderFillMaxDuration() {
        checkConfProvided("appUserTemplatePlacholderFillMaxDuration", appUserTemplatePlacholderFillMaxDuration);
        return appUserTemplatePlacholderFillMaxDuration;
    }

    

    public String getPersistenceStorageType() {
        checkConfProvided("persistenceDbType", persistenceStorageType);
        return persistenceStorageType;
    }

    public Integer getPersistenceSchemaVersion() {
        checkConfProvided("persistenceSchemaVersion", persistenceSchemaVersion);
        return persistenceSchemaVersion;
    }

    public String getDatasourceUrl() {
        checkConfProvided("datasourceUrl", datasourceUrl);
        return datasourceUrl;
    }

    public String getDatasourceUsername() {
        checkConfProvided("datasourceUsername", datasourceUsername);
        return datasourceUsername;
    }

    public String getDatasourcePassword() {
        checkConfProvided("datasourcePassword", datasourcePassword);
        return datasourcePassword;
    }

    public String getTwitterSigninConsumerKey() {
        checkConfProvided("twitterSigninConsumerKey", twitterSigninConsumerKey);
        return twitterSigninConsumerKey;
    }

    public String getTwitterSigninConsumerSecret() {
        checkConfProvided("twitterSigninConsumerSecret", twitterSigninConsumerSecret);
        return twitterSigninConsumerSecret;
    }

    public String getTwitterMainConsumerKey() {
        checkConfProvided("twitterMainConsumerKey", twitterMainConsumerKey);
        return twitterMainConsumerKey;
    }

    public String getTwitterMainConsumerSecret() {
        checkConfProvided("twitterMainConsumerSecret", twitterMainConsumerSecret);
        return twitterMainConsumerSecret;
    }

    public String getTwitterMainAccessToken() {
        checkConfProvided("twitterMainAccessToken", twitterMainAccessToken);
        return twitterMainAccessToken;
    }

    public String getTwitterMainAccessTokenSecret() {
        checkConfProvided("twitterMainAccessTokenSecret", twitterMainAccessTokenSecret);
        return twitterMainAccessTokenSecret;
    }
    
    
    
    
    protected void checkConfProvided(String name, Object value) {
        if (value == null) {
            throw new IllegalStateException("Config " + name + " not provided");
        }
    }
}
