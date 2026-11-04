/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.add(Runnable) delegates to offer(Runnable)
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.RejectedExecutionHandler;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Test add with a simple Runnable
        Runnable task = new Runnable() {
            public void run() {
                // no-op
            }
        };

        boolean added = executor.add(task);
        if (!added) {
            throw new RuntimeException("add(Runnable) should return true when offer succeeds");
        }

        // Shutdown executor to clean up
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new RuntimeException("Executor did not terminate in time");
        }
    }
}
