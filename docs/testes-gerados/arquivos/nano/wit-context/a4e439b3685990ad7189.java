/*
 * @test
 * @summary Test for ScheduledThreadPoolExecutor.isEmpty()
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testIsEmptyWhenEmpty();
        testIsEmptyWhenNotEmpty();
    }

    private static void testIsEmptyWhenEmpty() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        boolean result = executor.isEmpty();
        if (result != true) {
            throw new AssertionError("Expected isEmpty() to be true when no tasks are scheduled, but got " + result);
        }
        executor.shutdown();
    }

    private static void testIsEmptyWhenNotEmpty() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> {}, 1, TimeUnit.MILLISECONDS);
        try {
            Thread.sleep(10); // Wait for the task to be scheduled
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        boolean result = executor.isEmpty();
        if (result != false) {
            throw new AssertionError("Expected isEmpty() to be false when tasks are scheduled, but got " + result);
        }
        executor.shutdown();
    }
}
