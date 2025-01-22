package agh.oop.simulation;

public class PauseMechanism {
    private boolean paused = false;

    /**
     * Used as pause point for thread that is managed, the managed thread won't exit from this function as along as mechanism is paused.
     * @throws InterruptedException when wait gets interrupted.
     */
    public  synchronized void waitOnPause() throws InterruptedException {
        while (paused) {
            wait();
        }
    }

    /**
     * Pauses managed thread.
     */
    public synchronized void pause() {
        paused = true;
    }

    /**
     * Unpauses managed thread.
     */
    public synchronized  void unpause() {
        paused = false;
        notifyAll();
    }
}
