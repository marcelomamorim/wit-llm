/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.iterator() returns a consistent iterator snapshot
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Schedule a simple task
        Runnable task = new Runnable() {
            public void run() {}
        };
        executor.schedule(task, 10, TimeUnit.MILLISECONDS);

        // Get iterator snapshot
        Iterator<Runnable> it1 = executor.iterator();

        // The iterator should have exactly one element (the scheduled task)
        if (!it1.hasNext()) {
            throw new AssertionError("Iterator should have one element");
        }
        Runnable r = it1.next();
        if (r == null) {
            throw new AssertionError("Iterator returned null element");
        }
        if (it1.hasNext()) {
            throw new AssertionError("Iterator should have only one element");
        }

        // Cancel the task and check iterator again
        executor.remove(task);
        Iterator<Runnable> it2 = executor.iterator();

        // After removal, iterator should have no elements
        if (it2.hasNext()) {
            throw new AssertionError("Iterator should be empty after task removal");
        }

        executor.shutdownNow();
    }
}
