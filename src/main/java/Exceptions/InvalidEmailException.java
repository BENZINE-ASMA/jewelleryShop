package Exceptions;

/**
 *  exception for invalid email addresses.
 */
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
