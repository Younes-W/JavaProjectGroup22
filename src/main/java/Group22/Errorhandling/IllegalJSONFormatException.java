package Group22.Errorhandling;

public class IllegalJSONFormatException extends Exception {
    public IllegalJSONFormatException() {}

    public IllegalJSONFormatException(String message) {
        super(message);
    }
}
