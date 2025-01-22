package Group22.Util;

import org.json.JSONObject;

/**
 * Abstract base class for parsing JSON objects into specific types.
 *
 * @param <T> the type of object to be returned by the parser.
 */
public abstract class BaseParser<T> implements Parser<T> {
    /**
     * Parses a JSONObject into an object of type T.
     *
     * @param o the JSONObject to parse.
     * @return an instance of type T.
     */
    @Override
    public abstract T parse(JSONObject o);
}
