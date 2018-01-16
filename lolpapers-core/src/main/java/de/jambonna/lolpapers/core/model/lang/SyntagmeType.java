package de.jambonna.lolpapers.core.model.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A word group type. Each type has its own attributes and flags
 */
public class SyntagmeType {
    private final String code;
    private final transient Language language;
    private final Map<String, SyntagmeAttribute> attributes;
    private final transient Map<String, SyntagmeAttribute> attributeByFlagCode;
    private final List<SyntagmeDefinitionUpdate> updates;
    private final Pattern allowByPattern;
    private final boolean referencesAllowed;

    
    
    public SyntagmeType(Builder b) {
        code = b.getCode();
        language = b.getLanguage();
        
        Map<String, SyntagmeAttribute> attrMap = 
                new LinkedHashMap<>(b.getAttributeBuilders().size());
        Map<String, SyntagmeAttribute> attrByFlag = new HashMap<>();
        for (SyntagmeAttribute.Builder sab : b.getAttributeBuilders().values()) {
            SyntagmeAttribute sa = sab.build();
            attrMap.put(sa.getCode(), sa);
            
            for (SyntagmeFlag sf : sa.getFlags()) {
                String flagCode = sf.getCode();
                if (flagCode == null) {
                    throw new IllegalArgumentException(
                            "Flag for " + sa.getCode() + " has no code");
                }
                if (attrByFlag.get(flagCode) != null) {
                    throw new IllegalArgumentException(
                            "Flag " + flagCode + " defined more than once");
                }
                attrByFlag.put(sf.getCode(), sa);
            }
        }
        attributes = Collections.unmodifiableMap(attrMap);
        attributeByFlagCode = Collections.unmodifiableMap(attrByFlag);
        
        List<SyntagmeDefinitionUpdate> updateList = new ArrayList<>(b.getUpdateBuilders().size());
        for (SyntagmeDefinitionUpdate.Builder ub : b.getUpdateBuilders()) {
           updateList.add(ub.build());
        }
        updates = Collections.unmodifiableList(updateList);
        allowByPattern = b.getAllowByPattern() != null ? 
                Pattern.compile(b.getAllowByPattern()) : null;
        referencesAllowed = b.isReferencesAllowed();
    }
    
    public String getCode() {
        return code;
    }

    public Language getLanguage() {
        return language;
    }
    
    public Map<String, SyntagmeAttribute> getAttributes() {
        return attributes;
    }
        
    public SyntagmeAttribute getAttribute(String code) {
        SyntagmeAttribute sa = getAttributes().get(code);
        if (sa == null) {
            throw new IllegalArgumentException("Invalid attribute \"" + code + "\"");
        }
        return sa;
    }
    
    public SyntagmeAttribute getAttributeByFlagCode(String code) {
        SyntagmeAttribute sa = attributeByFlagCode.get(code);
        if (sa == null) {
            throw new IllegalArgumentException("Invalid flag \"" + code + "\"");
        }
        return sa;
    }
    
    public boolean flagExsits(String code) {
        return attributeByFlagCode.get(code) != null;
    }

    public List<SyntagmeDefinitionUpdate> getUpdates() {
        return updates;
    }

    public Pattern getAllowByPattern() {
        return allowByPattern;
    }

    public boolean isReferencesAllowed() {
        return referencesAllowed;
    }
    
    
    
    public static class Builder {
        private String code;
        private Language language;
        private String allowByPattern;
        private boolean referencesAllowed;
        
        private final Map<String, SyntagmeAttribute.Builder> attributeBuilders = new LinkedHashMap<>();
        private final List<SyntagmeDefinitionUpdate.Builder> updateBuilders = new ArrayList<>();
        
        public Builder(Language language, String code) {
            this.language = language;
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Language getLanguage() {
            return language;
        }

        public Builder setLanguage(Language language) {
            this.language = language;
            return this;
        }
        
        
        public SyntagmeAttribute.Builder attribute(String code) {
            SyntagmeAttribute.Builder builder = attributeBuilders.get(code);
            if (builder == null) {
                builder = new SyntagmeAttribute.Builder(this);
                builder.setCode(code);
                attributeBuilders.put(code, builder);
            }
            return builder;
        }

        public Map<String, SyntagmeAttribute.Builder> getAttributeBuilders() {
            return attributeBuilders;
        }
        
        public Builder removeAttribute(String code) {
            attributeBuilders.remove(code);
            return this;
        }
        
        public Builder moveAttributeToBottom(String code) {
            SyntagmeAttribute.Builder builder = attributeBuilders.remove(code);
            if (builder != null) {
                attributeBuilders.put(code, builder);
            }
            return this;
        }
                

        public SyntagmeDefinitionUpdate.Builder update() {
            SyntagmeDefinitionUpdate.Builder builder = new SyntagmeDefinitionUpdate.Builder(this);
            updateBuilders.add(builder);
            return builder;
        }

        public List<SyntagmeDefinitionUpdate.Builder> getUpdateBuilders() {
            return updateBuilders;
        }


                
        public String getAllowByPattern() {
            return allowByPattern;
        }

        public void setAllowByPattern(String allowByPattern) {
            this.allowByPattern = allowByPattern;
        }

        public boolean isReferencesAllowed() {
            return referencesAllowed;
        }

        public void setReferencesAllowed(boolean referencesAllowed) {
            this.referencesAllowed = referencesAllowed;
        }
        
    }
}