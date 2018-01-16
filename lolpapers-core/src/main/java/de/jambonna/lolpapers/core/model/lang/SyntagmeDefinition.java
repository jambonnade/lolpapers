package de.jambonna.lolpapers.core.model.lang;

import de.jambonna.lolpapers.core.model.UserException;
import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinitionUpdate.CtxVarValueType;
import de.jambonna.lolpapers.core.model.text.Text;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Definition of a word group (syntagme), its context and its possible replacement
 */
public class SyntagmeDefinition extends SyntagmeDefinitionAbstract {
    public static final String CTX_VAR_PREFIX = "PREFIX";
    public static final String CTX_VAR_SUFFIX = "SUFFIX";
    public static final String CTX_VAR_TEXT = "TEXT";
    
    
    private static final Logger logger = LoggerFactory.getLogger(SyntagmeDefinition.class);
    
//    private SyntagmeDefinitionContext contextDefinition;
    private SyntagmeReplacementDefinition replacementDefinition;
    private SyntagmeDefinition origDefinition;
    private String text;
    private Text textText;
    private String precedingText;
    private Text precedingTextText;
    private Set<String> manuallyDisabledFlags;
    private Set<String> flagsToSelect;
    private final Map<String, Set<String>> ctxVars;
    private final Map<String, CtxVarValueType> ctxVarsValueType;
    private final List<List<String>> ctxSamples;
    private Pattern ctxSampleVarPattern;
    private Random ctxRng;
    private String ctxMultiVarSep = " / ";

    
    public SyntagmeDefinition(SyntagmeType type) {
        super(type);
        
        ctxVars = new HashMap<>();
        ctxVarsValueType = new HashMap<>();
        ctxSamples = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        textText = null;
        update();
    }

    public Text getTextText() {
        if (textText == null) {
            textText = new Text(getType().getLanguage(), text != null ? text : "");
        }
        return textText;
    }
    
    public void setPrecedingText(String precedingText) {
        this.precedingText = precedingText;
        precedingTextText = null;
        update();
    }

    public Text getPrecedingTextText() {
        if (precedingTextText == null) {
            precedingTextText = new Text(getType().getLanguage(), 
                    precedingText != null ? precedingText : "");
        }
        return precedingTextText;
    }
    
    
    @Override
    public boolean isAttributeAllowed(SyntagmeAttribute attr) {
        return true;
    }

//    @Override
//    public boolean isAttributeRequired(SyntagmeAttribute attr) {
//        return attr.isRequired() && (attr.isContext() || attr.isDefinitionOnly());
//    }

    
    
    public String getSelectedAttributeFlag(String attributeCode) {
        SyntagmeAttribute sa = getType().getAttributeByFlagCode(attributeCode);
        for (SyntagmeFlag sf : sa.getFlags()) {
            String curCode = sf.getCode();
            if (isFlagOn(curCode)) {
                return curCode;
            }
        }
        return null;
    }

    public void setManuallyDisabledFlags(Collection<String> manuallyDisabledFlags) {
        this.manuallyDisabledFlags = manuallyDisabledFlags != null ? 
                new HashSet<>(manuallyDisabledFlags) : null;
    }
    
    protected void updateManuallyDisabledFlags() {
        if (manuallyDisabledFlags != null) {
            logger.debug("{}Updating manually disabled flags...", logPrefix);
            for (String flag : manuallyDisabledFlags) {
                disableFlag(flag);
            }
        }
    }

    @Override
    protected void clearFlags() {
        super.clearFlags();
        getReplacementDefinition().clearFlags();
    }

    @Override
    public void updateFlagsFromChoices() {
        super.updateFlagsFromChoices();
        
        getReplacementDefinition().updateFlagsFromChoices();
    }
    
    
    @Override
    public void update() {
        logger.debug("{}Updating flags...", logPrefix);
        
        SyntagmeReplacementDefinition sdr = getReplacementDefinition();
        
        clearFlags();
        updateManuallyDisabledFlags();
        updateFlagsFromChoices();
        
        flagsToSelect = null;
        ctxVars.clear();
        ctxSamples.clear();
        
        int count = 0;
        for (SyntagmeDefinitionUpdate update : getType().getUpdates()) {
            count++;
            SyntagmeDefinitionCondition condition = update.getCondition();
            if (condition != null) {
                logger.debug("{}Update {} : checking condition..", logPrefix, count);
                if (!condition.evaluate(this)) {
                    continue;
                }
            }
            logger.debug("{}Update {} : applying actions..", logPrefix, count);
            
            List<String> attributes;
            List<String> flags;
            
            attributes = update.getAttributesToDisable();
            if (attributes != null) {
                for (String attr : attributes) {
                    disableAttributeFlags(attr);
                }
            }
            attributes = update.getAttributesToEnable();
            if (attributes != null) {
                for (String attr : attributes) {
                    enableAllAttributeFlags(attr);
                }
            }
            flags = update.getFlagsToLimitTo();
            if (flags != null) {
                limitAttributeFlagsTo(flags);
            }
            flags = update.getFlagsToDisable();
            if (flags != null) {
                for (String flag : flags) {
                    disableFlag(flag);
                }
            }
            flags = update.getAttributeFlagsToSetTo();
            if (flags != null) {
                setAttributeFlagsTo(flags);
            }

            attributes = update.getReplAttributesToDisable();
            if (attributes != null) {
                for (String attr : attributes) {
                    sdr.disableAttributeFlags(attr);
                }
            }
            attributes = update.getReplAttributesToEnable();
            if (attributes != null) {
                for (String attr : attributes) {
                    sdr.enableAllAttributeFlags(attr);
                }
            }
            flags = update.getReplFlagsToLimitTo();
            if (flags != null) {
                sdr.limitAttributeFlagsTo(flags);
            }
            flags = update.getReplAttributeFlagsToSetTo();
            if (flags != null) {
                sdr.setAttributeFlagsTo(flags);
            }

            
//            attributes = update.getCtxAttributesToDisable();
//            if (attributes != null) {
//                for (String attr : attributes) {
//                    getContextDefinition().disableAttributeFlags(attr);
//                }
//            }
//            attributes = update.getCtxAttributesToEnable();
//            if (attributes != null) {
//                for (String attr : attributes) {
//                    getContextDefinition().enableAllAttributeFlags(attr);
//                }
//            }
//            flags = update.getCtxFlagsToLimitTo();
//            if (flags != null) {
//                getContextDefinition().limitAttributeFlagsTo(flags);
//            }
//            flags = update.getCtxAttributeFlagsToSetTo();
//            if (flags != null) {
//                getContextDefinition().setAttributeFlagsTo(flags);
//            }

            if (update.getFlagsToSelect() != null) {
                if (flagsToSelect == null) {
                    flagsToSelect = new HashSet<>();
                } else {
                    flagsToSelect.clear();
                }
                flagsToSelect.addAll(update.getFlagsToSelect());
            }

            String ctxVar = update.getCtxVar();
            if (ctxVar != null) {
                Set<String> varValues = ctxVars.get(ctxVar);
                if (varValues == null) {
                    varValues = new LinkedHashSet<>(1);
                    ctxVars.put(update.getCtxVar(), varValues);
                }
                CtxVarValueType curType = ctxVarsValueType.get(ctxVar);
                CtxVarValueType newType = update.getCtxVarValueType();
                // Clear previous values if was or is a default value
                if (curType == CtxVarValueType.DEFAULT || newType == CtxVarValueType.DEFAULT) {
                    varValues.clear();
                }
                ctxVarsValueType.put(ctxVar, newType);
                
                if (update.getCtxVarValues() != null) {
                    varValues.addAll(update.getCtxVarValues());
                }
            }
            if (update.getCtxSamplesToAdd() != null) {
                ctxSamples.add(update.getCtxSamplesToAdd());
            }
            
            updateManuallyDisabledFlags();
            updateFlagsFromChoices();
        }
    }


    public Random getCtxRng() {
        return ctxRng;
    }

    public void setCtxRng(Random ctxRng) {
        this.ctxRng = ctxRng;
    }

    public String getCtxMultiVarSep() {
        return ctxMultiVarSep;
    }

    public void setCtxMultiVarSep(String ctxMultiVarSep) {
        this.ctxMultiVarSep = ctxMultiVarSep;
    }
    
    public Set<String> getFlagsToSelect() {
        return flagsToSelect;
    }

    public Map<String, Set<String>> getCtxVars() {
        return ctxVars;
    }
    
    public Set<String> getCtxVarValues(String var) {
        return ctxVars.get(var);
    }
    
    public String getCtxVarStrValue(String var) {
        Set<String> values = getCtxVarValues(var);
        if (values != null && !values.isEmpty()) {
            if (values.size() > 1) {
                switch (ctxVarsValueType.get(var)) {
                    case MULTI:
                        return String.join(getCtxMultiVarSep(), values);
                    case RAND:
                        Random rng = getCtxRng();
                        if (rng != null) {
                            List<String> lstValues = new ArrayList<>(values);
                            return lstValues.get(rng.nextInt(lstValues.size()));
                        }
                        break;
                }
            }
            // Takes the first value as default
            return values.iterator().next();
        }
        return null;
    }

    public String getCtxPrefix() {
        return getCtxVarStrValue(CTX_VAR_PREFIX);
    }
    public String getCtxSuffix() {
        return getCtxVarStrValue(CTX_VAR_SUFFIX);
    }
    
    public List<List<String>> getCtxSamples() {
        return ctxSamples;
    }
    
    public List<String> getFinalCtxSamples(String placeholder) {
        List<String> result = new ArrayList<>(ctxSamples.size());
        for (List<String> samples : getCtxSamples()) {
            String sample = null;
            if (samples.size() > 1 && getCtxRng() != null) {
                sample = samples.get(getCtxRng().nextInt(samples.size()));
            } else if (!samples.isEmpty()) {
                sample = samples.get(0);
            }

            String finalSample = resolveCtxSampleVars(sample, placeholder, 0);
            if (finalSample != null) {
                result.add(finalSample);
            }
        }
        return result;
    }
    
    protected String resolveCtxSampleVars(String text, String placeholder, int depthCount) {
        
        if (ctxSampleVarPattern == null) {
            ctxSampleVarPattern = Pattern.compile("\\{(\\w+)\\}");
        }

        Matcher m = ctxSampleVarPattern.matcher(text);
        StringBuilder sb = new StringBuilder();
        int lastPos = 0;
        boolean resolveAgain = false;
        while (m.find()) {
            sb.append(text.subSequence(lastPos, m.start()));
            
            String var = m.group(1);
            String varValue = null;
            if (CTX_VAR_TEXT.equals(var)) {
                varValue = placeholder;
            } else {
                varValue = getCtxVarStrValue(var);
            }
            
            // Some variable undefined = consider the sample impossible
            if (varValue == null) {
                return null;
            }
            sb.append(varValue);
            
            lastPos = m.end();
            resolveAgain = true;
        }
        
        sb.append(text.subSequence(lastPos, text.length()));
        
        if (resolveAgain) {
            if (depthCount > 10) {
                throw new IllegalStateException("Infinite recursion");
            }
            return resolveCtxSampleVars(sb.toString(), placeholder, ++depthCount);
        }
        
        return sb.toString();
    }
    
    public boolean isAvailable() {
        Pattern pattern = getType().getAllowByPattern();
        if (pattern != null) {
            String theText = getText();
            if (theText != null) {
                Matcher matcher = pattern.matcher(theText);
                return matcher.find();
            }
            return false;
        }
        return true;
    }
    
    @Override
    public void validate() throws UserException {
        validateDef();
        
        try {
            getReplacementDefinition().validate();
        } catch (UserException ex) {
            throw new UserException("repl-" + ex.getCode(), ex.getMessage());
        }
    }
    
    public void validateDef() throws UserException {
        if (!isAvailable()) {
            throw new UserException("unavailable", 
                    "This syntagme type is not available");
        }
        
        super.validate();
    }
    
    @Override
    public String toString() {
        return getStringRepresentation() + 
                " [R] " + getReplacementDefinition().getStringRepresentation();
    }

//    public SyntagmeDefinitionContext getContextDefinition() {
//        if (contextDefinition == null) {
//            contextDefinition = new SyntagmeDefinitionContext(getType());
//        }
//        return contextDefinition;
//    }


//    public void initReplacementDefinition() {
//        replacementDefinition = new SyntagmeReplacementDefinition(this);
//    }
    
//    public SyntagmeReplacementDefinition getReplacementDefinition() {
//        return replacementDefinition;
//    }
//    public SyntagmeReplacementDefinition getSureReplacementDefinition() {
//        SyntagmeReplacementDefinition srd = getReplacementDefinition();
//        if (srd == null) {
//            throw new IllegalStateException("No replacement");
//        }
//        return srd;
//    }
    public SyntagmeReplacementDefinition getReplacementDefinition() {
        if (replacementDefinition == null) {
            replacementDefinition = new SyntagmeReplacementDefinition(this);
        }
        return replacementDefinition;
    }

//    public void setReplacementDefinition(SyntagmeReplacementDefinition replacement) {
//        if (replacement != null && !replacement.getType().equals(getType())) {
//            throw new IllegalArgumentException("Invalid replacement");
//        }
//        this.replacementDefinition = replacement;
//    }

    public SyntagmeDefinition getOrigDefinition() {
        return origDefinition;
    }

    public void setOrigDefinition(SyntagmeDefinition origDefinition) {
        if (origDefinition != null && !getType().equals(origDefinition.getType())) {
            throw new IllegalArgumentException("Invalid orig definition");
        }
        this.origDefinition = origDefinition;
    }

    
    public static SyntagmeDefinition unserialize(String serialized, Language language) 
            throws SyntagmeUnserializeException {
        DefinitionInfo info = parseSerializedDefinition(serialized, language);
        SyntagmeDefinition sd = new SyntagmeDefinition(info.getStype());
        sd.setFromDefinitionInfo(info);
        return sd;
    }
    public static SyntagmeDefinition unserialize(
            String serializedDef, String serializedRepl, Language language) 
            throws SyntagmeUnserializeException {
        SyntagmeDefinition sd = unserialize(serializedDef, language);
        
        if (serializedRepl != null && serializedRepl.length() > 0) {
            sd.unserializeReplacement(serializedRepl);
        }
        return sd;
    }
    
    public void unserializeReplacement(String serialized) 
            throws SyntagmeUnserializeException {
        DefinitionInfo info = parseSerializedDefinition(serialized, getType().getLanguage());
        if (!info.getStype().equals(getType())) {
            throw new SyntagmeUnserializeException(
                    "invalid-repl-type", "Invalid repl definition type");
        }
        getReplacementDefinition().setFromDefinitionInfo(info);
    }
    
//    public static SyntagmeDefinition unserialize(
//            String definition, String replDefinition, Language language) 
//            throws SyntagmeUnserializeException {
//        DefinitionInfo info = parseSerializedDefinition(definition, language);
//        SyntagmeDefinition sd = new SyntagmeDefinition(info.getStype());
//        sd.setFromDefinitionInfo(info);
//        
//        if (replDefinition != null && replDefinition.length() > 0) {
//            DefinitionInfo infoRepl = parseSerializedDefinition(replDefinition, language);
//            if (!infoRepl.getStype().equals(sd.getType())) {
//                throw new SyntagmeUnserializeException("invalid-repl-type", "Invalid repl definition type");
//            }
//            SyntagmeReplacementDefinition sdr = sd.getReplacementDefinition();
//            sdr.setFromDefinitionInfo(infoRepl);
//        }
//        return sd;
//    }
    
    
}
