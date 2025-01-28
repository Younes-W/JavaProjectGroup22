package de.frauas.fb2.javaproject.ws25.group22.api;

import de.frauas.fb2.javaproject.ws25.group22.errorhandling.ConnectionFailedException;
import de.frauas.fb2.javaproject.ws25.group22.errorhandling.IllegalJSONFormatException;
import de.frauas.fb2.javaproject.ws25.group22.util.DroneDynamicsParser;
import de.frauas.fb2.javaproject.ws25.group22.util.DroneParser;
import de.frauas.fb2.javaproject.ws25.group22.util.DroneTypeParser;
import de.frauas.fb2.javaproject.ws25.group22.util.Util;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Manages a collection of available drones and drone types and initializes them.
 * @author Younes Wimmer, Tobias Ilcken, Parnia Esfahani
 */

public class DroneCollection {
    private static final Logger LOGGER = Logger.getLogger(DroneCollection.class.getName());
    private final HashMap<Integer, DroneType> droneTypes = new HashMap<>();
    private final HashMap<Integer, Drone> drones = new HashMap<>();

    /**
     * Constructs a DroneCollection and initializes drones, drone types, and dynamics data.
     */
    public DroneCollection() {
        initializeDrones();
        initializeDroneTypes();
        initializeDroneDynamics();
    }

    /**
     * Provides an unmodifiable map of drones.
     * @return an unmodifiable view of the drones map.
     */
    public Map<Integer, Drone> getDrones() {
        return Map.copyOf(drones);
    }

    /**
     * Provides an unmodifiable map of drone types.
     * @return an unmodifiable view of the droneTypes map.
     */
    public Map<Integer, DroneType> getDroneTypes() {
        return Map.copyOf(droneTypes);
    }

    /**
     * Initializes the drone types by fetching data from the drone API and parsing it.
     */
    private void initializeDroneTypes() {
        LOGGER.info("fetching DroneTypes");
        String url = "http://dronesim.facets-labs.com/api/dronetypes/?format=json";
        DroneTypeParser droneTypeParser = new DroneTypeParser();
        try{
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
            LOGGER.info("successfully fetched " + droneTypes.size() + " DroneTypes");
        }catch(ConnectionFailedException e){
            LOGGER.severe("Connection failed. Make sure you are connected to the internet.");
        }catch(IllegalJSONFormatException e){
            LOGGER.severe("Illegal JSON format. Could not fetch drone types.");
        }

    }

    /**
     * Initializes the drones by fetching data from the drone API and parsing it.
     */
    private void initializeDrones() {
        LOGGER.info("fetching Drones");
        String url = "http://dronesim.facets-labs.com/api/drones/?format=json";
        DroneParser droneParser = new DroneParser();
        try{
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
            LOGGER.info("successfully fetched " + drones.size() + " Drones");
        }catch(ConnectionFailedException e){
            LOGGER.severe("Connection failed. Make sure you are connected to the internet.");
        }catch(IllegalJSONFormatException e){
            LOGGER.severe("Illegal JSON format. Could not fetch drones.");
        }
    }

    /**
     * Initializes the dynamics data for each drone by fetching them from the drone API.
     * It fetches a fixed number of dynamics per drone and assigns them accordingly.
     */
    private void initializeDroneDynamics() {
        int initialDynamics = 42;
        LOGGER.info("fetching initial DroneDynamics");
        String url = "http://dronesim.facets-labs.com/api/dronedynamics/?limit=" + getDrones().size() * initialDynamics + "&offset=0";
        DroneDynamicsParser droneDynamicsParser = new DroneDynamicsParser();
        DroneAPI droneAPI = new DroneAPI(url);
        try {
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
                }
            }
            LOGGER.info("successfully fetched 42 dynamics per drone");
        }catch(ConnectionFailedException e){
            LOGGER.severe("Connection failed. Make sure you are connected to the internet.");
        }catch(IllegalJSONFormatException e){
            LOGGER.severe("Illegal JSON format. Could not fetch drone dynamics.");
        }
    }
}
