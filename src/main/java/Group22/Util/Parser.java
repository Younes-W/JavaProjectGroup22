package Group22.Util;

import org.json.JSONObject;

public interface Parser<T> {
    T parse(JSONObject json);
}
