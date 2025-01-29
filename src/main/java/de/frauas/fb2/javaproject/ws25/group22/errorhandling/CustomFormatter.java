package de.frauas.fb2.javaproject.ws25.group22.errorhandling;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Custom log formatter to structure log messages with a specific timestamp and format.
 *
 * @author Younes Wimmer, Tobias Ilcken, Parnia Esfahani
 */
public class CustomFormatter extends Formatter {
    /**
     * Formats the given log record as a string with a timestamp, log level, and message.
     *
     * @param logRecord the log record to format.
     * @return the formatted log message.
     */
    @Override
    public String format(LogRecord logRecord) {
        String timestamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(logRecord.getMillis());
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(timestamp);
        builder.append("]");
        builder.append(" [");
        builder.append(logRecord.getLevel());
        builder.append("] ");
        builder.append(logRecord.getMessage());
        builder.append("\n");
        return builder.toString();
    }
}
