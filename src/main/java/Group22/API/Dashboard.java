package Group22.API;

import Group22.Errorhandling.Logging;
import java.util.Map;

/**
 * The Dashboard class manages a collection of drones, handling selection, dynamics fetching,
 * and refresh operations for both API and UI contexts.
 */
public class Dashboard {
    private DroneManager droneManager;
    private Drone selectedDrone = null;
    private DroneDynamics selectedDynamics = null;
    private int offset = 0;
    private Thread dynamicsThread;
    private DynamicsThreadFetcher dynamicsFetcher;

    /**
     * Constructs a new Dashboard instance and initializes the DroneManager.
     */
    public Dashboard() {
        droneManager = new DroneManager();
    }

    /**
     * Retrieves a map of all drones managed by the DroneManager.
     *
     * @return a map with drone IDs as keys and Drone objects as values.
     */
    public Map<Integer,Drone> getDrones(){
        return droneManager.getDrones();
    }

    /**
     * Retrieves a map of all drone types managed by the DroneManager.
     *
     * @return a map with drone type IDs as keys and DroneType objects as values.
     */
    public Map<Integer,DroneType> getDroneTypes(){
        return droneManager.getDroneTypes();
    }

    /**
     * Retrieves a specific Drone by its ID.
     *
     * @param id the ID of the drone.
     * @return the Drone object corresponding to the given ID, or null if not found.
     */
    public Drone getDrone(int id) {
        return droneManager.getDrones().get(id);
    }

    /**
     * Retrieves a specific DroneType by its ID.
     *
     * @param id the ID of the drone type.
     * @return the DroneType object corresponding to the given ID, or null if not found.
     */
    public DroneType getDroneType(int id) {
        return droneManager.getDroneTypes().get(id);
    }

    /**
     * Retrieves the DroneType for a given Drone.
     *
     * @param drone the drone whose type is to be retrieved.
     * @return the DroneType of the provided drone, or null if not found.
     */
    public DroneType getDroneType(Drone drone) {
        return droneManager.getDroneTypes().get(drone.getDroneTypeId());
    }

    /**
     * Gets the currently selected drone.
     *
     * @return the currently selected Drone, or null if none is selected.
     */
    public Drone getSelectedDrone() {
        return selectedDrone;
    }

    /**
     * Sets the selected drone and starts fetching its dynamics in a separate thread.
     * If null is passed, it stops any ongoing dynamics fetching and resets the selection.
     *
     * @param selectedDrone the Drone to select or null to reset selection.
     */
    public void setSelectedDrone(Drone selectedDrone) {
        if (selectedDrone == null) {
            // If null is passed, stop thread and reset selectedDrone.
            this.selectedDrone = null;
            return;
        }

        Logging.info("Thread of selected Drone is running");
        Logging.info("fetching dynamics of Drone " + selectedDrone.getId());
        this.selectedDrone = selectedDrone;
        stopDynamicsThread();
        dynamicsFetcher = new DynamicsThreadFetcher(0, selectedDrone);
        dynamicsThread = new Thread(dynamicsFetcher);
        dynamicsThread.setDaemon(true);
        dynamicsThread.start();

        this.selectedDrone.setDynamicsFetched(true);
        Logging.info("fetched dynamics of Drone " + selectedDrone.getId());
    }

    /**
     * Stops the dynamics fetching thread if it is running.
     * Ensures threads are properly interrupted and joined.
     */
    public void stopDynamicsThread() {
        if (dynamicsFetcher != null) {
            dynamicsFetcher.stopFetching();
        }
        if (dynamicsThread != null && dynamicsThread.isAlive()) {
            dynamicsThread.interrupt();
            try {
                dynamicsThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        dynamicsThread = null;
        dynamicsFetcher = null;
    }

    /**
     * Updates the selected dynamics by adjusting the offset based on the provided change.
     * Ensures the offset remains within valid bounds and updates the selectedDynamics accordingly.
     *
     * @param change the amount to change the current offset by.
     */
    public void updateSelectedDynamics(int change) {
        offset = offset + change;
        if (offset < 0) {
            offset = 0;
        } else if (offset >= selectedDrone.getDynamicsCount()) {
            offset = selectedDrone.getDynamicsCount() - 1;
        }
        selectedDynamics = selectedDrone.getDynamics(offset);
    }

    /**
     * Retrieves the currently selected dynamics data.
     *
     * @return the currently selected DroneDynamics, or null if none selected.
     */
    public DroneDynamics getSelectedDynamics() {
        return selectedDynamics;
    }

    /**
     * Retrieves the current offset used for selecting dynamics.
     *
     * @return the current offset.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Refreshes the API by reinitializing the DroneManager, stopping dynamics thread,
     * and resetting selection and offset.
     */
    public void apiRefresh() {
        droneManager = new DroneManager();
        stopDynamicsThread();
        offset = 0;
        this.selectedDrone = null;
        this.selectedDynamics = null;
        Logging.info("API Refresh in Dashboard ...");
    }

    /**
     * Refreshes the UI by stopping dynamics thread and resetting selection and offset
     * without reinitializing the DroneManager.
     */
    public void uiRefresh() {
        stopDynamicsThread();
        offset = 0;
        this.selectedDrone = null;
        this.selectedDynamics = null;
    }
}
