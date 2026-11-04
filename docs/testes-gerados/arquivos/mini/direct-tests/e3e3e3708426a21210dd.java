/*
 * @test
 * @summary Test Timer(String name) constructor sets thread name and starts thread
 * @run main TimerWitupTest
 */

import java.util.Timer;

public class TimerWitupTest {
    public static void main(String[] args) throws Exception {
        final String threadName = "TestTimerThread";
        Timer timer = new Timer(threadName);

        // Wait briefly to allow thread to start
        Thread.sleep(100);

        // Check that the timer's thread has the expected name
        // The Timer class does not expose the thread directly, so we check active threads
        boolean found = false;
        Thread[] threads = new Thread[Thread.activeCount() * 2];
        int count = Thread.enumerate(threads);
        for (int i = 0; i < count; i++) {
            if (threadName.equals(threads[i].getName())) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new AssertionError("Timer thread with name '" + threadName + "' not found");
        }

        // Cancel timer to allow JVM to exit cleanly
        timer.cancel();
    }
}
