package Group22.TestBackend;
import Group22.API.Dashboard;
import Group22.API.Drone;
import Group22.API.InitializationThread;


public class Test {
    public static void main(String[] args) throws InterruptedException {
        InitializationThread initThreadTask = new InitializationThread(0);
        Thread initThread = new Thread(initThreadTask);
        initThread.setDaemon(true);
        initThread.start();
        initThread.join();

        Dashboard d1 = initThreadTask.getDashboard();
        System.out.println("Dashboard initialisiert und bereit zur Nutzung.");
       Drone drone = d1.getDrones().get(131);
       System.out.println(drone.getDynamicsCount());
       System.out.println(drone.getTotalDistanceTravelled());
       System.out.println(drone.getSpeedOvertime());

       d1.setSelectedDrone(drone);
       Thread.sleep(5000);
        System.out.println(drone.getDynamicsCount());
        System.out.println(drone.getTotalDistanceTravelled());
        System.out.println(drone.getSpeedOvertime());
        System.out.println(drone.getDynamics(5760).getSpeed());
        d1.stopDynamicsThread();

    }
}

