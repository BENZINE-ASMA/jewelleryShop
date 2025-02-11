package Exceptions;
/**
 *  exception for when a required data item is not found.
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
