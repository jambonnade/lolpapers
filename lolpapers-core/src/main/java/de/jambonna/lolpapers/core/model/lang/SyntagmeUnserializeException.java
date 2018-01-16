package de.jambonna.lolpapers.core.model.lang;

public class SyntagmeUnserializeException extends Exception {

    private final String code;
    
    public SyntagmeUnserializeException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
