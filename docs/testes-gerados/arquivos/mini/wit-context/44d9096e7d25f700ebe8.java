/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.size() method for correct locking and size retrieval
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ScheduledThreadPoolExecutor with 1 core thread
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Initially, size should be 0
        int initialSize = executor.size();
        if (initialSize != 0) {
            throw new AssertionError("Expected initial size 0 but got " + initialSize);
        }

        // Schedule a no-op task with delay 0
        executor.schedule(new Runnable() {
            public void run() {
                // no-op
            }
        }, 0, TimeUnit.MILLISECONDS);

        // Give some time for the task to be queued
        Thread.sleep(50);

        // Size should be at least 1 now (task queued)
        int sizeAfterSchedule = executor.size();
        if (sizeAfterSchedule < 1) {
            throw new AssertionError("Expected size >= 1 after scheduling task but got " + sizeAfterSchedule);
        }

        // Shutdown executor
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new AssertionError("Executor did not terminate in time");
        }

        // After shutdown and termination, size should be 0
        int sizeAfterShutdown = executor.size();
        if (sizeAfterShutdown != 0) {
            throw new AssertionError("Expected size 0 after shutdown but got " + sizeAfterShutdown);
        }
    }
}
