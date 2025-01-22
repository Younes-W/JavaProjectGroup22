package Group22.API;

import Group22.Errorhandling.ConnectionFailedException;
import Group22.Errorhandling.IllegalJSONFormatException;
import Group22.Errorhandling.Logging;
import Group22.Util.DroneDynamicsParser;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A runnable that fetches all dynamics data for a specific drone in a continuous loop
 * until no new data is found or the thread is interrupted.
 */
public class DynamicsFetcher implements Runnable {
    private final Drone drone;
    private final int dynamicsCount;

    /**
     * Constructs a DynamicsThreadFetcher for a specific drone.
     *
     * @param drone the drone for which dynamics data will be fetched.
     */
    public DynamicsFetcher(Drone drone) {
        this.drone = drone;
        this.dynamicsCount = drone.getDynamicsCount();
    }

    /**
     * Continuously fetches new dynamics data until no more data is available
     * or the thread is interrupted.
     */
    @Override
    public void run() {
        Logging.info("Fetching all dynamics for drone " + drone.getId() + "...");
        String baseUrl = "http://dronesim.facets-labs.com/api/" + drone.getId() + "/dynamics/";
        int totalCount = fetchCount(baseUrl);
        if(drone.getDynamicsFetched() == true) {
            Logging.info("Dynamics for drone" + drone.getId() +  "are already fetched.");
        }else {
            String url = baseUrl + "?limit=" + totalCount + "&offset=" + dynamicsCount;
            DroneAPI droneAPI = new DroneAPI(url);
            try {
                JSONObject response = droneAPI.fetchJSON();
                    JSONArray results = response.getJSONArray("results");
                    DroneDynamicsParser droneDynamicsParser = new DroneDynamicsParser();
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject o = results.getJSONObject(i);
                        DroneDynamics newDynamics = droneDynamicsParser.parse(o);
                        drone.getDroneDynamicsList().add(newDynamics);
                    }
                    Logging.info("Fetching completed for drone " + drone.getId());
                    drone.setDynamicsFetched(true);
            } catch (ConnectionFailedException e) {
                Logging.error("Connection failed. Make sure you are connected to the internet.");
            } catch(IllegalJSONFormatException e) {
                Logging.error("Illegal JSON format. Failed to fetch dynamics.");
            }
        }
        }

    /**
     * Fetches the total count of dynamics available for the drone from the specified base URL.
     *
     * @param baseUrl the base URL to fetch the count from.
     * @return the total count of available dynamics.
     */
    private int fetchCount(String baseUrl) {
        try {
            JSONObject response = new DroneAPI(baseUrl).fetchJSON();
            if (response.has("count")) {
                return response.getInt("count");
            } else {
                Logging.error("Could not fetch the droneDynamics count");
            }
        }catch(ConnectionFailedException e){
            Logging.error("Connection failed. Make sure you are connected to the internet.");
        }catch(IllegalJSONFormatException e){
            Logging.error("Illegal JSON format. Failed to fetch count.");
        }
        return 0;
    }
}
