package Group22.Errorhandling;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Custom log formatter to structure log messages with a specific timestamp and format.
 */
public class CustomFormatter extends Formatter {
    /**
     * Formats the given log record as a string with a timestamp, log level, and message.
     *
     * @param record the log record to format.
     * @return the formatted log message.
     */
    @Override
    public String format(LogRecord record) {
        String timestamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(record.getMillis());
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(timestamp);
        builder.append("]");
        builder.append(" [");
        builder.append(record.getLevel());
        builder.append("] ");
        builder.append(record.getMessage());
        builder.append("\n");
        return builder.toString();
    }
}
