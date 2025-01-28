package de.frauas.fb2.javaproject.ws25.group22.util;

import de.frauas.fb2.javaproject.ws25.group22.api.DroneDynamics;
import de.frauas.fb2.javaproject.ws25.group22.errorhandling.IllegalJSONFormatException;
import org.json.JSONObject;

/**
 * Parser for converting JSON objects into {@link DroneDynamics} instances.
 */
public class DroneDynamicsParser extends BaseParser<DroneDynamics> {
    /**
     * Parses a JSONObject into a DroneDynamics instance.
     *
     * @param o the JSONObject representing drone dynamics data.
     * @return a new DroneDynamics object.
     */
    @Override
    public DroneDynamics parse(JSONObject o) throws IllegalJSONFormatException {
        if(validate(o)){
            String timestamp = o.getString("timestamp");
            String lastSeen = o.getString("last_seen");
            String status = o.optString("status","None");
            double alignRoll = o.optDouble("align_roll",0);
            double alignPitch = o.optDouble("align_pitch",0);
            double alignYaw = o.optDouble("align_yaw",0);
            double latitude = o.getDouble("latitude");
            double longitude = o.getDouble("longitude");
            int batteryStatus = o.getInt("battery_status");
            int speed = o.getInt("speed");
            return new DroneDynamics(timestamp, speed, alignRoll, alignPitch, alignYaw,
                    longitude, latitude, batteryStatus, lastSeen, status);
        }else{
            throw new IllegalJSONFormatException();
        }
    }

    /**
     * validates the Drone dynamics json object.
     * @param o the JSONObject of the drone dynamics.
     * @return true if JSONObject is valid, false otherwise.
     * */
    private boolean validate(JSONObject o) {
        String[] attributes = {"drone","timestamp", "speed", "longitude", "latitude","battery_status","last_seen"};
        for (String attribute : attributes) {
            if (!o.has(attribute)) {
                return false;
            }
        }
        return true;
    }
}
