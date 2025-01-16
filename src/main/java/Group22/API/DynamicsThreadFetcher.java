package Group22.API;

import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONObject;

public class DynamicsThreadFetcher extends BaseThread {
    private final Drone drone;
    private int currentOffset;

    public DynamicsThreadFetcher(long milliSeconds,Drone drone ) {
        super(milliSeconds);
        this.drone = drone;
        this.currentOffset = drone.getDynamicsCount();
    }

    @Override
    public void run() {
        while (running) {
            fetchAndUpdateDynamics();
            sleepInterval();
        }
    }

    private void fetchAndUpdateDynamics() {
        String url = "http://dronesim.facets-labs.com/api/" + drone.getID() + "/dynamics/?limit=500&offset=" + currentOffset;

        DroneAPI droneAPI = new DroneAPI(url);
        JSONObject response = droneAPI.fetchJSON();
        JSONArray results = response.getJSONArray("results");
        int loadedCount = 0;

        for (int i = 0; i < results.length(); i++) {
            JSONObject o = results.getJSONObject(i);
            DroneDynamics newDynamics = DroneDynamics.parseDroneDynamics(o);
//            Platform.runLater(() -> {
//                drone.getDroneDynamicsList().add(newDynamics);
//                // Eventuell GUI-Komponenten aktualisieren, falls nötig.
//            });
            drone.getDroneDynamicsList().add(newDynamics);
            currentOffset++;
            loadedCount++;

        }
        if (loadedCount >0) {
            System.out.println("Neues Update: " + loadedCount
                    + " neue Dynamics geladen um "
                    + java.time.LocalTime.now());
        }
    }
    public void stopFetching() {
        running = false;
    }




}