package Group22.Util;

import Group22.API.Drone;
import org.json.JSONObject;

public class DroneParser implements Parser<Drone>{
    public Drone parse(JSONObject o){
        int id = o.getInt("id");
        String created = o.getString("created");
        String serialNumber = o.getString("serialnumber");
        String droneType = o.getString("dronetype");
        int droneTypeId = Util.cut_id(droneType);
        int carriageWeight = o.getInt("carriage_weight");
        String carriageType = o.getString("carriage_type");

        return new Drone(id,droneTypeId,created,serialNumber,carriageWeight,carriageType);
    }

}
