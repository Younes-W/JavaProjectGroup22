package Group22.Errorhandling;

/**
 * A custom exception indicating a failure to connect to a remote service or resource.
 */
public class ConnectionFailedException extends RuntimeException {
    /** Constructs a new ConnectionFailedException with no detail message. */
    //TODO no usage rn
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
