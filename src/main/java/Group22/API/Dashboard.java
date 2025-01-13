package Group22.API;

import java.util.Map;

public class Dashboard {
    private DroneManager droneManager;
    private Drone selectedDrone = null;
    private DroneDynamics selectedDynamics = null;
    private int offset = 0;

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
    public void setSelectedDrone(Drone selectedDrone) {
        this.selectedDrone = selectedDrone;
        //TODO hier muss dann der thread gestartet werden der die dynamics lädt probably
        this.selectedDrone.setDynamicsFetched(true);
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

    //TODO methode für API Refresh.(Müsste dann einfach neues DroneManagerobjekt sein
    // und SelectedDrone und SelectedDynamic auf null setzen und offset = 0 setzen)

}
