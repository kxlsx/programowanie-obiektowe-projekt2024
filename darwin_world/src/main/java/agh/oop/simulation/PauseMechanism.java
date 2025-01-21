package agh.oop.simulation;

public class PauseMechanism {
    private boolean paused = false;

    public  synchronized void waitOnPause() throws InterruptedException {
        while (paused) {
            wait();
        }
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized  void unpause() {
        paused = false;
        notifyAll();
    }
}
