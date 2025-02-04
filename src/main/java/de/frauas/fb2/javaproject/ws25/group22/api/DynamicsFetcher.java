package de.frauas.fb2.javaproject.ws25.group22.api;

import de.frauas.fb2.javaproject.ws25.group22.errorhandling.ConnectionFailedException;
import de.frauas.fb2.javaproject.ws25.group22.errorhandling.IllegalJSONFormatException;
import de.frauas.fb2.javaproject.ws25.group22.util.DroneDynamicsParser;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.logging.Logger;

/**
 * A runnable that fetches all dynamics data for a specific drone in a continuous loop
 * until no new data is found or the thread is interrupted.
 *
 * @author Younes Wimmer, Tobias Ilcken
 */
public class DynamicsFetcher implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(DynamicsFetcher.class.getName());
    private final Drone drone;
    private final int dynamicsCount;

    /**
     * Constructs a DynamicsFetcher for a specific drone.
     *
     * @param drone the drone for which dynamics data will be fetched.
     */
    public DynamicsFetcher(Drone drone) {
        this.drone = drone;
        this.dynamicsCount = drone.getDynamicsCount();
    }

    /**
     * Continuously fetches new dynamics data until no more data is available.
     */
    @Override
    public void run() {
        LOGGER.info("Fetching all dynamics for drone " + drone.getId() + "...");
        String baseUrl = "http://dronesim.facets-labs.com/api/" + drone.getId() + "/dynamics/";
        int totalCount = fetchTotalDynamicsCount(baseUrl);
        String url = baseUrl + "?limit=" + totalCount + "&offset=" + dynamicsCount;
        DroneAPI droneAPI = new DroneAPI(url);
        try {
            JSONObject response = droneAPI.fetchJSON();
            JSONArray results = response.getJSONArray("results");
            DroneDynamicsParser droneDynamicsParser = new DroneDynamicsParser();
            for (int i = 0; i < results.length(); i++) {
                JSONObject dynamicsData = results.getJSONObject(i);
                DroneDynamics newDynamics = droneDynamicsParser.parse(dynamicsData);
                drone.getDroneDynamicsList().add(newDynamics);
            }
            LOGGER.info("Fetching completed for drone " + drone.getId());
            drone.setDynamicsFetched(true);
        } catch (ConnectionFailedException e) {
            LOGGER.severe("Connection failed. Make sure you are connected to the internet.");
        } catch (IllegalJSONFormatException e) {
            LOGGER.severe("Illegal JSON format. Failed to fetch dynamics.");
        }
    }

    /**
     * Fetches the total count of dynamics available for the drone from the specified base URL.
     *
     * @param baseUrl the base URL to fetch the count from.
     * @return the total count of available dynamics.
     */
    private int fetchTotalDynamicsCount(String baseUrl) {
        try {
            JSONObject response = new DroneAPI(baseUrl).fetchJSON();
            if (response.has("count")) {
                return response.getInt("count");
            } else {
                LOGGER.severe("Could not fetch the drone dynamics count.");
            }
        } catch (ConnectionFailedException e) {
            LOGGER.severe("Connection failed. Make sure you are connected to the internet.");
        } catch (IllegalJSONFormatException e) {
            LOGGER.severe("Illegal JSON format. Failed to fetch count.");
        }
        return 0;
    }
}
