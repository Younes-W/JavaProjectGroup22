package Group22.Errorhandling;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides static utility methods for logging information, warnings, and errors.
 * Utilizes Java's built-in logging framework with a custom formatter.
 */
public class Logging {
    private static final Logger LOGGER = Logger.getLogger(Logging.class.getName());

    /**
     * Initializes the logging system with a specified log file.
     *
     * @param logFileName the name of the log file
     * @throws IllegalArgumentException if the log file name is null.
     */
    public static void initialize(String logFileName) {
        if(logFileName == null){
            throw new IllegalArgumentException("Log file name cannot be null");
        }
        try {
            FileHandler fileHandler = new FileHandler(logFileName);
            CustomFormatter formatter = new CustomFormatter();
            fileHandler.setFormatter(formatter);
            fileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(fileHandler);
            LOGGER.info("Log file has been initialized");
        } catch(IOException e) {
            LOGGER.log(Level.SEVERE,"Error opening log file", e);
        }
    }

    /**
     * Logs an informational message.
     *
     * @param message the message to log.
     */
    public static void info(String message) {
        LOGGER.info(message);
    }

    /**
     * Logs an error message.
     *
     * @param message the error message to log.
     */
    public static void error(String message) {
        LOGGER.severe(message);
    }

    /**
     * Logs a warning message.
     *
     * @param message the warning message to log.
     */
    public static void warning(String message) {
        LOGGER.warning(message);
    }
}
