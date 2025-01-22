package Group22.Util;

import Group22.Errorhandling.IllegalJSONFormatException;
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
