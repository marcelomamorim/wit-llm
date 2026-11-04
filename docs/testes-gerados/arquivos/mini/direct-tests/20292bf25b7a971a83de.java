/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.submit(Runnable) method
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Test submitting a simple Runnable that sets a flag
        final boolean[] ran = new boolean[1];
        Runnable task = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };

        Future<?> future = executor.submit(task);

        // Wait for task completion
        future.get();

        if (!ran[0]) {
            throw new AssertionError("Runnable task did not run as expected");
        }

        // Test submitting a null Runnable throws NullPointerException
        boolean npeThrown = false;
        try {
            executor.submit(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("submit(null) did not throw NullPointerException");
        }

        executor.shutdown();
    }
}
