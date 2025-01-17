package Group22.API;

import Group22.Util.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DroneManager {
    private final HashMap<Integer,DroneType> droneTypes = new HashMap<>();
    private final HashMap<Integer,Drone> drones = new HashMap<>();

    public DroneManager(){
        initializeDrones();
        initializeDroneTypes();
        initializeDroneDynamics();
    }
    public Map<Integer, Drone> getDrones(){
        return Map.copyOf(drones);
    }
    public Map<Integer, DroneType> getDroneTypes(){
        return Map.copyOf(droneTypes);
    }
    public int getDroneCount(){
        return drones.size();
    }
    public int getDroneTypeCount(){
        return droneTypes.size();
    }

    private void initializeDroneTypes() {
        String url = "http://dronesim.facets-labs.com/api/dronetypes/?format=json";
        while (url != null) {
            DroneAPI droneAPI = new DroneAPI(url);
            JSONObject wholeFile = droneAPI.fetchJSON();
            if (!wholeFile.isNull("next")) {
                url = wholeFile.getString("next");
            } else {
                url = null;
            }
            JSONArray jsonFile = wholeFile.getJSONArray("results");
            for (int i = 0; i < jsonFile.length(); i++) {
                JSONObject o = jsonFile.getJSONObject(i);
                DroneType droneType = DroneType.parseDroneTypes(o);
                Integer key = droneType.getID();
                droneTypes.put(key, droneType);
            }
        }
    }
    private void initializeDrones() {
        String url = "http://dronesim.facets-labs.com/api/drones/?format=json";
        while (url != null) {
            DroneAPI droneAPI = new DroneAPI(url);
            JSONObject wholeFile = droneAPI.fetchJSON();
            if (!wholeFile.isNull("next")) {
                url = wholeFile.getString("next");
            } else {
                url = null;
            }
            JSONArray jsonFile = wholeFile.getJSONArray("results");
            for (int i = 0; i < jsonFile.length(); i++) {
                JSONObject o = jsonFile.getJSONObject(i);
                Drone drone = Drone.parseDrone(o);
                int key = drone.getID();
                drones.put(key,drone);
            }
        }
    }
    private void initializeDroneDynamics() {
        String url = "http://dronesim.facets-labs.com/api/dronedynamics/?limit=" + getDrones().size()*42  +"&offset=0";
        while (url != null) {
            DroneAPI droneAPI = new DroneAPI(url);
            JSONObject wholeFile = droneAPI.fetchJSON();

            JSONArray results = wholeFile.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject o = results.getJSONObject(i);
                DroneDynamics dynamic = DroneDynamics.parseDroneDynamics(o);

                String droneUrl = o.getString("drone");
                int droneId = Util.cut_id(droneUrl);
                Drone drone = drones.get(droneId);
                drone.getDroneDynamicsList().add(dynamic);
                drone.calculateTotalDistance(drone.getDynamicsCount() - 1);
                drone.calculateAverageSpeedDistanceTime();
            }
            url = null;
        }
    }



}
