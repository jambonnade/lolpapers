package de.jambonna.lolpapers.core.model.lang;

import de.jambonna.lolpapers.core.model.UserException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds flag selection for a given SyntagmeType
 */
public abstract class SyntagmeDefinitionAbstract {
    
    public static final String SERIALIZATION_MAIN_SEPARATOR = ":";
    public static final String SERIALIZATION_SECONDARY_SEPARATOR = ",";
            
    
    private static final Logger logger = LoggerFactory.getLogger(SyntagmeDefinitionAbstract.class);

    private final SyntagmeType type;
    private final Map<String, SyntagmeFlagState> flags;
    private final Set<String> chosenFlags;
    private final Set<String> chosenAttributes;
    private final Set<String> pubChosenFlags;
    private final Set<String> pubChosenAttributes;
//    private Phase phase;
    protected String logPrefix;


    public SyntagmeDefinitionAbstract(SyntagmeType type) {
        if (type == null) {
            throw new IllegalArgumentException("Invalid syntagme type");
        }
        
        this.type = type;
        flags = new HashMap<>();
        chosenFlags = new HashSet<>();
        chosenAttributes = new HashSet<>();
        pubChosenFlags = Collections.unmodifiableSet(chosenFlags);
        pubChosenAttributes = Collections.unmodifiableSet(chosenAttributes);
        logPrefix = "";
    }
    
    public SyntagmeType getType() {
        return type;
    }
    
    public boolean isA(String typeCode) {
        return getType().getCode().equals(typeCode);
    }

    public boolean isAttributeAllowed(SyntagmeAttribute attr) {
        return false;
    }
    
    public boolean isAttributeRequired(SyntagmeAttribute attr) {
        return attr.isRequired();
    }
    
    public boolean isAttributeMulti(SyntagmeAttribute attr) {
        return attr.isMulti();
    }

    public boolean isAttributeCodeValid(String attrCode) {
        try {
            SyntagmeAttribute attr = getType().getAttribute(attrCode);
            return isAttributeAllowed(attr);
        } catch (IllegalArgumentException ex) {
        }
        return false;
    }
    
    public boolean isFlagValid(String flagCode) {
        try {
            SyntagmeAttribute attr = getAttributeByFlagCode(flagCode);
            return true;
        } catch (IllegalArgumentException ex) {
        }
        return false;
    }

    
    public SyntagmeAttribute getAttributeByFlagCode(String flagCode) {
        SyntagmeAttribute attr = getType().getAttributeByFlagCode(flagCode);
        if (!isAttributeAllowed(attr)) {
            throw new IllegalArgumentException("Invalid attribute " + attr.getCode());
        }
        return attr;
    }
    
    public SyntagmeAttribute getAttribute(String attrCode) {
        SyntagmeAttribute attr = getType().getAttribute(attrCode);
        if (!isAttributeAllowed(attr)) {
            throw new IllegalArgumentException("Invalid attribute " + attrCode);
        }
        return attr;
    }
    
    
        
    public void selectFlag(String code, boolean flagOn) {
        SyntagmeAttribute attr = getAttributeByFlagCode(code);
        if (getFlagState(code) != SyntagmeFlagState.DISABLED) {
            if (flagOn) {
                setFlagOn(code);
            } else {
                setFlagOff(code);
            }
            updateAttributeChosenFlags(attr);
            update();
        }
    }
    
    public void update() {
        
    }
    
    public void selectFlag(String code) {
        selectFlag(code, true);
    }
    public void unselectFlag(String code) {
        selectFlag(code, false);
    }

    public void updateAttributeChosenFlags(SyntagmeAttribute attr) {
        for (SyntagmeFlag flag : attr.getFlags()) {
            String flagCode = flag.getCode();
            if (getFlagState(flagCode) == SyntagmeFlagState.ON) {
                setChosenFlag(flagCode);
            } else {
                unsetChosenFlag(flagCode);
            }
        }
    }

    public void setChosenFlag(String flag) {
        logger.debug("{}Setting chosen flag {}", logPrefix, flag);
        
        SyntagmeAttribute attr = getAttributeByFlagCode(flag);
        
        if (!isAttributeMulti(attr)) {
            for (SyntagmeFlag sf : attr.getFlags()) {
                String curCode = sf.getCode();
                if (!flag.equals(curCode)) {
                    unsetChosenFlag(curCode);
                }
            }
        }

        chosenAttributes.add(attr.getCode());

        chosenFlags.add(flag);
    }
    public void unsetChosenFlag(String flag) {
        logger.debug("{}Unsetting chosen flag {}", logPrefix, flag);
        
        SyntagmeAttribute attr = getAttributeByFlagCode(flag);
        chosenAttributes.add(attr.getCode());

        chosenFlags.remove(flag);
    }
    
    public boolean hasChosenFlag(String flag) {
        return chosenFlags.contains(flag);
    }
    
    public void resetChosenFlags(Collection<String> attributes, Collection<String> flags) {
        chosenAttributes.clear();
        chosenFlags.clear();

        for (String attrCode : attributes) {
            if (isAttributeCodeValid(attrCode) && !getAttribute(attrCode).isVirtual()) {
                chosenAttributes.add(attrCode);
            }
        }
        
        for (String flagCode : flags) {
            if (isFlagValid(flagCode) && !getAttributeByFlagCode(flagCode).isVirtual()) {
                setChosenFlag(flagCode);
            }
        }
    }
    
    public void resetChosenFlags(Collection<String> flags) {
        Collection<String> attributes = Collections.emptyList();
        resetChosenFlags(attributes, flags);
    }

    public Set<String> getChosenFlags() {
        return pubChosenFlags;
    }

    public Set<String> getChosenAttributes() {
        return pubChosenAttributes;
    }

    
    
    public void updateFlagsFromChoices() {
        logger.debug("{}Updating flags from flag choices...", logPrefix);

        for (String attrCode : chosenAttributes) {
            SyntagmeAttribute attr = getAttribute(attrCode);
            for (SyntagmeFlag f : attr.getFlags()) {
                String flagCode = f.getCode();
                if (chosenFlags.contains(flagCode)) {
                    setFlagOn(flagCode);
                } else {
                    setFlagOff(flagCode);
                }
            }
        }
    }
    

    protected boolean setFlagState(String code, SyntagmeFlagState state) {
        logger.debug("{}Setting flag {} to {}", logPrefix, code, state);
        if (!isFlagValid(code)) {
            throw new IllegalArgumentException("Invalid flag " + code);
        }
        SyntagmeFlagState previous = flags.put(code, state);
        return previous != state;
    }
    
    public SyntagmeFlagState getFlagState(String code) {
        return flags.getOrDefault(code, SyntagmeFlagState.OFF);
    }
    
    public boolean isFlagOn(String code) {
        return getFlagState(code) == SyntagmeFlagState.ON;
    }
    public boolean isFlagEnabled(String code) {
        return getFlagState(code) != SyntagmeFlagState.DISABLED;
    }
    public Set<String> getFlagsOn(Boolean context) {
        Set<String> result = new HashSet<>(flags.size());
        for (Map.Entry<String, SyntagmeFlagState> entry : flags.entrySet()) {
            String flagCode = entry.getKey();
            boolean addIt = true;
            if (context != null) {
                SyntagmeAttribute attr = getAttributeByFlagCode(flagCode);
                addIt = (context == attr.isContext());
            }
            if (addIt && entry.getValue() == SyntagmeFlagState.ON) {
                result.add(flagCode);
            }
        }
        return result;
    }
    public Set<String> getFlagsOn() {
        return getFlagsOn(null);
    }
    public Set<SyntagmeFlag> getSpecificAttributeFlagsOn(SyntagmeAttribute attr) {
        Set<SyntagmeFlag> result = new LinkedHashSet<>(attr.getFlags().size());
        boolean everythingOn = true;
        for (SyntagmeFlag f : attr.getFlags()) {
            String flagCode = f.getCode();
            if (isFlagOn(flagCode)) {
                result.add(f);
            } else {
                everythingOn = false;
            }
        }
        if (everythingOn) {
            result = Collections.emptySet();
        }
        return result;
    }
    
    protected void clearFlags() {
        logger.debug("{}Clearing flags", logPrefix);
        flags.clear();
    }
    
    protected void enableFlag(String code) {
        if (getFlagState(code) == SyntagmeFlagState.DISABLED) {
            setFlagState(code, SyntagmeFlagState.OFF);
        }
    }
    protected void disableFlag(String code) {
        setFlagState(code, SyntagmeFlagState.DISABLED);
    }

    protected boolean setFlagOn(String code) {
        boolean result = false;
        if (getFlagState(code) != SyntagmeFlagState.DISABLED) {
            result = setFlagState(code, SyntagmeFlagState.ON);
            if (result) {
                SyntagmeAttribute attr = getType().getAttributeByFlagCode(code);
                if (!isAttributeMulti(attr)) {
                    for (SyntagmeFlag sf : attr.getFlags()) {
                        String curCode = sf.getCode();
                        if (!code.equals(curCode)) {
                            setFlagOff(curCode);
                        }
                    }
                }
            }
        }
        return result;
    }
    protected boolean setFlagOff(String code) {
        if (getFlagState(code) != SyntagmeFlagState.DISABLED) {
            return setFlagState(code, SyntagmeFlagState.OFF);
        }
        return false;
    }
    
    protected void disableAttributeFlags(String attributeCode) {
        logger.debug("{}Disabling {} attribute flags", logPrefix, attributeCode);
        SyntagmeAttribute attr = getAttribute(attributeCode);
        
        for (SyntagmeFlag f : attr.getFlags()) {
            disableFlag(f.getCode());
        }
    }
    
    protected void enableAllAttributeFlags(String attributeCode) {
        logger.debug("{}Enabling {} attribute flags", logPrefix, attributeCode);
        SyntagmeAttribute attr = getAttribute(attributeCode);
        
        for (SyntagmeFlag f : attr.getFlags()) {
            enableFlag(f.getCode());
        }
    }
    
    protected void limitAttributeFlagsTo(Collection<String> flags) {
        logger.debug("{}Limiting attribute flags to {}", logPrefix, flags);

        for (SyntagmeAttribute attr : getType().getAttributes().values()) {
            if (!isAttributeAllowed(attr)) {
                continue;
            }
            
            boolean foundFlag = false;
            for (SyntagmeFlag flag : attr.getFlags()) {
                if (flags.contains(flag.getCode())) {
                    foundFlag = true;
                    break;
                }
            }
            if (foundFlag) {
                for (SyntagmeFlag flag : attr.getFlags()) {
                    String flagCode = flag.getCode();
                    if (flags.contains(flagCode)) {
                        enableFlag(flagCode);
                    } else {
                        disableFlag(flagCode);
                    }
                }
            }
        }
    }
    
    protected void setAttributeFlagsTo(Collection<String> flags) {
        logger.debug("{}Setting attribute flags to {}", logPrefix, flags);

        for (SyntagmeAttribute attr : getType().getAttributes().values()) {
            if (!isAttributeAllowed(attr)) {
                continue;
            }
            
            boolean foundFlag = false;
            for (SyntagmeFlag flag : attr.getFlags()) {
                if (flags.contains(flag.getCode())) {
                    foundFlag = true;
                    break;
                }
            }
            if (foundFlag) {
                for (SyntagmeFlag flag : attr.getFlags()) {
                    String flagCode = flag.getCode();
                    if (flags.contains(flagCode)) {
                        setFlagOn(flagCode);
                    } else {
                        setFlagOff(flagCode);
                    }
                }
            }
        }
    }
    
    public void checkRequiredAttributes() throws UserException {
        for (SyntagmeAttribute attr : getType().getAttributes().values()) {
            if (!isAttributeAllowed(attr) || !isAttributeRequired(attr)) {
                continue;
            }
            
            String attrCode = attr.getCode();
            boolean found = false;
            boolean oneAvailable = false;
            for (SyntagmeFlag flag : attr.getFlags()) {
                SyntagmeFlagState state = getFlagState(flag.getCode());
                if (state != SyntagmeFlagState.DISABLED) {
                    oneAvailable = true;
                }
                if (state == SyntagmeFlagState.ON) {
                    found = true;
                    break;
                }
            }
            if (oneAvailable && !found) {
                throw new UserException("unset-attr-" + attrCode, 
                        "Nothing selected for attribute " + attrCode);
            }
        }
    }
    
    public void validate() throws UserException {
        checkRequiredAttributes();
    }
    
    @Override
    public String toString() {
        return getStringRepresentation();
    }
    
    public String getStringRepresentation() {
        return getStringRepresentationFor(null);
    }
    public String getStringRepresentation(String... limitToAttributes) {
        return getStringRepresentationFor(Arrays.asList(limitToAttributes));
    }
    
    public String getStringRepresentationFor(List<String> limitToAttributes) {
        StringBuilder sb = new StringBuilder();
        for (SyntagmeAttribute attr : getType().getAttributes().values()) {
            if (!isAttributeAllowed(attr)) {
                continue;
            }
            
            String attrCode = attr.getCode();
            if (limitToAttributes != null && !limitToAttributes.contains(attrCode)) {
                continue;
            }
            
            sb.append(attrCode);
            if (chosenAttributes.contains(attrCode)) {
                sb.append("= ");
            } else {
                sb.append(": ");
            }

            for (SyntagmeFlag flag : attr.getFlags()) {
                sb.append(flag.getCode());
                if (chosenFlags.contains(flag.getCode())) {
                    switch (getFlagState(flag.getCode())) {
                        case DISABLED:
                            sb.append("%");
                            break;
                        case OFF:
                            sb.append("/");
                            break;
                        case ON:
                            sb.append("*");
                            break;
                    }
                } else {
                    switch (getFlagState(flag.getCode())) {
                        case DISABLED:
                            sb.append("#");
                            break;
                        case OFF:
                            sb.append("_");
                            break;
                        case ON:
                            sb.append("+");
                            break;
                    }
                }
                sb.append(" ");
            }
        }
        return sb.toString();
    }

//    public Phase getPhase() {
//        return phase;
//    }
//
//    public void setPhase(Phase phase) {
//        this.phase = phase;
//    }
//    
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(getType().getCode());
        sb.append(SERIALIZATION_MAIN_SEPARATOR);
        // Put them in ST order
        boolean hasItem = false;
        for (SyntagmeAttribute attr : getType().getAttributes().values()) {
            if (chosenAttributes.contains(attr.getCode())) {
                if (hasItem) {
                    sb.append(SERIALIZATION_SECONDARY_SEPARATOR);
                }
                sb.append(attr.getCode());
                hasItem = true;
            }
        }
        sb.append(SERIALIZATION_MAIN_SEPARATOR);
        hasItem = false;
        for (SyntagmeAttribute attr : getType().getAttributes().values()) {
            for (SyntagmeFlag flag : attr.getFlags()) {
                if (chosenFlags.contains(flag.getCode())) {
                    if (hasItem) {
                        sb.append(SERIALIZATION_SECONDARY_SEPARATOR);
                    }
                    sb.append(flag.getCode());
                    hasItem = true;
                }
            }
        }
        return sb.toString();
    }
    
    
    public void setFromDefinitionInfo(DefinitionInfo info) {
        resetChosenFlags(info.getAttributes(), info.getFlags());
    }
    
    public static DefinitionInfo parseSerializedDefinition(String definition, Language language)
            throws SyntagmeUnserializeException {
        String[] parts = StringUtils.splitByWholeSeparatorPreserveAllTokens(
                definition, SERIALIZATION_MAIN_SEPARATOR);
        if (parts.length != 3) {
            throw new SyntagmeUnserializeException(
                    "invalid-format", "Invalid serialized definition format");
        }
        String stypeCode = parts[0];
        String attributes = parts[1];
        String flags = parts[2];

        SyntagmeType st = language.getSyntagmeTypes().get(stypeCode);
        if (st == null) {
            throw new SyntagmeUnserializeException("invalid-stype", "Unknown syntagme type");
        }

        DefinitionInfo info = new DefinitionInfo(st);
        info.setAttributes(Arrays.asList(attributes.split(SERIALIZATION_SECONDARY_SEPARATOR)));
        info.setFlags(Arrays.asList(flags.split(SERIALIZATION_SECONDARY_SEPARATOR)));
        return info;
    }
    
    
    public static class DefinitionInfo {
        private final SyntagmeType stype;
        private List<String> flags;
        private List<String> attributes;

        public DefinitionInfo(SyntagmeType stype) {
            if (stype == null) {
                throw new IllegalArgumentException("Invalid syntagme type");
            }
            this.stype = stype;
            flags = Collections.emptyList();
        }

        
        public SyntagmeType getStype() {
            return stype;
        }

        public List<String> getFlags() {
            return flags;
        }

        public void setFlags(List<String> flags) {
            if (flags == null) {
                throw new IllegalArgumentException("Invalid flag list");
            }
            this.flags = flags;
        }

        public List<String> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<String> attributes) {
            if (attributes == null) {
                throw new IllegalArgumentException("Invalid attribute list");
            }
            this.attributes = attributes;
        }
        

    }

//    public static enum Phase {
//        DEFINITION,
//        REPLACEMENT
//    }
}
