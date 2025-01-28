package de.frauas.fb2.javaproject.ws25.group22.util;

import de.frauas.fb2.javaproject.ws25.group22.errorhandling.IllegalJSONFormatException;
import org.json.JSONObject;

/**
 * A generic interface for parsing a JSONObject into an object of type T.
 * @param <T> the type of object to be returned by the parser.
 */
public interface Parser<T> {
    /**
     * Parses a JSONObject into an object of type T.
     * @param o the JSONObject to parse.
     * @return an instance of type T.
     */
    T parse(JSONObject o) throws IllegalJSONFormatException;
}
