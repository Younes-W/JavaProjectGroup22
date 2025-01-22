package Group22.API;

import java.util.ArrayList;
import java.util.List;



public class ThreadManager {
    private List<Thread>threads = new ArrayList<>();

    public ThreadManager() {}

    public void startDynamicsFetchingThread(Drone drone) {
        removeFinishedThreads();
        boolean threadAlreadyRunning = checkIfThreadForThisDroneIsAlreadyRunning(drone);
        if (!threadAlreadyRunning) {
            DynamicsFetcher dynamicsFetcher = new DynamicsFetcher(drone);
            Thread dynamicsThread = new Thread(dynamicsFetcher);
            dynamicsThread.setName("Fetcherthread" + drone.getId());
            threads.add(dynamicsThread);
            dynamicsThread.start();
        }
    }
    private boolean checkIfThreadForThisDroneIsAlreadyRunning(Drone drone) {
        for (Thread thread : threads) {
            if(thread.getName().equals("FetcherThread" + drone.getId())){
                return true;
            }
        }
        return false;
    }
    private void removeFinishedThreads() {
        threads.removeIf(thread -> !thread.isAlive());
    }
}
