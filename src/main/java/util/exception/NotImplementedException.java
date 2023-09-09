package util.exception;

public class NotImplementedException extends Exception{
    public NotImplementedException() {
        System.err.println("Not Implemented");
    }

    public NotImplementedException(String message) {
        super(message);
    }
}
