package de.frauas.fb2.javaproject.ws25.group22.util;

import de.frauas.fb2.javaproject.ws25.group22.errorhandling.IllegalJSONFormatException;
import org.json.JSONObject;

/**
 * Abstract base class for parsing JSON objects into specific types.
 *
 * @param <T> the type of object to be returned by the parser.
 *
 * @author Younes Wimmer, Tobias Ilcken
 */
public abstract class BaseParser<T> implements Parser<T> {
    /**
     * Parses a JSONObject into an object of type T.
     *
     * @param o the JSONObject to parse.
     * @return an instance of type T.
     * @throws IllegalJSONFormatException if the JSON format is invalid.
     */
    @Override
    public abstract T parse(JSONObject o) throws IllegalJSONFormatException;

    protected abstract boolean validate(JSONObject o);
}
