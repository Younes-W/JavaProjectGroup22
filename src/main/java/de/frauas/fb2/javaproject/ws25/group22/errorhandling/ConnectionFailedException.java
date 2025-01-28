package de.frauas.fb2.javaproject.ws25.group22.errorhandling;

/**
 * A custom exception indicating a failure to connect to the server.
 */
public class ConnectionFailedException extends Exception {
    /** Constructs a new ConnectionFailedException */
    public ConnectionFailedException() {}

    /**
     * Constructs a new ConnectionFailedException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ConnectionFailedException(String message) {
        super(message);
    }
}
