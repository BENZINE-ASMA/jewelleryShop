package Exceptions;

/**
 *  exception for SQL execution failures.
 */
public class SQLExecutionException extends RuntimeException {
    public SQLExecutionException(String message) {
        super(message);
    }

    public SQLExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}

