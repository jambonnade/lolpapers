package de.jambonna.lolpapers.core.model.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Holds stuff to do with a SyntagmeDefinition when the condition is met
 */
public class SyntagmeDefinitionUpdate {
    private final SyntagmeDefinitionCondition condition;
    private final List<String> attributesToDisable;
    private final List<String> attributesToEnable;
    private final List<String> flagsToLimitTo;
    private final List<String> flagsToDisable;
    private final List<String> attributeFlagsToSetTo;
    private final List<String> replAttributesToDisable;
    private final List<String> replAttributesToEnable;
    private final List<String> replFlagsToLimitTo;
    private final List<String> replAttributeFlagsToSetTo;
    private final List<String> flagsToSelect;
    
    private final String ctxVar;
    private final List<String> ctxVarValues;
    private final CtxVarValueType ctxVarValueType;
    private final List<String> ctxSamplesToAdd;

    public SyntagmeDefinitionUpdate(Builder b) {
        condition = b.getCondition();
        
        List<String> attributes;
        List<String> flags;
        attributes = b.getAttributesToDisable();
        attributesToDisable = attributes != null ? Collections.unmodifiableList(new ArrayList<>(attributes)) : null;
        attributes = b.getAttributesToEnable();
        attributesToEnable = attributes != null ? Collections.unmodifiableList(new ArrayList<>(attributes)) : null;
        flags = b.getFlagsToLimitTo();
        flagsToLimitTo = flags != null ? Collections.unmodifiableList(new ArrayList<>(flags)) : null;
        flags = b.getFlagsToDisable();
        flagsToDisable = flags != null ? Collections.unmodifiableList(new ArrayList<>(flags)) : null;
        flags = b.getAttributeFlagsToSetTo();
        attributeFlagsToSetTo = flags != null ? Collections.unmodifiableList(new ArrayList<>(flags)) : null;
        
        attributes = b.getReplAttributesToDisable();
        replAttributesToDisable = attributes != null ? Collections.unmodifiableList(new ArrayList<>(attributes)) : null;
        attributes = b.getReplAttributesToEnable();
        replAttributesToEnable = attributes != null ? Collections.unmodifiableList(new ArrayList<>(attributes)) : null;
        flags = b.getReplFlagsToLimitTo();
        replFlagsToLimitTo = flags != null ? Collections.unmodifiableList(new ArrayList<>(flags)) : null;
        flags = b.getReplAttributeFlagsToSetTo();
        replAttributeFlagsToSetTo = flags != null ? Collections.unmodifiableList(new ArrayList<>(flags)) : null;
        
        flags = b.getFlagsToSelect();
        flagsToSelect = flags != null ? Collections.unmodifiableList(new ArrayList<>(flags)) : null;

        
        ctxVar = b.getCtxVar();
        List<String> varValues = null;
        if (b.getCtxVarValue() != null) {
            varValues = Arrays.asList(b.getCtxVarValue());
        } else if (b.getCtxVarValues() != null) {
            varValues = Arrays.asList(b.getCtxVarValues());
        }
        ctxVarValues = varValues != null ? Collections.unmodifiableList(varValues) : null;
        ctxVarValueType = b.getCtxVarValueType();
        ctxSamplesToAdd = b.getCtxSamplesToAdd() != null ? 
                Collections.unmodifiableList(Arrays.asList(b.getCtxSamplesToAdd())) : null;
    }

    public SyntagmeDefinitionCondition getCondition() {
        return condition;
    }    
    
    public List<String> getAttributesToDisable() {
        return attributesToDisable;
    }

    public List<String> getAttributesToEnable() {
        return attributesToEnable;
    }

    public List<String> getFlagsToLimitTo() {
        return flagsToLimitTo;
    }

    public List<String> getFlagsToDisable() {
        return flagsToDisable;
    }

    public List<String> getAttributeFlagsToSetTo() {
        return attributeFlagsToSetTo;
    }

    public List<String> getReplAttributesToDisable() {
        return replAttributesToDisable;
    }

    public List<String> getReplAttributesToEnable() {
        return replAttributesToEnable;
    }

    public List<String> getReplFlagsToLimitTo() {
        return replFlagsToLimitTo;
    }

    public List<String> getReplAttributeFlagsToSetTo() {
        return replAttributeFlagsToSetTo;
    }


    public List<String> getFlagsToSelect() {
        return flagsToSelect;
    }
    

    public String getCtxVar() {
        return ctxVar;
    }

    public List<String> getCtxVarValues() {
        return ctxVarValues;
    }

    public CtxVarValueType getCtxVarValueType() {
        return ctxVarValueType;
    }

    public List<String> getCtxSamplesToAdd() {
        return ctxSamplesToAdd;
    }
    
    

    
    public static class Builder {
        private final SyntagmeType.Builder parent;
        private ConditionBuilder conditionBuilder;
        private List<String> attributesToDisable;
        private List<String> attributesToEnable;
        private List<String> flagsToLimitTo;
        private List<String> flagsToDisable;
        private List<String> attributeFlagsToSetTo;
        private List<String> replAttributesToDisable;
        private List<String> replAttributesToEnable;
        private List<String> replFlagsToLimitTo;
        private List<String> replAttributeFlagsToSetTo;
        private List<String> flagsToSelect;
        private String ctxVar;
        private String ctxVarValue;
        private String[] ctxVarValues;
        private CtxVarValueType ctxVarValueType;
        private String[] ctxSamplesToAdd;


        public Builder(SyntagmeType.Builder parent) {
            this.parent = parent;
        }
        
        public ConditionBuilder condition() {
            if (conditionBuilder == null) {
                conditionBuilder = new ConditionBuilder(this, null);
            }
            return conditionBuilder;
        }

        public SyntagmeDefinitionCondition getCondition() {
            if (conditionBuilder != null) {
                return conditionBuilder.build();
            }
            return null;
        }

        public Builder disableAttributeFlags(String... attributeCodes) {
            attributesToDisable = Arrays.asList(attributeCodes);
            return this;
        }
        public Builder enableAttributeFlags(String... attributeCodes) {
            attributesToEnable = Arrays.asList(attributeCodes);
            return this;
        }
        public Builder limitAttributeFlagsTo(String... flags) {
            flagsToLimitTo = Arrays.asList(flags);
            return this;
        }
        public Builder disableFlags(String... flags) {
            flagsToDisable = Arrays.asList(flags);
            return this;
        }
        public Builder setAttributeFlagsTo(String... flags) {
            attributeFlagsToSetTo = Arrays.asList(flags);
            return this;
        }
        public Builder setAndLimitAttributeFlagsTo(String... flags) {
            List<String> flagList = Arrays.asList(flags);
            flagsToLimitTo = flagList;
            attributeFlagsToSetTo = flagList;
            return this;
        }
        
        public Builder disableReplAttributeFlags(String... attributeCodes) {
            replAttributesToDisable = Arrays.asList(attributeCodes);
            return this;
        }
        public Builder enableReplAttributeFlags(String... attributeCodes) {
            replAttributesToEnable = Arrays.asList(attributeCodes);
            return this;
        }
        public Builder limitReplAttributeFlagsTo(String... flags) {
            replFlagsToLimitTo = Arrays.asList(flags);
            return this;
        }
        public Builder setReplAttributeFlagsTo(String... flags) {
            replAttributeFlagsToSetTo = Arrays.asList(flags);
            return this;
        }
        public Builder setAndLimitReplAttributeFlagsTo(String... flags) {
            List<String> flagList = Arrays.asList(flags);
            replFlagsToLimitTo = flagList;
            replAttributeFlagsToSetTo = flagList;
            return this;
        }

        
        public List<String> getAttributesToDisable() {
            return attributesToDisable;
        }

        public List<String> getAttributesToEnable() {
            return attributesToEnable;
        }

        public List<String> getFlagsToLimitTo() {
            return flagsToLimitTo;
        }

        public List<String> getFlagsToDisable() {
            return flagsToDisable;
        }

        public List<String> getAttributeFlagsToSetTo() {
            return attributeFlagsToSetTo;
        }

        public List<String> getReplAttributesToDisable() {
            return replAttributesToDisable;
        }

        public List<String> getReplAttributesToEnable() {
            return replAttributesToEnable;
        }

        public List<String> getReplFlagsToLimitTo() {
            return replFlagsToLimitTo;
        }

        public List<String> getReplAttributeFlagsToSetTo() {
            return replAttributeFlagsToSetTo;
        }
        
        
        public List<String> getFlagsToSelect() {        
            return flagsToSelect;
        }

        public Builder setFlagsToSelect(String... flagsToSelect) {
            this.flagsToSelect = Arrays.asList(flagsToSelect);
            return this;
        }
        

        public String getCtxVar() {
            return ctxVar;
        }
        public String getCtxVarValue() {
            return ctxVarValue;
        }
        public String[] getCtxVarValues() {
            return ctxVarValues;
        }
        public CtxVarValueType getCtxVarValueType() {
            return ctxVarValueType;
        }


        public Builder setCtxVarValue(String ctxVar, String ctxVarValue, CtxVarValueType ctxVarValueType) {
            this.ctxVar = ctxVar;
            this.ctxVarValue = ctxVarValue;
            this.ctxVarValueType = ctxVarValueType;
            return this;
        }
        public Builder setCtxVarValue(String ctxVar, String ctxVarValues[], CtxVarValueType ctxVarValueType) {
            this.ctxVar = ctxVar;
            this.ctxVarValues = ctxVarValues;
            this.ctxVarValueType = ctxVarValueType;
            return this;
        }
        


        public String[] getCtxSamplesToAdd() {
            return ctxSamplesToAdd;
        }

        public Builder setCtxSamplesToAdd(String[] ctxSampleToAdd) {
            this.ctxSamplesToAdd = ctxSampleToAdd;
            return this;
        }
        
        
        
        
        
        public SyntagmeType.Builder end() {
            return parent;
        }
        
        public SyntagmeDefinitionUpdate build() {
            return new SyntagmeDefinitionUpdate(this);
        }
    }
    
    public enum CtxVarValueType {
        DEFAULT,
        MULTI,
        RAND
    }
    
    public static class ConditionBuilder {
        private final Builder updateBuilder;
        private final ConditionBuilder parent;
        private List<ConditionBuilder> children;
        private boolean orMode;
        private SyntagmeDefinitionCondition condition;

        public ConditionBuilder(Builder updateBuilder, ConditionBuilder parent) {
            this.updateBuilder = updateBuilder;
            this.parent = parent;
        }
        
        public ConditionBuilder parent() {
            if (parent == null) {
                throw new IllegalStateException("No parent");
            }
            return parent;
        }
                
        public Builder end() {
            return updateBuilder;
        }

        public boolean isOrMode() {
            return orMode;
        }

        public void setOrMode(boolean orMode) {
            this.orMode = orMode;
        }
        
        protected ConditionBuilder newChild() {
            ConditionBuilder child = new ConditionBuilder(updateBuilder, this);
            if (children == null) {
                children = new ArrayList<>();
            }
            children.add(child);
            return child;
        }
        
        public ConditionBuilder flagsCond(String type, boolean flagsSet, String... flags) {
            for (String flag : flags) {
                SyntagmeDefinitionCondition c = 
                        new SyntagmeDefinitionCondition.Flag(type, flag, flagsSet);
                newChild().setCondition(c);
            }
            return this;
        }
        public ConditionBuilder flagsOn(String... flags) {
            return flagsCond(SyntagmeDefinitionCondition.Flag.TYPE_DEF, true, flags);
        }
        public ConditionBuilder flagsOff(String... flags) {
            return flagsCond(SyntagmeDefinitionCondition.Flag.TYPE_DEF, false, flags);
        }
        public ConditionBuilder replFlagsOn(String... flags) {
            return flagsCond(SyntagmeDefinitionCondition.Flag.TYPE_REPL, true, flags);
        }
        public ConditionBuilder origFlagsOn(String... flags) {
            return flagsCond(SyntagmeDefinitionCondition.Flag.TYPE_ORIG_DEF, true, flags);
        }
        public ConditionBuilder origFlagsOff(String... flags) {
            return flagsCond(SyntagmeDefinitionCondition.Flag.TYPE_ORIG_DEF, false, flags);
        }
        public ConditionBuilder origReplFlagsOn(String... flags) {
            return flagsCond(SyntagmeDefinitionCondition.Flag.TYPE_ORIG_REPL, true, flags);
        }
        public ConditionBuilder origReplFlagsOff(String... flags) {
            return flagsCond(SyntagmeDefinitionCondition.Flag.TYPE_ORIG_REPL, false, flags);
        }
        
        public ConditionBuilder textMatch(String pattern) {
            SyntagmeDefinitionCondition c = 
                    new SyntagmeDefinitionCondition.TextMatch(pattern);
            newChild().setCondition(c);
            return this;
        }
        public ConditionBuilder textMatch(String pattern, 
                boolean caseInsensitive, boolean withoutDiacritics) {
            SyntagmeDefinitionCondition c = 
                    new SyntagmeDefinitionCondition.TextMatch(
                        pattern, caseInsensitive, withoutDiacritics);
            newChild().setCondition(c);
            return this;
        }
        
        public ConditionBuilder prevWords(String... words) {
            SyntagmeDefinitionCondition c = 
                    new SyntagmeDefinitionCondition.PrecedingWords(Arrays.asList(words));
            newChild().setCondition(c);
            return this;
        }
        
        public ConditionBuilder and() {
            ConditionBuilder child = newChild();
            return child;
        }
        public ConditionBuilder or() {
            ConditionBuilder child = newChild();
            child.setOrMode(true);
            return child;
        }
        
        
        public ConditionBuilder setCondition(SyntagmeDefinitionCondition condition) {
            this.condition = condition;
            return this;
        }
        
        public SyntagmeDefinitionCondition build() {
            if (condition == null && children != null) {
                List<SyntagmeDefinitionCondition> conds = new ArrayList<>(children.size());
                for (ConditionBuilder cb : children) {
                    conds.add(cb.build());
                }
                if (orMode) {
                    condition = new SyntagmeDefinitionCondition.OneTrue(conds);
                } else {
                    condition = new SyntagmeDefinitionCondition.AllTrue(conds);
                }
            }
            if (condition == null) {
                throw new IllegalStateException("No condition");
            }
            return condition;
        }
    }
}
