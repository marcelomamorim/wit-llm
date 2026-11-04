/*
 * @test
 * @summary Verifies that clearing the delayed work queue of a ScheduledThreadPoolExecutor removes all pending tasks
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> q = ex.getQueue();
            check(q.isEmpty(), "queue should start empty");

            ex.schedule(() -> {}, 1, TimeUnit.HOURS);
            ex.schedule(() -> {}, 2, TimeUnit.HOURS);
            check(q.size() == 2, "expected 2 pending tasks but was " + q.size());

            q.clear();
            check(q.isEmpty(), "queue should be empty after clear()");
            check(q.size() == 0, "size should be 0 after clear()");

            q.clear();
            check(q.isEmpty(), "clear() on empty queue should keep it empty");

            ex.schedule(() -> {}, 1, TimeUnit.HOURS);
            check(q.size() == 1, "queue should accept new tasks after clear()");
        } finally {
            ex.shutdownNow();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
