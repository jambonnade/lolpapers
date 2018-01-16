package de.jambonna.lolpapers.core.model.lang;

/**
 * Part of a SyntagmeDefinition related to the replacement
 */
public class SyntagmeReplacementDefinition extends SyntagmeDefinitionAbstract {
    
    private final SyntagmeDefinition definition;
    
    public SyntagmeReplacementDefinition(SyntagmeDefinition definition) {
        super(definition.getType());
        
        this.definition = definition;
        logPrefix = "[REPL] ";
    }

    public SyntagmeDefinition getDefinition() {
        return definition;
    }
    
    @Override
    public boolean isAttributeAllowed(SyntagmeAttribute attr) {
        return !attr.isContext() && !attr.isDefinitionOnly();
    }

    @Override
    public boolean isAttributeRequired(SyntagmeAttribute attr) {
        return true;
    }

    @Override
    public boolean isAttributeMulti(SyntagmeAttribute attr) {
        return true;
    }

    
    @Override
    public void update() {
        getDefinition().update();
    }
    
}
