package Group22.Util;

import Group22.API.Drone;
import Group22.Errorhandling.IllegalJSONFormatException;
import org.json.JSONObject;

/**
 * Parser for converting JSON objects into {@link Drone} instances.
 */
public class DroneParser extends BaseParser<Drone> {
    /**
     * Parses a JSONObject into a Drone instance.
     *
     *
     */
    public DroneParser() {}
    @Override
    public Drone parse(JSONObject o) throws IllegalJSONFormatException {
       if(validate(o)){
           int id = o.getInt("id");
           String created = o.optString("created", "");
           String serialNumber = o.getString("serialnumber");
           String droneType = o.getString("dronetype");
           int droneTypeId = Util.cut_id(droneType);
           int carriageWeight = o.optInt("carriage_weight",0);
           String carriageType = o.optString("carriage_type","Not");
           return new Drone(id, droneTypeId, created, serialNumber, carriageWeight, carriageType);
       }else{
           throw new IllegalJSONFormatException();
       }
    }

    private boolean validate(JSONObject o) {
        String[] attributes = {"id","dronetype","serialnumber"};
        for(String attribute : attributes){
            if(!o.has(attribute)){
                return false;
            }
        }
        return true;
    }
}
