package Group22.API;

public class InitializationThread extends BaseThread {
    private Dashboard dashboard;  // Feld zum Speichern des Dashboards

    public InitializationThread(long milliSeconds) {
        super(milliSeconds);
    }

    @Override
    public void run() {
        System.out.println("Initialisiere Drohnen, Types und Dynamics...");
        dashboard = new Dashboard();
        System.out.println("Initialisierung abgeschlossen.");
        running = false;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }
}
