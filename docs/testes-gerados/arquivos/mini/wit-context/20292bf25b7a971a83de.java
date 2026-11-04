/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.submit(Runnable) for null task and normal execution
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Test normal submission with a simple Runnable
        Runnable task = new Runnable() {
            public void run() {
                // no-op
            }
        };
        Future<?> f = executor.submit(task);
        if (f == null) {
            throw new AssertionError("submit returned null Future");
        }

        // Test submit with null task - expect NullPointerException
        try {
            executor.submit(null);
            throw new AssertionError("submit(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new AssertionError("Executor did not terminate in time");
        }
    }
}
