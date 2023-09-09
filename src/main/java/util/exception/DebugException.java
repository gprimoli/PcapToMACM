package util.exception;

public class DebugException extends Exception{
    public DebugException() {
        System.err.println("DEBUG");
    }

    public DebugException(String message) {
        super(message);
    }
}
