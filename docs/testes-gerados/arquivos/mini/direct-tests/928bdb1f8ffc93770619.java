/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.shutdown() calls super.shutdown() without error
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.shutdown();
        if (!executor.isShutdown()) {
            throw new AssertionError("Executor should be shutdown after shutdown() call");
        }
    }
}
