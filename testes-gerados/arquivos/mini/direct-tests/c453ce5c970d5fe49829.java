/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.toArray() returns correct snapshot array
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Submit a simple task to have at least one element in the queue
        executor.schedule(new Runnable() {
            public void run() {
                // no-op
            }
        }, 100, TimeUnit.MILLISECONDS);

        // Immediately call toArray, should include the scheduled task
        Object[] arr = executor.toArray();

        if (arr == null) {
            throw new AssertionError("toArray() returned null");
        }

        if (arr.length == 0) {
            throw new AssertionError("toArray() returned empty array, expected at least one element");
        }

        // Check that all elements are not null
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                throw new AssertionError("toArray() returned array with null element at index " + i);
            }
        }

        executor.shutdownNow();
    }
}
