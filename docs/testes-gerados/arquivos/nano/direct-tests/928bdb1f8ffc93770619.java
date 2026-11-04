/*
 * @test    @summary Test shutdown() method of ScheduledThreadPoolExecutor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testShutdown();
    }

    public static void testShutdown() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.shutdown();
        boolean isShutdown = executor.isShutdown();
        if (!isShutdown) {
            throw new AssertionError("Executor should be shutdown after shutdown() call");
        }
    }
}
