package Group22.API;

/**
 * Abstract base class for creating threads that perform periodic tasks.
 * Subclasses must implement the {@link #run()} method to define specific behavior.
 */
public abstract class BaseThread implements Runnable {
    protected volatile boolean running = true;
    protected final long milliSeconds;

    /**
     * Constructs a new BaseThread with the specified sleep interval.
     *
     * @param milliSeconds the sleep interval in milliseconds between task executions.
     */
    public BaseThread(long milliSeconds) {
        this.milliSeconds = milliSeconds;
    }

    /**
     * Stops the thread execution by setting the running flag to false and interrupting the current thread.
     */
    public void stopRunning() {
        running = false;
        Thread.currentThread().interrupt();
    }

    /**
     * The main execution method of the thread.
     * Must be implemented by subclasses to provide specific functionality.
     */
    @Override
    abstract public void run();

    /**
     * Causes the current thread to sleep for the specified interval.
     * If the sleep is interrupted, the thread's interrupt status is set and running is set to false.
     */
    protected void sleepInterval() {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            running = false;
        }
    }
}
