/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.shutdownNow throws UnsupportedOperationException
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        // Create ScheduledThreadPoolExecutor with 1 core thread
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.shutdownNow();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception, test passes
        } finally {
            executor.shutdown(); // clean up
        }
    }
}
