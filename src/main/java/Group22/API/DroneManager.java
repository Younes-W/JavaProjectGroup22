package Group22.API;

import Group22.Errorhandling.Logging;
import Group22.Util.DroneDynamicsParser;
import Group22.Util.DroneParser;
import Group22.Util.DroneTypeParser;
import Group22.Util.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the collection of drones and drone types, including initializing data and
 * fetching associated dynamics for each drone.
 */
public class DroneManager {
    private final HashMap<Integer, DroneType> droneTypes = new HashMap<>();
    private final HashMap<Integer, Drone> drones = new HashMap<>();

    /**
     * Constructs a DroneManager and initializes drones, drone types, and dynamics data.
     */
    public DroneManager() {
        initializeDrones();
        initializeDroneTypes();
        initializeDroneDynamics();
    }

    /**
     * Provides an unmodifiable map of drones.
     *
     * @return an unmodifiable view of the drones map.
     */
    public Map<Integer, Drone> getDrones() {
        return Map.copyOf(drones);
    }

    /**
     * Provides an unmodifiable map of drone types.
     *
     * @return an unmodifiable view of the droneTypes map.
     */
    public Map<Integer, DroneType> getDroneTypes() {
        return Map.copyOf(droneTypes);
    }

    /** @return the number of drones managed */
    public int getDroneCount() {
        return drones.size();
    }

    /** @return the number of drone types managed */
    public int getDroneTypeCount() {
        return droneTypes.size();
    }

    /**
     * Initializes the drone types by fetching data from an external API and parsing it.
     */
    private void initializeDroneTypes() {
        Logging.info("fetching DroneTypes");
        String url = "http://dronesim.facets-labs.com/api/dronetypes/?format=json";
        DroneTypeParser droneTypeParser = new DroneTypeParser();
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
                DroneType droneType = droneTypeParser.parse(o);
                Integer key = droneType.getId();
                droneTypes.put(key, droneType);
            }
        }
        Logging.info("fetched " + droneTypes.size() + " DroneTypes");
    }

    /**
     * Initializes the drones by fetching data from an external API and parsing it.
     */
    private void initializeDrones() {
        Logging.info("fetching Drones");
        String url = "http://dronesim.facets-labs.com/api/drones/?format=json";
        DroneParser droneParser = new DroneParser();
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
                Drone drone = droneParser.parse(o);
                int key = drone.getId();
                drones.put(key, drone);
            }
        }
        Logging.info("fetched " + drones.size() + " Drones");
    }

    /**
     * Initializes the dynamics data for each drone by fetching from an external API.
     * It fetches a fixed number of dynamics entries per drone and assigns them accordingly.
     */
    private void initializeDroneDynamics() {
        Logging.info("fetching initial DroneDynamics");
        String url = "http://dronesim.facets-labs.com/api/dronedynamics/?limit=" + getDrones().size() * 42 + "&offset=0";
        DroneDynamicsParser droneDynamicsParser = new DroneDynamicsParser();
        while (url != null) {
            DroneAPI droneAPI = new DroneAPI(url);
            JSONObject wholeFile = droneAPI.fetchJSON();

            JSONArray results = wholeFile.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject o = results.getJSONObject(i);
                DroneDynamics dynamic = droneDynamicsParser.parse(o);

                String droneUrl = o.getString("drone");
                int droneId = Util.cut_id(droneUrl);
                Drone drone = drones.get(droneId);
                if (drone != null) {
                    drone.getDroneDynamicsList().add(dynamic);
                    drone.calculateDistanceUpTo(i);
                    drone.calculateAverageSpeedUpTo(i);
                }
            }
            url = null;
        }
        Logging.info("fetched 42 dynamics per drone");
    }
}
