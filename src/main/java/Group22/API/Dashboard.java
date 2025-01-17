package Group22.API;

import Group22.Errorhandling.Logging;

import java.util.Map;

public class Dashboard {
    private DroneManager droneManager;
    private Drone selectedDrone = null;
    private DroneDynamics selectedDynamics = null;
    private int offset = 0;

    private Thread dynamicsThread;
    private DynamicsThreadFetcher dynamicsFetcher;



    public Dashboard() {
        droneManager = new DroneManager();
    }
    public Map<Integer,Drone> getDrones(){
        return droneManager.getDrones();
    }
    public Map<Integer,DroneType> getDroneTypes(){
        return droneManager.getDroneTypes();
    }
    public Drone getDrone(int id) {
        return droneManager.getDrones().get(id);
    }
    public DroneType getDroneType(int id) {
        return droneManager.getDroneTypes().get(id);
    }
    public DroneType getDroneType(Drone drone) {
        return droneManager.getDroneTypes().get(drone.getDroneTypeId());
    }

    public Drone getSelectedDrone() {
        return selectedDrone;
    }

    public void setSelectedDrone(Drone selectedDrone) {
        System.out.println("Thread of selected Drone is running");
        Logging.info("fetching dynamics of Drone " + selectedDrone.getID());
        this.selectedDrone = selectedDrone;
        stopDynamicsThread();

        dynamicsFetcher = new DynamicsThreadFetcher(0,selectedDrone);
        dynamicsThread = new Thread(dynamicsFetcher);
        dynamicsThread.setDaemon(true);
        dynamicsThread.start();

        this.selectedDrone.setDynamicsFetched(true);
        Logging.info("fetched dynamics of Drone " + selectedDrone.getID());
    }
    public void stopDynamicsThread() {
        if (dynamicsFetcher!= null) {
            dynamicsFetcher.stopFetching();
        }
        if(dynamicsThread != null && dynamicsThread.isAlive()) {
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

    public void updateSelectedDynamics(int change) {
        offset = offset + change;
        if(offset < 0){
            offset = 0;
        }else if(offset >= selectedDrone.getDynamicsCount()){
            offset = selectedDrone.getDynamicsCount() - 1;
        }
        selectedDynamics = selectedDrone.getDynamics(offset);
    }

    public DroneDynamics getSelectedDynamics() {
        return selectedDynamics;
    }
    public int getOffset() {
        return offset;
    }
    //TODO methode für API Refresh.(Müsste dann einfach neues DroneManagerobjekt sein
    // und SelectedDrone und SelectedDynamic auf null setzen und offset = 0 setzen)
    public void apiRefresh() {
        droneManager = new DroneManager();
        stopDynamicsThread();
        offset = 0;
        this.selectedDrone = null;
        this.selectedDynamics = null;
        System.out.println("API Refresh in Dashboard durchgeführt..");
    }
    public void uiRefresh() {
        stopDynamicsThread();
        offset = 0;
        this.selectedDrone = null;
        this.selectedDynamics = null;
    }


}
