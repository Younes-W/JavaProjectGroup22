package Group22.TestBackend;
import Group22.API.Dashboard;
import Group22.API.Drone;


public class Test {
    public static void main(String[] args) throws InterruptedException {
        Dashboard d1 = new Dashboard();

        Drone drone = d1.getDrones().get(131);
        System.out.println(drone.getDynamicsCount());
        d1.setSelectedDrone(drone);
        Thread.sleep(65000);
        System.out.println(drone.getDynamicsCount());
        d1.stopDynamicsThread();

    }
}

