/*
 * @test
 * @summary Test ForkJoinTask.adapt(Runnable) returns a non-null ForkJoinTask
 * @run main ForkJoinTaskWitupTest
 */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {
    public static void main(String[] args) {
        // Test with a simple Runnable that sets a flag
        final boolean[] ran = {false};
        Runnable r = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };

        ForkJoinTask<?> task = ForkJoinTask.adapt(r);
        if (task == null) {
            throw new AssertionError("ForkJoinTask.adapt returned null");
        }

        // Fork and join the task to ensure it runs
        task.fork();
        task.join();

        if (!ran[0]) {
            throw new AssertionError("Adapted task did not run the Runnable");
        }

        // Test with a Runnable that throws RuntimeException
        Runnable throwingRunnable = new Runnable() {
            public void run() {
                throw new RuntimeException("Test exception");
            }
        };

        ForkJoinTask<?> throwingTask = ForkJoinTask.adapt(throwingRunnable);
        throwingTask.fork();
        try {
            throwingTask.join();
            throw new AssertionError("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            if (!"Test exception".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
