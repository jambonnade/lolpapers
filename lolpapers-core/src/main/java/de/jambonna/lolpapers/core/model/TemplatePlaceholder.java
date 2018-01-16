package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.app.App;
import de.jambonna.lolpapers.core.model.lang.Language;
import de.jambonna.lolpapers.core.model.lang.SyntagmeAttribute;
import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinition;
import de.jambonna.lolpapers.core.model.lang.SyntagmeFlag;
import de.jambonna.lolpapers.core.model.lang.SyntagmeReplacementDefinition;
import de.jambonna.lolpapers.core.model.lang.SyntagmeUnserializeException;
import de.jambonna.lolpapers.core.model.lang.fr.FrLanguage;
import de.jambonna.lolpapers.core.model.text.ContentProcessor;
import de.jambonna.lolpapers.core.validation.BeanValidator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A placeholder in a ContentTemplate. 
 * Spans over a word group in the ContentTemplate and holds a SyntagmeDefinition 
 * for this word group and its replacement.
 * Holds replacement information too, used in the replacement phase.
 */
@Entity
public class TemplatePlaceholder extends ModelAbstract {
    
    private static final Logger logger = LoggerFactory.getLogger(TemplatePlaceholder.class);

    
    @Id
    private Long templatePlaceholderId;
    
    @Version
    private Long versionNumber;

    @ManyToOne
    @JoinColumn(name = "contentTemplateId")
    private ContentTemplate contentTemplate;
    
    @ManyToOne
    @JoinColumn(name = "createdByUserId")
    private User createdBy;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    private String reference;
    
    private Integer fromWord;
    private Integer nbWords;
    @Size(max = 250)
    private String origText;
    
    private String usePlaceholder;
    private String semanticLinkWith;
    private String definition;
    private String definitionReplacement;
    
    @Transient
    private SyntagmeDefinition definitionSD;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date replacementStartedAt;
    
    @ManyToOne
    @JoinColumn(name = "replacementByUserId")
    private User replacementBy;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date replacementAt;
    
    @Size(max = 250)
    private String replacementText;
    
    private String replacementDefinitionFlags;
    
    @Transient
    private SyntagmeDefinition replacementSD;
    
    @Transient
    private Language language;

    
    @Override
    public void init(App app) {
        super.init(app);
        if (getContentTemplate() != null) {
            getContentTemplate().init(app);
        }
    }

    
    public Long getTemplatePlaceholderId() {
        return templatePlaceholderId;
    }

    public void setTemplatePlaceholderId(Long templatePlaceholderId) {
        this.templatePlaceholderId = templatePlaceholderId;
    }

    public ContentTemplate getContentTemplate() {
        return contentTemplate;
    }

    public void setContentTemplate(ContentTemplate contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getFromWord() {
        return fromWord;
    }

    public void setFromWord(Integer fromWord) {
        this.fromWord = fromWord;
    }

    public Integer getNbWords() {
        return nbWords;
    }

    public void setNbWords(Integer nbWords) {
        this.nbWords = nbWords;
    }

    public int getOrigTextMaxLg() {
        return BeanValidator.getInstance().getPropertyMaxLength(TemplatePlaceholder.class, "origText");
    }

    public String getOrigText() {
        return origText;
    }

    public void setOrigText(String origText) {
        this.origText = origText;
        SyntagmeDefinition sd = definitionSD;
        if (sd != null) {
            sd.setText(origText);
        }
    }

    public String getUsePlaceholder() {
        return usePlaceholder;
    }

    public void setUsePlaceholder(String usePlaceholder) {
        this.usePlaceholder = usePlaceholder;
    }

    public String getSemanticLinkWith() {
        return semanticLinkWith;
    }

    public void setSemanticLinkWith(String semanticLinkWith) {
        this.semanticLinkWith = semanticLinkWith;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
        definitionSD = null;
    }

    public String getDefinitionReplacement() {
        return definitionReplacement;
    }

    public void setDefinitionReplacement(String definitionReplacement) {
        this.definitionReplacement = definitionReplacement;
        definitionSD = null;
    }

    public SyntagmeDefinition getDefinitionSD() {
        if (definitionSD == null) {
            try {
                definitionSD = unserializeDefinition();
            } catch (SyntagmeUnserializeException ex) {
                logger.error("Unable to unserialize sd", ex);
            }
        }
        return definitionSD;
    }
    
    public SyntagmeDefinition getSureDefinitionSD() {
        SyntagmeDefinition sd = getDefinitionSD();
        if (sd == null) {
            throw new IllegalStateException("No definition for placeholder");
        }
        return sd;
    }
    
    public SyntagmeDefinition unserializeDefinition() throws SyntagmeUnserializeException {
        SyntagmeDefinition result = null;
        String serializedDefinition = getDefinition();
        String serializedReplDefinition = getDefinitionReplacement();
        if (serializedDefinition != null && serializedDefinition.length() > 0) {
            result = SyntagmeDefinition.unserialize(
                    serializedDefinition, serializedReplDefinition, 
                    getLanguage());

            result.setText(getOrigText());
        }
        return result;
    }

    public void setDefinitionSD(SyntagmeDefinition definitionSD) {
        this.definitionSD = definitionSD;
        updateSerializedDefinition();
    }
    
    public void updateSerializedDefinition() {
        definition = null;
        definitionReplacement = null;
        origText = null;
        SyntagmeDefinition sd = definitionSD;
        if (sd != null) {
            definition = sd.serialize();
            definitionReplacement = sd.getReplacementDefinition().serialize();
            origText = sd.getText();
        }
    }
    
    public void validatePlaceholder() throws UserException {        
        if (getReference() == null) {
            throw new UserException("missing-ref", "Missing placeholder ref");
        }
        if (getFromWord() == null || getNbWords() == null) {
            throw new UserException("missing-words", "Missing placeholder words");
        }
        if (getOrigText() == null) {
            throw new UserException("missing-text", "Missing placeholder orig text");
        }
        
        ContentTemplate t = getContentTemplate();
        ContentProcessor p = t.getLanguage().getContentProcessor();
        
        int startWordId = getFromWord();
        Integer sid = p.getWordSentenceId(t.getActualContentElements(), startWordId);
        if (sid == null) {
            throw new UserException("invalid-word-start", "Invalid start word");
        }
        int lastWordId = startWordId + getNbWords() - 1;
        Integer lastwSid = p.getWordSentenceId(t.getActualContentElements(), lastWordId);
        if (lastwSid == null || lastWordId < startWordId || !lastwSid.equals(sid)) {
            throw new UserException("invalid-word-end", "Invalid end word");
        }
        
        String realText = p.getWordRangeText(t.getActualContentElements(), startWordId, getNbWords());
        logger.debug("Checking sd orig text '{}' VS real '{}'", getOrigText(), realText);
        
        // Input text has to be normalized the same way getWordRangeText does
        if (!getOrigText().equals(realText)) {
            throw new UserException("invalid-orig-text", "Invalid placeholder text");
        }
    }
    
    public void validatePlaceholderDefinition() throws UserException {
        if (getUsePlaceholder() == null) {
            if (getDefinitionSD() == null) {
                throw new UserException("missing-sd", "Missing placeholder SD");
            }
            try {
                getDefinitionSD().validate();
            } catch (UserException ex) {
                throw new UserException("sd-" + ex.getCode(), ex.getMessage());
            }
        }
    }
    
    
    public boolean isReplacementNeeded() {
        return getUsePlaceholder() == null;
    }

    public Date getReplacementStartedAt() {
        return replacementStartedAt;
    }

    public void setReplacementStartedAt(Date replacementStartedAt) {
        this.replacementStartedAt = replacementStartedAt;
    }

    
    public User getReplacementBy() {
        return replacementBy;
    }

    public void setReplacementBy(User replacementBy) {
        this.replacementBy = replacementBy;
    }

    public Date getReplacementAt() {
        return replacementAt;
    }

    public void setReplacementAt(Date replacementAt) {
        this.replacementAt = replacementAt;
    }

    public int getReplacementTextMaxLg() {
        return BeanValidator.getInstance().getPropertyMaxLength(TemplatePlaceholder.class, "replacementText");
    }
    
    public String getReplacementText() {
        return replacementText;
    }

    public void setReplacementText(String replacementText) {
        this.replacementText = replacementText;
        replacementSD = null;
    }

    public String getReplacementDefinitionFlags() {
        return replacementDefinitionFlags;
    }

    public void setReplacementDefinitionFlags(String replacementDefinitionFlags) {
        this.replacementDefinitionFlags = replacementDefinitionFlags;
        replacementSD = null;
    }

    public void normalizeReplacementText() {
        SyntagmeDefinition sd = getReplacementSD();
        String normalizedText = sd.getType().getLanguage().normalizeSyntagmeText(sd);
        sd.setText(normalizedText);
        replacementText = normalizedText;
    }
//    public void setReplacement(String text, Collection<String> supDefFlags) {
//        SyntagmeDefinition sd = createReplacementSD(supDefFlags);
        
//        List<String> finalFlags = new ArrayList<>();
//        
//        // Copy context flags
//        Set<String> flagsOn = origSd.getFlagsOn();
//        for (String flagCode : flagsOn) {
//            SyntagmeAttribute attr = sd.getAttributeByFlagCode(flagCode);
//            if (attr.isContext()) {
//                finalFlags.add(flagCode);
//            }
//        }
//        
//        // Copy input def flags
//        for (String flagCode : defFlags) {
//            SyntagmeAttribute attr = sd.getAttributeByFlagCode(flagCode);
//            if (!attr.isContext()) {
//                finalFlags.add(flagCode);
//            }
//        }
        
//        sd.resetChosenFlags(defFlags);
//
//        sd.setText(text);
//        String normalizedText = sd.getType().getLanguage().normalizeSyntagmeText(sd);
//        sd.setText(normalizedText);
//        
//        replacementSD = sd;
//        replacementText = normalizedText;
//        
//        updateSerializedReplacement();
//    }
    
    public SyntagmeDefinition getReplacementSD() {
        if (replacementSD == null) {
            replacementSD = unserializeReplacementSD();
        }
        return replacementSD;
    }
    
    public SyntagmeDefinition createReplacementSD(Collection<String> supDefFlags) {
        SyntagmeDefinition origSd = getSureDefinitionSD();
        SyntagmeDefinition sd = new SyntagmeDefinition(origSd.getType());
        sd.setOrigDefinition(origSd);
        
        Set<String> flags = origSd.getFlagsOn(true);
        if (supDefFlags != null) {
            flags.addAll(supDefFlags);
        }
        sd.resetChosenFlags(flags);
        return sd;
    }
    
    public SyntagmeDefinition unserializeReplacementSD() {
        List<String> supDefFlags = Collections.emptyList();
        String strFlags = getReplacementDefinitionFlags();
        if (strFlags != null) {
            supDefFlags = Arrays.asList(strFlags.split(","));
        }
        
        SyntagmeDefinition sd = createReplacementSD(supDefFlags);
        sd.setText(getReplacementText());
        sd.selectFlag(FrLanguage.FLAG_COMMON_PHASE_REPL);
        return sd;
    }

    public void updateSerializedReplacement() {
        replacementDefinitionFlags = null;
        SyntagmeDefinition sd = replacementSD;
        if (sd != null) {
            replacementDefinitionFlags = String.join(",", sd.getChosenFlags());
        }
    }
    
    public boolean isReplacementFilled() {
        return getReplacementAt() != null;
    }
    
    public void validateReplacement() throws UserException {
        SyntagmeDefinition defSd = getDefinitionSD();
        
        if (!isReplacementNeeded() || defSd == null) {
            throw new IllegalStateException("Invalid placeholder to replace");
        }
        
        normalizeReplacementText();
        SyntagmeDefinition sd = getReplacementSD();
        sd.validateDef();
        
        if (!sd.isFlagOn(Language.FLAG_COMMON_PHASE_REPL)) {
            throw new UserException("invalid-phase", "Invalid phase flag");
        }
        if (!sd.isFlagOn(Language.FLAG_COMMON_REUSABLE_ON)) {
            throw new UserException("not-reusable", "Invalid reusable flag");
        }

        defSd.update();
        SyntagmeReplacementDefinition defSdr = defSd.getReplacementDefinition();
        
        for (SyntagmeAttribute attr : defSd.getType().getAttributes().values()) {
            if (attr.isContext()) {
                // Check context flags are same
                for (SyntagmeFlag flag : attr.getFlags()) {
                    String flagCode = flag.getCode();
                    if (defSd.isFlagOn(flagCode) != sd.isFlagOn(flagCode)) {
                        throw new UserException("invalid-ctx-repl-flag", "Invalid replacement context flag");
                    }
                }
            } else if (!attr.isDefinitionOnly()) {
                // Check definition flags are allowed in the original replacement 
                // or are "definition only" flags
                for (SyntagmeFlag flag : attr.getFlags()) {
                    String flagCode = flag.getCode();
                    if (sd.isFlagOn(flagCode) && !defSdr.isFlagOn(flagCode)) {
                        throw new UserException("invalid-repl-flag", "Invalid replacement flag");
                    }
                }
            }
        }
    }

    public Language getLanguage() {
        ContentTemplate ct = getContentTemplate();
        if (ct != null) {
            return ct.getLanguage();
        }
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
