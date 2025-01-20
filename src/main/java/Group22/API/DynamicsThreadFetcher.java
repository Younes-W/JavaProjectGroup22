package Group22.API;

import Group22.Errorhandling.Logging;
import Group22.Util.DroneDynamicsParser;
import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A thread that continuously fetches new dynamics data for a specific drone at regular intervals.
 * Extends {@link BaseThread} to utilize periodic sleeping and thread management.
 */
public class DynamicsThreadFetcher extends BaseThread {
    private final Drone drone;
    private int currentOffset;

    /**
     * Constructs a DynamicsThreadFetcher for a specific drone.
     *
     * @param milliSeconds the interval in milliseconds between fetch operations.
     * @param drone        the drone for which dynamics data will be fetched.
     */
    public DynamicsThreadFetcher(long milliSeconds, Drone drone) {
        super(milliSeconds);
        this.drone = drone;
        this.currentOffset = drone.getDynamicsCount();
    }

    /**
     * Continuously runs the fetch loop until interrupted or no new dynamics are available.
     */
    @Override
    public void run() {
        while (running && !Thread.currentThread().isInterrupted()) {
            fetchAndUpdateDynamics();
            sleepInterval();
        }
    }

    /**
     * Fetches new dynamics data for the associated drone and updates its dynamics list.
     */
    private void fetchAndUpdateDynamics() {
        String baseUrl = "http://dronesim.facets-labs.com/api/" + drone.getId() + "/dynamics/";
        int totalCount = fetchCount(baseUrl);
        String url = baseUrl + "?limit=" + totalCount + "&offset=" + currentOffset;

        DroneAPI droneAPI = new DroneAPI(url);
        JSONObject response = droneAPI.fetchJSON();
        if (response == null) {
            Logging.error("Error: No valid response");
            running = false;
            return;
        }

        JSONArray results = response.getJSONArray("results");
        if (results.isEmpty()) {
            Logging.info("No new dynamics found. Close Thread.");
            running = false;
            return;
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

    /**
     * Fetches the total count of dynamics available for the drone.
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

    /**
     * Stops the fetching process by setting the running flag to false and interrupting the thread.
     */
    public void stopFetching() {
        running = false;
        Thread.currentThread().interrupt();
    }
}
