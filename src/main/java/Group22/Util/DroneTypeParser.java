package Group22.Util;

import Group22.API.DroneType;
import org.json.JSONObject;

/**
 * Parser for converting JSON objects into {@link DroneType} instances.
 */
public class DroneTypeParser extends BaseParser<DroneType> {
    /**
     * Parses a JSONObject into a DroneType instance.
     *
     * @param o the JSONObject representing a drone type.
     * @return a new DroneType object.
     */
    @Override
    public DroneType parse(JSONObject o) {
        int id = o.getInt("id");
        String manufacturer = o.getString("manufacturer");
        String typename = o.getString("typename");
        int weight = o.getInt("weight");
        int maxSpeed = o.getInt("max_speed");
        int batteryCapacity = o.getInt("battery_capacity");
        int controlRange = o.getInt("control_range");
        int maxCarriage = o.getInt("max_carriage");

        return new DroneType(id, manufacturer, typename, weight, maxSpeed,
                batteryCapacity, controlRange, maxCarriage);
    }
}
