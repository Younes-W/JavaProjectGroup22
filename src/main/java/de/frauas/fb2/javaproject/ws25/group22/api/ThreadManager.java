package de.frauas.fb2.javaproject.ws25.group22.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Manages a list of drone fetching threads and prevents the starting of duplicate threads.
 *
 * @author Tobias Ilcken
 */
public class ThreadManager {
    private static final Logger LOGGER = Logger.getLogger(ThreadManager.class.getName());
    private final List<Thread> threads = new ArrayList<>();

    /**
     * Constructs a ThreadManager.
     */
    public ThreadManager() {}

    /**
     * Starts the fetching thread if the thread is not already running
     * and if the dynamics were not fetched before.
     * Updates the thread list beforehand.
     *
     * @param drone The drone for which the fetching thread should be started.
     */
    public void startDynamicsFetchingThread(Drone drone) {
        removeFinishedThreads();
        boolean threadAlreadyRunning = isThreadForDroneRunning(drone);
        if (!threadAlreadyRunning && !drone.isDynamicsFetched()) {
            DynamicsFetcher dynamicsFetcher = new DynamicsFetcher(drone);
            Thread dynamicsThread = new Thread(dynamicsFetcher);
            dynamicsThread.setName("FetcherThread" + drone.getId());
            threads.add(dynamicsThread);
            dynamicsThread.start();
            LOGGER.info("Started fetching thread for drone " + drone.getId());
        } else {
            LOGGER.info("Fetching thread already running or dynamics already fetched for drone " + drone.getId());
        }
    }

    /**
     * Checks whether a fetching thread for the drone is already running.
     *
     * @param drone The drone for which the check should be done.
     * @return true if a thread is currently running for the drone, false otherwise.
     */
    private boolean isThreadForDroneRunning(Drone drone) {
        String expectedThreadName = "FetcherThread" + drone.getId();
        for (Thread thread : threads) {
            if (thread.getName().equals(expectedThreadName) && thread.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes threads that have finished from the thread list.
     */
    private void removeFinishedThreads() {
        threads.removeIf(thread -> !thread.isAlive());
    }
}
