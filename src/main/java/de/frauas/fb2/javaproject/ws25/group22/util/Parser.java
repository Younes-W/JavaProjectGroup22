package de.frauas.fb2.javaproject.ws25.group22.util;

import de.frauas.fb2.javaproject.ws25.group22.errorhandling.IllegalJSONFormatException;
import org.json.JSONObject;

/**
 * A generic interface for parsing a JSONObject into an object of type T.
 *
 * @param <T> the type of object to be returned by the parser.
 *
 * @author Younes Wimmer, Tobias Ilcker, Parnia Esfahani
 */
public interface Parser<T> {
    /**
     * Parses a JSONObject into an object of type T.
     *
     * @param o the JSONObject to parse.
     * @return an instance of type T.
     * @throws IllegalJSONFormatException if the JSON format is invalid.
     */
    T parse(JSONObject o) throws IllegalJSONFormatException;
}
