package exception;

public class NoGroupFoundException extends RuntimeException{
    public NoGroupFoundException() {
        super();
    }

    public NoGroupFoundException(String message) {
        super(message);
    }

    public NoGroupFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
