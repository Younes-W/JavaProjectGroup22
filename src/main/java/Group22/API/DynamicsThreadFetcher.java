package Group22.API;

import Group22.Errorhandling.Logging;
import Group22.Util.DroneDynamicsParser;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A runnable that fetches all dynamics data for a specific drone in a continuous loop
 * until no new data is found or the thread is interrupted.
 */
public class DynamicsThreadFetcher implements Runnable {
    private final Drone drone;
    private int currentOffset;

    /**
     * Constructs a DynamicsThreadFetcher for a specific drone.
     *
     * @param drone the drone for which dynamics data will be fetched.
     */
    public DynamicsThreadFetcher(Drone drone) {
        this.drone = drone;
        this.currentOffset = drone.getDynamicsCount();
    }

    /**
     * Continuously fetches new dynamics data until no more data is available
     * or the thread is interrupted.
     */
    @Override
    public void run() {
        Logging.info("Fetching all dynamics for drone " + drone.getId() + "...");
        while (!Thread.currentThread().isInterrupted()) {
            String baseUrl = "http://dronesim.facets-labs.com/api/" + drone.getId() + "/dynamics/";
            int totalCount = fetchCount(baseUrl);
            String url = baseUrl + "?limit=" + totalCount + "&offset=" + currentOffset;

            DroneAPI droneAPI = new DroneAPI(url);
            JSONObject response = droneAPI.fetchJSON();
            if (response == null) {
                Logging.error("Error: No valid response");
                break;
            }

            JSONArray results = response.getJSONArray("results");
            if (results.isEmpty()) {
                Logging.info("No more new dynamics found.");
                break;
            }

            DroneDynamicsParser droneDynamicsParser = new DroneDynamicsParser();
            for (int i = 0; i < results.length(); i++) {
                JSONObject o = results.getJSONObject(i);
                DroneDynamics newDynamics = droneDynamicsParser.parse(o);
                drone.getDroneDynamicsList().add(newDynamics);
                drone.calculateDistanceUpTo(currentOffset);
                drone.calculateAverageSpeedUpTo(currentOffset);
                currentOffset++;
            }
        }
        Logging.info("Fetching completed for drone " + drone.getId());
    }

    /**
     * Fetches the total count of dynamics available for the drone from the specified base URL.
     *
     * @param baseUrl the base URL to fetch the count from.
     * @return the total count of available dynamics.
     */
    private int fetchCount(String baseUrl) {
        JSONObject response = new DroneAPI(baseUrl).fetchJSON();
        if (response != null) {
            return response.getInt("count");
        } else {
            Logging.error("Error: no valid JSON response");
            return 0;
        }
    }
}
