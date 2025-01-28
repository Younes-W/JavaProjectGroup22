package de.frauas.fb2.javaproject.ws25.group22.util;

import de.frauas.fb2.javaproject.ws25.group22.api.DroneType;
import de.frauas.fb2.javaproject.ws25.group22.errorhandling.IllegalJSONFormatException;
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
    /**
     * validates the drone type json object.
     * @param o the JSONObject of the drone type.
     * @return true if JSONObject is valid, false otherwise.
     * */
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
