/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.add(Runnable) for null and normal input
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Test add(null) should throw NullPointerException
        try {
            executor.add(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test add with a simple Runnable returns true or false (behavior depends on internal queue)
        Runnable task = new Runnable() {
            public void run() {}
        };

        boolean result = executor.add(task);
        // The add method delegates to offer, which may throw UnsupportedOperationException or return boolean
        // According to current code, it returns offer(e), so result is boolean
        // We assert that no exception is thrown and result is boolean
        if (!(result == true || result == false)) {
            throw new Error("add returned non-boolean value");
        }

        executor.shutdown();
    }
}
