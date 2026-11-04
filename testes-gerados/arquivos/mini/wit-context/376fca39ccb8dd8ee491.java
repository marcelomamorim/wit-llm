/*
 * @test
 * @summary Test ForkJoinTask.adapt(Runnable) for null input and normal Runnable
 * @run main ForkJoinTaskWitupTest
 */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {
    public static void main(String[] args) {
        testAdaptWithNull();
        testAdaptWithSimpleRunnable();
        System.out.println("All tests passed.");
    }

    private static void testAdaptWithNull() {
        try {
            ForkJoinTask.adapt(null);
            throw new AssertionError("Expected NullPointerException for null runnable");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAdaptWithSimpleRunnable() {
        Runnable r = new Runnable() {
            public void run() {
                // no-op
            }
        };
        ForkJoinTask<?> task = ForkJoinTask.adapt(r);
        if (task == null) {
            throw new AssertionError("adapt returned null for valid runnable");
        }
        // The returned task should not be done initially
        if (task.isDone()) {
            throw new AssertionError("Newly adapted task should not be done");
        }
        // Fork and join to check basic execution
        task.fork();
        task.join();
        if (!task.isDone()) {
            throw new AssertionError("Task should be done after join");
        }
    }
}
