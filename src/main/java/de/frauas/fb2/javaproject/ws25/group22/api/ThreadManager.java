package de.frauas.fb2.javaproject.ws25.group22.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of drone fetching threads and prevents the starting of duplicate threads.
 * @author Tobias Ilcken, Younes Wimmer
 */

public class ThreadManager {
    private final List<Thread>threads = new ArrayList<>();

    public ThreadManager() {}

    /**
     * Starts the fetching thread if the thread is not already running
     * and if the dynamics were not fetched before.
     * Updates the thread list beforehand.
     * @param drone The drone for which the fetching thread should be started.
     */
    public void startDynamicsFetchingThread(Drone drone) {
        removeFinishedThreads();
        boolean threadAlreadyRunning = checkIfThreadForThisDroneIsAlreadyRunning(drone);
        if (!threadAlreadyRunning && !drone.getDynamicsFetched()) {
            DynamicsFetcher dynamicsFetcher = new DynamicsFetcher(drone);
            Thread dynamicsThread = new Thread(dynamicsFetcher);
            dynamicsThread.setName("Fetcherthread" + drone.getId());
            threads.add(dynamicsThread);
            dynamicsThread.start();
        }
    }

    /**
     * Checks whether a fetching thread for the drone is already running.
     * @param drone The drone for which the check should be done.
     * @return true if thread is currently running, false otherwise
     */
    private boolean checkIfThreadForThisDroneIsAlreadyRunning(Drone drone) {
        for (Thread thread : threads) {
            if(thread.getName().equals("Fetcherthread" + drone.getId())){
                return true;
            }
        }
        return false;
    }

    /**
     * removes threads that finished from the thread list.
     */
    private void removeFinishedThreads() {
        threads.removeIf(thread -> !thread.isAlive());
    }
}
