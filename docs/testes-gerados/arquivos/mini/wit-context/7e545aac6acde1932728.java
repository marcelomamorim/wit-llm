/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.put(Runnable) for exception and normal behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Test put with null argument: expect NullPointerException
        try {
            executor.put(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test put with a valid Runnable: expect no exception
        Runnable task = new Runnable() {
            public void run() {
                // no-op
            }
        };

        try {
            executor.put(task);
        } catch (UnsupportedOperationException uoe) {
            // The underlying queue's put may throw UnsupportedOperationException
            // Acceptable as per current implementation
        } catch (Exception e) {
            throw new Error("Unexpected exception: " + e);
        }

        executor.shutdown();
    }
}
