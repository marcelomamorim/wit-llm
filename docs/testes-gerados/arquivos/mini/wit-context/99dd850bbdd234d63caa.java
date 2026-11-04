/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.contains(Object) method behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        // Create ScheduledThreadPoolExecutor with 1 core thread
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Test contains with null (should not throw, returns false)
        boolean resultNull = executor.contains(null);
        if (resultNull) {
            throw new RuntimeException("contains(null) should return false when no tasks submitted");
        }

        // Test contains with an arbitrary object (should not throw, returns false)
        Object obj = new Object();
        boolean resultObj = executor.contains(obj);
        if (resultObj) {
            throw new RuntimeException("contains(obj) should return false when no tasks submitted");
        }

        // Submit a Runnable task and test contains with that task
        Runnable task = new Runnable() {
            public void run() {}
        };
        executor.schedule(task, 10, java.util.concurrent.TimeUnit.MILLISECONDS);

        // Because contains checks for presence, it should return true for the submitted task
        boolean resultTask = executor.contains(task);
        if (!resultTask) {
            throw new RuntimeException("contains(task) should return true after scheduling the task");
        }

        // Test contains with a different new object (should return false)
        Object other = new Object();
        boolean resultOther = executor.contains(other);
        if (resultOther) {
            throw new RuntimeException("contains(other) should return false for unknown object");
        }

        executor.shutdown();

        System.out.println("ScheduledThreadPoolExecutor.contains(Object) tests passed.");
    }
}
