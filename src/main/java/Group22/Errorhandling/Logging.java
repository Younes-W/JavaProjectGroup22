package Group22.Errorhandling;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {
    private static final Logger LOGGER = Logger.getLogger(Logging.class.getName());

    public static void initialize(String logFileName){
        if(logFileName == null){
            throw new IllegalArgumentException("Log file name cannot be null");
        }
        try{
            FileHandler fileHandler = new FileHandler(logFileName);
            CustomFormatter formatter = new CustomFormatter();
            fileHandler.setFormatter(formatter);
            fileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(fileHandler);
            LOGGER.info("Log file has been initialized");
        }catch(IOException e){
            LOGGER.log(Level.SEVERE,"Error opening log file", e);
        }
    }
    public static void info(String message) {
        LOGGER.info(message);
    }
    public static void error(String message) {LOGGER.severe(message);}
    public static void warning(String message) {
        LOGGER.warning(message);
    }
}