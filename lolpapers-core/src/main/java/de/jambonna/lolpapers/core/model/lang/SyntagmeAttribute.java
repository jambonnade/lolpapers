package de.jambonna.lolpapers.core.model.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Group of flags in a SyntagmeType
 */
public class SyntagmeAttribute {
    private final String code;
    private final String groupCode;
    private final boolean required;
    private final boolean multi;
    private final boolean context;
    private final boolean definitionOnly;
    private final boolean virtual;

    private final Set<SyntagmeFlag> flags;

    public SyntagmeAttribute(Builder builder) {
        code = builder.getCode();
        if (code == null) {
            throw new IllegalArgumentException("No attr code");
        }
        
        groupCode = builder.getGroupCode();
        required = builder.required();
        multi = builder.isMulti();
        context = builder.isContext();
        definitionOnly = builder.isDefinitionOnly();
        virtual = builder.isVirtual();
        
        List<SyntagmeFlag> flagList = new ArrayList<>(builder.getFlagBuilders().size());
        for (SyntagmeFlag.Builder fb : builder.getFlagBuilders().values()) {
            flagList.add(fb.build());
        }
        flags = Collections.unmodifiableSet(new LinkedHashSet<>(flagList));
    }

    public String getCode() {
        return code;
    }

    public String getGroupCode() {
        return groupCode;
    }
    
    public boolean isRequired() {
        return required;
    }
    
    public boolean isMulti() {
        return multi;
    }

    public boolean isContext() {
        return context;
    }

    public boolean isDefinitionOnly() {
        return definitionOnly;
    }

    public boolean isVirtual() {
        return virtual;
    }


    public Set<SyntagmeFlag> getFlags() {
        return flags;
    }

    
    
    
    public static class Builder {
        private String code;
        private final SyntagmeType.Builder parent;
        private String groupCode;
        private boolean required;
        private boolean multi;
        private boolean context;
        private boolean definitionOnly;
        private boolean virtual;
        private final Map<String, SyntagmeFlag.Builder> flagBuilders;

        
        public Builder(SyntagmeType.Builder parent) {
            this.parent = parent;
            groupCode = null;
            required = false;
            multi = false;
            context = false;
            definitionOnly = false;
            flagBuilders = new LinkedHashMap<>();
        }
        
        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public String getCode() {
            return code;
        }

        public String getGroupCode() {
            return groupCode;
        }

        public Builder setGroupCode(String groupCode) {
            this.groupCode = groupCode;
            return this;
        }

        
        public boolean required() {
            return required;
        }

        public Builder setRequired(boolean required) {
            this.required = required;
            return this;
        }

        public boolean isMulti() {
            return multi;
        }

        public Builder setMulti(boolean multi) {
            this.multi = multi;
            return this;
        }

        public boolean isContext() {
            return context;
        }

        public Builder setContext(boolean context) {
            this.context = context;
            return this;
        }
                
        public boolean isDefinitionOnly() {
            return definitionOnly;
        }

        public Builder setDefinitionOnly(boolean definitionOnly) {
            this.definitionOnly = definitionOnly;
            return this;
        }

        public boolean isVirtual() {
            return virtual;
        }

        public Builder setVirtual(boolean virtual) {
            this.virtual = virtual;
            return this;
        }


        public Map<String, SyntagmeFlag.Builder> getFlagBuilders() {
            return flagBuilders;
        }
        

                
        
        public SyntagmeFlag.Builder flag(String code) {
            
            SyntagmeFlag.Builder builder = flagBuilders.get(code);
            if (builder == null) {
                builder = new SyntagmeFlag.Builder(this);
                builder.setCode(code);
                flagBuilders.put(code, builder);
            }
            return builder;
        }
        
        public Builder removeFlag(String code) {
            flagBuilders.remove(code);
            return this;
        }
        
        public Builder moveFlagToBottom(String code) {
            SyntagmeFlag.Builder builder = flagBuilders.remove(code);
            if (builder != null) {
                flagBuilders.put(code, builder);
            }
            return this;
        }

        public SyntagmeType.Builder end() {
            return parent;
        }
        
        public SyntagmeAttribute build() {
            SyntagmeAttribute sa = new SyntagmeAttribute(this);
            return sa;
        }
    }
}
