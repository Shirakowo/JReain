package dev.shirako.reain;

public class ReainException extends RuntimeException {
    public ReainException() {
        super();
    }

    public ReainException(String message) {
        super(message);
    }

    public ReainException(Throwable cause) {
        super(cause);
    }

    public ReainException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ReainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
