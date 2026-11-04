/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.toArray() method behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ScheduledThreadPoolExecutor with 1 core thread
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Schedule a simple task to run after 10 milliseconds
        executor.schedule(new Runnable() {
            public void run() {
                // no-op
            }
        }, 10, TimeUnit.MILLISECONDS);

        // Call toArray() and verify it returns an Object[] with expected length
        Object[] arr = executor.toArray();

        if (arr == null) {
            throw new AssertionError("toArray() returned null");
        }

        // The array length should be >= 0 (empty or with scheduled tasks)
        if (arr.length < 0) {
            throw new AssertionError("toArray() returned array with negative length");
        }

        // The array elements should be non-null RunnableScheduledFuture or null (depending on timing)
        for (Object o : arr) {
            if (o == null) {
                throw new AssertionError("toArray() returned array containing null element");
            }
            // We cannot assert exact class, but it should be Runnable or subclass
            if (!(o instanceof Runnable)) {
                throw new AssertionError("toArray() returned array containing non-Runnable element: " + o.getClass());
            }
        }

        // Shutdown executor cleanly
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new AssertionError("Executor did not terminate in time");
        }
    }
}
