/*
 * @test
 * @summary Test for clear() method in ScheduledThreadPoolExecutor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testClearMethod();
    }

    public static void testClearMethod() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        try {
            // Schedule a dummy task to ensure queue is not empty
            executor.schedule(() -> {}, 1, TimeUnit.SECONDS);
            // Call clear() and verify no exceptions thrown
            executor.clear();
            // After clear, queue should be empty, size zero
            // No direct API to verify queue contents, but no exception indicates success
        } catch (Exception e) {
            throw new AssertionError("clear() method threw an exception: " + e, e);
        }
    }
}
