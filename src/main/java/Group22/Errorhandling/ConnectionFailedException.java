package Group22.Errorhandling;

public class ConnectionFailedException extends RuntimeException {
    public ConnectionFailedException() {}
    public ConnectionFailedException(String message) {
        super(message);
    }
}
