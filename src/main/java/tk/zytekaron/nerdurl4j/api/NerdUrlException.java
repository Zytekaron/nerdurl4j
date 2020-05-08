package tk.zytekaron.nerdurl4j.api;

public class NerdUrlException extends RuntimeException {
    
    public NerdUrlException(String message) {
        super(message);
    }
    
    public NerdUrlException(Exception exception) {
        super(exception);
    }
}