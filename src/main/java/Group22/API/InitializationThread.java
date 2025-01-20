package Group22.API;

import Group22.Errorhandling.Logging;

/**
 * A thread responsible for initializing the dashboard, which in turn initializes drones,
 * drone types, and dynamics data.
 */
public class InitializationThread extends BaseThread {
    private Dashboard dashboard;

    /**
     * Constructs an InitializationThread with the specified sleep interval.
     * //TODO no usage rn
     * @param milliSeconds the interval in milliseconds between operations (not used here).
     */
    public InitializationThread(long milliSeconds) {
        super(milliSeconds);
    }

    /**
     * Runs the initialization process by creating a new Dashboard instance.
     * After initialization, sets the running flag to false.
     */
    @Override
    public void run() {
        Logging.info("Initialisiere Drohnen, Types und Dynamics...");
        dashboard = new Dashboard();
        Logging.info("Initialisierung abgeschlossen.");
        running = false;
    }

    /**
     * Retrieves the initialized Dashboard instance.
     *
     * @return the Dashboard that was initialized by this thread.
     */
    public Dashboard getDashboard() {
        return dashboard;
    }
}
