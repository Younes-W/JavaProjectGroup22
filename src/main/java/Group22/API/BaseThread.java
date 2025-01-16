package Group22.API;

public abstract class BaseThread implements Runnable {
    protected volatile boolean running = true;
    protected final long milliSeconds;

    public BaseThread(long milliSeconds) {
        this.milliSeconds = milliSeconds;
    }
    public void stopRunning() {
        running = false;
    }

    protected void sleepInterval() {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            running = false;
        }
    }

}
