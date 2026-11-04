/*
 * @test
 * @summary Test ForkJoinTask.adapt(Runnable, T) for null argument and normal usage
 * @run main ForkJoinTaskWitupTest
 */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {
    public static void main(String[] args) {
        testAdaptWithNullRunnable();
        testAdaptWithValidRunnable();
    }

    private static void testAdaptWithNullRunnable() {
        try {
            ForkJoinTask.adapt(null, "result");
            throw new Error("Expected NullPointerException for null runnable");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAdaptWithValidRunnable() {
        Runnable r = new Runnable() {
            public void run() {
                // no-op
            }
        };
        ForkJoinTask<String> task = ForkJoinTask.adapt(r, "testResult");
        if (task == null) {
            throw new Error("adapt returned null for valid runnable");
        }
        // The task should not be done before execution
        if (task.isDone()) {
            throw new Error("Task should not be done before execution");
        }
        // Fork and join to execute
        task.fork();
        String result = task.join();
        if (!"testResult".equals(result)) {
            throw new Error("Expected result 'testResult' but got: " + result);
        }
    }
}
