package Exceptions;

/**
 *  exception for dashboard loading failures.
 */
public class DashboardLoadingException extends RuntimeException {
    public DashboardLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}