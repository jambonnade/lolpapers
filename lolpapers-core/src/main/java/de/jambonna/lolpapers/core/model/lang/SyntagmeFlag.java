package de.jambonna.lolpapers.core.model.lang;

/**
 * Holds information about a flag in a SyntagmeType
 */
public class SyntagmeFlag {    
    private final String code;
    private final String standaloneGeneralLabel;
    private final String quickCode;

    private SyntagmeFlag(Builder b) {
        code = b.getCode();
        if (code == null) {
            throw new IllegalArgumentException("No flag code");
        }
        standaloneGeneralLabel = b.getStandaloneGeneralLabel();
//        if (standaloneGeneralLabel == null) {
//            throw new IllegalArgumentException("No flag label");
//        }
        quickCode = b.getQuickCode();
    }

    public String getCode() {
        return code;
    }
    
    public String getStandaloneGeneralLabel() {
        return standaloneGeneralLabel;
    }

    public String getQuickCode() {
        return quickCode;
    }

    
    
    public static class Builder {
        private final SyntagmeAttribute.Builder parent;
        private String code;
        private String standaloneGeneralLabel;
        private String quickCode;

        public Builder(SyntagmeAttribute.Builder parent) {
            this.parent = parent;
        }
        
        public String getCode() {
            return code;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setStandaloneGeneralLabel(String label) {
            this.standaloneGeneralLabel = label;
            return this;
        }
        
        public String getStandaloneGeneralLabel() {
            return standaloneGeneralLabel;
        }

        public String getQuickCode() {
            return quickCode;
        }

        public Builder setQuickCode(String quickCode) {
            this.quickCode = quickCode;
            return this;
        }        

        public SyntagmeAttribute.Builder end() {
            return parent;
        }

        public SyntagmeFlag build() {
            return new SyntagmeFlag(this);
        }
    }
}
