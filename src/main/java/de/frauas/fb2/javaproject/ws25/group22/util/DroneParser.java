package de.frauas.fb2.javaproject.ws25.group22.util;

import de.frauas.fb2.javaproject.ws25.group22.api.Drone;
import de.frauas.fb2.javaproject.ws25.group22.errorhandling.IllegalJSONFormatException;
import org.json.JSONObject;

/**
 * Parser for converting JSON objects into {@link Drone} instances.
 *
 * @author Younes Wimmer, Tobias Ilcker, Parnia Esfahani
 */
public class DroneParser extends BaseParser<Drone> {
    /**
     * Default constructor for DroneParser.
     */
    public DroneParser() {
        super();
    }

    /**
     * Parses a JSONObject into a Drone instance.
     *
     * @param o the JSONObject representing drone data.
     * @return a new Drone object.
     * @throws IllegalJSONFormatException if the JSON format is invalid.
     */
    @Override
    public Drone parse(JSONObject o) throws IllegalJSONFormatException {
        if (validate(o)) {
            int id = o.getInt("id");
            String created = o.optString("created", "");
            String serialNumber = o.getString("serialnumber");
            String droneType = o.getString("dronetype");
            int droneTypeId = Util.cut_id(droneType);
            int carriageWeight = o.optInt("carriage_weight", 0);
            String carriageType = o.optString("carriage_type", "Not");
            return new Drone(id, droneTypeId, created, serialNumber, carriageWeight, carriageType);
        } else {
            throw new IllegalJSONFormatException("Invalid JSON format for Drone.");
        }
    }

    /**
     * Validates the drone JSON object.
     *
     * @param o the JSONObject of the drone.
     * @return true if JSONObject is valid, false otherwise.
     */
    private boolean validate(JSONObject o) {
        String[] attributes = {"id", "dronetype", "serialnumber"};
        for (String attribute : attributes) {
            if (!o.has(attribute)) {
                return false;
            }
        }
        return true;
    }
}
