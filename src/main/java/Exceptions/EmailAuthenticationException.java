package Exceptions;

/**
 *  exception for email authentication failures.
 */
public class EmailAuthenticationException extends RuntimeException {
    public EmailAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}