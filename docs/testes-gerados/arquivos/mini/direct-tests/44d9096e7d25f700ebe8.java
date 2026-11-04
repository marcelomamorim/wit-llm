/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.size() method for basic correctness
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ScheduledThreadPoolExecutor with 1 core thread
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Initially, size() should be 0 because no tasks are scheduled
        int initialSize = executor.size();
        if (initialSize != 0) {
            throw new Error("Expected initial size 0 but got " + initialSize);
        }

        // Schedule a task with delay to increase internal queue size
        executor.schedule(new Runnable() {
            public void run() {
                // no-op
            }
        }, 100, TimeUnit.MILLISECONDS);

        // size() should be 1 after scheduling one task
        int sizeAfterSchedule = executor.size();
        if (sizeAfterSchedule != 1) {
            throw new Error("Expected size 1 after scheduling one task but got " + sizeAfterSchedule);
        }

        // Wait for the task to run and complete
        Thread.sleep(200);

        // After task execution, size() should return 0 again
        int sizeAfterExecution = executor.size();
        if (sizeAfterExecution != 0) {
            throw new Error("Expected size 0 after task execution but got " + sizeAfterExecution);
        }

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new Error("Executor did not terminate in time");
        }
    }
}
