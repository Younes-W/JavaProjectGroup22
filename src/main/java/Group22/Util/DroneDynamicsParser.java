package Group22.Util;

import Group22.API.DroneDynamics;
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
    public DroneDynamics parse(JSONObject o) {
        String timestamp = o.getString("timestamp");
        String lastSeen = o.getString("last_seen");
        String status = o.getString("status");
        double alignRoll = o.getDouble("align_roll");
        double alignPitch = o.getDouble("align_pitch");
        double alignYaw = o.getDouble("align_yaw");
        double latitude = o.getDouble("latitude");
        double longitude = o.getDouble("longitude");
        int batteryStatus = o.getInt("battery_status");
        int speed = o.getInt("speed");

        return new DroneDynamics(timestamp, speed, alignRoll, alignPitch, alignYaw,
                longitude, latitude, batteryStatus, lastSeen, status);
    }
}
