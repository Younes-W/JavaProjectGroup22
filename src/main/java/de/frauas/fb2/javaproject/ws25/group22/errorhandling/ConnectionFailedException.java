package de.frauas.fb2.javaproject.ws25.group22.errorhandling;

/**
 * Exception thrown when a connection to the server fails.
 *
 * @author Tobias Ilcken, Younes Wimmer
 */
public class ConnectionFailedException extends Exception {
    /**
     * Constructs a new ConnectionFailedException with {@code null} as its detail message.
     */
    public ConnectionFailedException() {
        super();
    }

    /**
     * Constructs a new ConnectionFailedException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public ConnectionFailedException(String message) {
        super(message);
    }
}
