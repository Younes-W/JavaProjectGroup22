package Group22.Util;

import Group22.API.Drone;
import org.json.JSONObject;

/**
 * Parser for converting JSON objects into {@link Drone} instances.
 */
public class DroneParser extends BaseParser<Drone> {
    /**
     * Parses a JSONObject into a Drone instance.
     *
     * @param o the JSONObject representing a drone.
     * @return a new Drone object.
     */
    @Override
    public Drone parse(JSONObject o) {
        int id = o.getInt("id");
        String created = o.getString("created");
        String serialNumber = o.getString("serialnumber");
        String droneType = o.getString("dronetype");
        int droneTypeId = Util.cut_id(droneType);
        int carriageWeight = o.getInt("carriage_weight");
        String carriageType = o.getString("carriage_type");

        return new Drone(id, droneTypeId, created, serialNumber, carriageWeight, carriageType);
    }
}
