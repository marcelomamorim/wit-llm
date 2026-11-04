/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.iterator() method behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ScheduledThreadPoolExecutor with 1 core thread
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Obtain iterator from executor
        Iterator<Runnable> it = executor.iterator();

        // The iterator should not be null
        if (it == null) {
            throw new AssertionError("iterator() returned null");
        }

        // The iterator should have no elements initially
        if (it.hasNext()) {
            throw new AssertionError("iterator() should be empty initially");
        }

        // Shutdown executor to clean up
        executor.shutdown();
    }
}
