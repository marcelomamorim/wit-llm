/* @test    @summary Test Timer(String name) constructor with a specific thread name    @run main TimerWitupTest */

import java.util.Timer;

public class TimerWitupTest {
    public static void main(String[] args) {
        String threadName = "TestTimerThread";
        Timer timer = new Timer(threadName);
        if (timer == null) {
            throw new AssertionError("Timer instance is null");
        }
        // Since Timer does not expose thread name, test indirectly by creating a task and verifying thread name
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String currentThreadName = Thread.currentThread().getName();
                if (!currentThreadName.equals(threadName)) {
                    throw new AssertionError("Thread name mismatch: expected " + threadName + " but was " + currentThreadName);
                }
                // Cancel timer after first run to avoid lingering threads
                this.cancel();
            }
        };
        timer.schedule(task, 0);
        // Wait briefly to allow task execution
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Cancel the timer to clean up
        timer.cancel();
    }
}
