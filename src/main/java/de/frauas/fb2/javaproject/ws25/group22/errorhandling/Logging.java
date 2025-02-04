package de.frauas.fb2.javaproject.ws25.group22.errorhandling;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * Provides static utility methods for logging information, warnings, and errors.
 * Utilizes Java's built-in logging framework with a custom formatter.
 *
 * @author Tobias Ilcken
 */
public class Logging {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Initializes the logging system with a specified log file.
     *
     * @param logFileName the name of the log file
     * @throws IllegalArgumentException if the log file name is null.
     */
    public static void initialize(String logFileName) {
        if (logFileName == null) {
            throw new IllegalArgumentException("Log file name cannot be null");
        }
        try {
            CustomFormatter customFormatter = new CustomFormatter();
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(customFormatter);
            LOGGER.addHandler(consoleHandler);
            FileHandler fileHandler = new FileHandler(logFileName);
            fileHandler.setFormatter(customFormatter);
            fileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(fileHandler);
            LOGGER.setUseParentHandlers(false);
            LOGGER.info("Log file has been initialized");
        } catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, "Error opening log file", ioException);
        }
    }
}
