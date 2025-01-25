package Group22.Errorhandling;

/**
 * A custom exception indicating a wrongly formatted JSON file has been received.
 */

public class IllegalJSONFormatException extends Exception {
    public IllegalJSONFormatException() {}

    public IllegalJSONFormatException(String message) {
        super(message);
    }
}
