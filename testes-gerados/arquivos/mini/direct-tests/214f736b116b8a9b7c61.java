/*
 * @test
 * @summary Test Timer(String name, boolean isDaemon) constructor sets thread name and daemon status and starts thread
 * @run main java.util.TimerWitupTest
 */

import java.util.Timer;

public class TimerWitupTest {
    public static void main(String[] args) throws Exception {
        final String threadName = "TestTimerThread";
        final boolean isDaemon = true;

        Timer timer = new Timer(threadName, isDaemon);

        // Wait briefly to ensure thread has started
        Thread.sleep(100);

        Thread timerThread = getTimerThread(timer);

        if (timerThread == null) {
            throw new AssertionError("Timer thread is null");
        }

        if (!threadName.equals(timerThread.getName())) {
            throw new AssertionError("Expected thread name: " + threadName + ", but was: " + timerThread.getName());
        }

        if (timerThread.isDaemon() != isDaemon) {
            throw new AssertionError("Expected daemon status: " + isDaemon + ", but was: " + timerThread.isDaemon());
        }

        if (!timerThread.isAlive()) {
            throw new AssertionError("Timer thread is not alive after construction");
        }

        timer.cancel(); // cleanup
    }

    // Helper method to find the Timer's thread by name
    private static Thread getTimerThread(Timer timer) {
        // The Timer thread name is set to the name passed in constructor
        // We search all threads for one with that name
        Thread[] threads = getAllThreads();
        for (Thread t : threads) {
            if (t != null && t.getName().equals("TestTimerThread")) {
                return t;
            }
        }
        return null;
    }

    // Returns all live threads
    private static Thread[] getAllThreads() {
        ThreadGroup rootGroup = getRootThreadGroup();
        int estimatedSize = rootGroup.activeCount() * 2;
        Thread[] threads = new Thread[estimatedSize];
        int actualSize = rootGroup.enumerate(threads, true);
        Thread[] result = new Thread[actualSize];
        System.arraycopy(threads, 0, result, 0, actualSize);
        return result;
    }

    private static ThreadGroup getRootThreadGroup() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup parent;
        while ((parent = group.getParent()) != null) {
            group = parent;
        }
        return group;
    }
}
