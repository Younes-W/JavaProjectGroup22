package Group22.Errorhandling;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter{
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