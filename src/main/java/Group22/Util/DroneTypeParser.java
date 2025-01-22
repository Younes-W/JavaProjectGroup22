package Group22.Util;

import Group22.API.DroneType;
import Group22.Errorhandling.IllegalJSONFormatException;
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
    public DroneType parse (JSONObject o) throws IllegalJSONFormatException {
        if(validate(o)){
            int id = o.getInt("id");
            String manufacturer = o.getString("manufacturer");
            String typename = o.getString("typename");
            int weight = o.getInt("weight");
            int maxSpeed = o.getInt("max_speed");
            int batteryCapacity = o.getInt("battery_capacity");
            int controlRange = o.getInt("control_range");
            int maxCarriage = o.optInt("max_carriage",0);

            return new DroneType(id, manufacturer, typename, weight, maxSpeed,
                    batteryCapacity, controlRange, maxCarriage);
        }else{
            throw new IllegalJSONFormatException();
        }
    }

    private boolean validate(JSONObject o) {
        String[] attributes = {"id","manufacturer","typename","weight","max_speed","battery_capacity","control_range"};
        for(String attribute : attributes){
            if(!o.has(attribute)){
                return false;
            }
        }
        return true;
    }
}
