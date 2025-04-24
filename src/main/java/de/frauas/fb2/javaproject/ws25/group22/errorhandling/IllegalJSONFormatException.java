package de.frauas.fb2.javaproject.ws25.group22.errorhandling;

/**
 * Exception thrown when a JSON file has an illegal format.
 *
 * @author Tobias Ilcken, Younes Wimmer
 */
public class IllegalJSONFormatException extends Exception {
    /**
     * Constructs a new IllegalJSONFormatException with {@code null} as its detail message.
     */
    public IllegalJSONFormatException() {
        super();
    }

    /**
     * Constructs a new IllegalJSONFormatException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public IllegalJSONFormatException(String message) {
        super(message);
    }
}
