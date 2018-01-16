package de.jambonna.lolpapers.web.model.content;

import de.jambonna.lolpapers.core.model.TemplatePlaceholder;
import de.jambonna.lolpapers.core.model.lang.Language;
import de.jambonna.lolpapers.core.model.lang.SyntagmeDefinition;
import de.jambonna.lolpapers.core.model.lang.SyntagmeType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Web version of a TemplatePlaceholder used in the javascript template editor
 */
public class Placeholder {
    private Long id;
    private String reference;
    private Integer fromWordId;
    private Integer nbWords;
    private String text;
    private String stCode;
    private String[] sdFlags;
    private String[] sdAttributes;
    private String[] srFlags;
    private String[] srAttributes;
    private String usePlaceholder;
    private boolean changed;
    private boolean removed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getFromWordId() {
        return fromWordId;
    }

    public void setFromWordId(Integer fromWordId) {
        this.fromWordId = fromWordId;
    }

    public Integer getNbWords() {
        return nbWords;
    }

    public void setNbWords(Integer nbWords) {
        this.nbWords = nbWords;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public String getStCode() {
        return stCode;
    }

    public void setStCode(String stCode) {
        this.stCode = stCode;
    }

    public String[] getSdFlags() {
        return sdFlags;
    }

    public void setSdFlags(String[] sdFlags) {
        this.sdFlags = sdFlags;
    }

    public String[] getSdAttributes() {
        return sdAttributes;
    }

    public void setSdAttributes(String[] sdAttributes) {
        this.sdAttributes = sdAttributes;
    }

    public String[] getSrFlags() {
        return srFlags;
    }

    public void setSrFlags(String[] srFlags) {
        this.srFlags = srFlags;
    }

    public String[] getSrAttributes() {
        return srAttributes;
    }

    public void setSrAttributes(String[] srAttributes) {
        this.srAttributes = srAttributes;
    }

    public String getUsePlaceholder() {
        return usePlaceholder;
    }

    public void setUsePlaceholder(String usePlaceholder) {
        this.usePlaceholder = usePlaceholder;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
    
    
    public void setFromTemplatePlaceholder(TemplatePlaceholder tp) {
        setId(tp.getTemplatePlaceholderId());
        setReference(tp.getReference());
        setFromWordId(tp.getFromWord());
        setNbWords(tp.getNbWords());
        
        SyntagmeDefinition sd = tp.getDefinitionSD();
        if (sd != null) {
            setText(sd.getText());
            setStCode(sd.getType().getCode());
            setSdFlags(sd.getChosenFlags().toArray(new String[] {}));
            setSdAttributes(sd.getChosenAttributes().toArray(new String[] {}));
            setSrFlags(sd.getReplacementDefinition().getChosenFlags().toArray(new String[] {}));
            setSrAttributes(sd.getReplacementDefinition().getChosenAttributes().toArray(new String[] {}));            
        } else {
            setText(null);
            setStCode(null);
            setSdFlags(null);
            setSdAttributes(null);
            setSrFlags(null);
            setSrAttributes(null);
        }
        setUsePlaceholder(tp.getUsePlaceholder());
    }
    
    public void updateTemplatePlaceholder(TemplatePlaceholder tp) {
        tp.setReference(getReference());
        tp.setFromWord(getFromWordId());
        tp.setNbWords(getNbWords());
        
        SyntagmeDefinition sd = null;
        if (tp.getContentTemplate() != null) {
            Language l = tp.getContentTemplate().getLanguage();
            SyntagmeType st = l.getSyntagmeTypes().get(getStCode());
            if (st != null) {
                sd = new SyntagmeDefinition(st);
                List<String> flags = Collections.emptyList();
                if (getSdFlags() != null) {
                    flags = Arrays.asList(getSdFlags());
                }
                List<String> attributes = Collections.emptyList();
                if (getSdAttributes()!= null) {
                    attributes = Arrays.asList(getSdAttributes());
                }
                sd.resetChosenFlags(attributes, flags);

                flags = Collections.emptyList();
                if (getSrFlags() != null) {
                    flags = Arrays.asList(getSrFlags());
                }
                attributes = Collections.emptyList();
                if (getSrAttributes()!= null) {
                    attributes = Arrays.asList(getSrAttributes());
                }
                sd.getReplacementDefinition().resetChosenFlags(attributes, flags);
                
                sd.setText(getText());
            }
        }
        tp.setDefinitionSD(sd);
        tp.setOrigText(getText());
        tp.setUsePlaceholder(getUsePlaceholder());
    }
    
    public SyntagmeDefinition getSyntagmeDefinition(Language l) {
        SyntagmeType st = l.getSyntagmeType(getStCode());
        if (st != null) {
            SyntagmeDefinition sd = new SyntagmeDefinition(st);
            List<String> flags = Collections.emptyList();
            if (getSdFlags() != null) {
                flags = Arrays.asList(getSdFlags());
            }
            List<String> attributes = Collections.emptyList();
            if (getSdAttributes()!= null) {
                attributes = Arrays.asList(getSdAttributes());
            }
            sd.resetChosenFlags(attributes, flags);
            
            flags = Collections.emptyList();
            if (getSrFlags() != null) {
                flags = Arrays.asList(getSrFlags());
            }
            attributes = Collections.emptyList();
            if (getSrAttributes()!= null) {
                attributes = Arrays.asList(getSrAttributes());
            }
            
            sd.getReplacementDefinition().resetChosenFlags(attributes, flags);
            
            sd.setText(getText());
            return sd;
        }
        return null;
    }
}
