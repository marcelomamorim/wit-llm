/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.shutdownNow() returns expected list and changes state
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Submit a simple task that sleeps briefly
        executor.schedule(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }, 500, TimeUnit.MILLISECONDS);

        // Call shutdownNow and capture returned list
        List<Runnable> notExecuted = executor.shutdownNow();

        // The returned list should contain the scheduled task since it was not started
        if (notExecuted == null || notExecuted.isEmpty()) {
            throw new AssertionError("shutdownNow() returned empty list, expected at least one task");
        }

        // After shutdownNow, executor should be shutdown
        if (!executor.isShutdown()) {
            throw new AssertionError("Executor should be shutdown after shutdownNow()");
        }

        // Await termination to ensure executor terminates
        boolean terminated = executor.awaitTermination(2, TimeUnit.SECONDS);
        if (!terminated) {
            throw new AssertionError("Executor did not terminate after shutdownNow()");
        }
    }
}
