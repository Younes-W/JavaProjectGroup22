package de.frauas.fb2.javaproject.ws25.group22.api;

import java.util.Map;
import java.util.logging.Logger;

/**
 * The Dashboard class is the intersection of the backend and the GUI.
 * It can be used to get all relevant information for each drone, drone type and drone dynamic.
 *
 * @author Younes Wimmer, Tobias Ilcken, Parnia Esfahani
 */
public class Dashboard {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private DroneCollection droneCollection;
    private ThreadManager threadManager;
    private Drone selectedDrone = null;
    private DroneDynamics selectedDynamics = null;
    private int offset = 0;

    /**
     * Constructs a new Dashboard instance and initializes the DroneCollection as well as the thread manager.
     *
     * @see ThreadManager
     * @see DroneCollection
     */
    public Dashboard() {
        droneCollection = new DroneCollection();
        threadManager = new ThreadManager();
    }

    /**
     * Retrieves a map of all drones managed by the DroneCollection.
     *
     * @return a map of all drones with drone ids as the keys.
     */
    public Map<Integer, Drone> getDrones() {
        return droneCollection.getDrones();
    }

    /**
     * Retrieves a map of all drone types managed by the DroneCollection.
     *
     * @return a map with drone type IDs as keys and DroneType objects as values.
     */
    public Map<Integer, DroneType> getDroneTypes() {
        return droneCollection.getDroneTypes();
    }

    /**
     * Retrieves a specific Drone by its ID.
     *
     * @param id the ID of the drone.
     * @return the Drone object corresponding to the given ID, or null if not found.
     */
    public Drone getDrone(int id) {
        return droneCollection.getDrones().get(id);
    }

    /**
     * Retrieves a specific DroneType by its ID.
     *
     * @param id the ID of the drone type.
     * @return the DroneType object corresponding to the given ID, or null if not found.
     */
    public DroneType getDroneType(int id) {
        return droneCollection.getDroneTypes().get(id);
    }

    /**
     * Retrieves the DroneType for a given Drone.
     *
     * @param drone the drone whose type is to be retrieved.
     * @return the DroneType of the provided drone, or null if not found.
     */
    public DroneType getDroneType(Drone drone) {
        return droneCollection.getDroneTypes().get(drone.getDroneTypeId());
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
     * Retrieves the current offset used for selecting dynamics.
     *
     * @return the current offset.
     */
    public int getOffset() {
        return offset;
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
     * Sets the selected drone and starts fetching the dynamics.
     *
     * @param selectedDrone the Drone to select or null to reset selection.
     */
    public void setSelectedDrone(Drone selectedDrone) {
        dashboardRefresh();
        if (selectedDrone == null) {
            this.selectedDrone = null;
            return;
        }
        this.selectedDrone = selectedDrone;
        threadManager.startDynamicsFetchingThread(selectedDrone);
    }

    /**
     * Updates the selected dynamics by adjusting the offset based on the provided change.
     * Ensures the offset remains within valid bounds and updates the selectedDynamics accordingly.
     *
     * @param change the amount to change the current offset by.
     */
    public void updateSelectedDynamics(int change) {
        offset += change;
        if (offset < 0) {
            offset = 0;
        } else if (selectedDrone != null && offset >= selectedDrone.getDynamicsCount()) {
            offset = selectedDrone.getDynamicsCount() - 1;
        }
        if (selectedDrone != null) {
            selectedDynamics = selectedDrone.getDynamics(offset);
        }
    }

    /**
     * Refreshes the API by reinitializing the DroneManager,
     * resetting offsets and selections.
     */
    public void apiRefresh() {
        dashboardRefresh();
        droneCollection = new DroneCollection();
        threadManager = new ThreadManager();
        LOGGER.info("Refreshing API...");
    }

    //Resets the selected drone, dynamics and dynamics offset.
    private void dashboardRefresh() {
        offset = 0;
        this.selectedDrone = null;
        this.selectedDynamics = null;
    }
}
