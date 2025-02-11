package Exceptions;

/**
 *  exception for image loading failures.
 */
public class ImageLoadingException extends RuntimeException {
    public ImageLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}