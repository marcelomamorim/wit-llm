/*
 * @test
 * @summary Verifies IllegalStateException from iterator remove() on the ScheduledThreadPoolExecutor queue when no element was returned by next() or after it was already removed
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> queue = pool.getQueue();

            Iterator<Runnable> emptyIt = queue.iterator();
            boolean threw = false;
            try {
                emptyIt.remove();
            } catch (IllegalStateException expected) {
                threw = true;
            }
            check(threw, "remove() before next() must throw IllegalStateException");

            pool.schedule(() -> { }, 1, TimeUnit.HOURS);
            check(queue.size() == 1, "queue should contain the delayed task");

            Iterator<Runnable> it = queue.iterator();
            check(it.hasNext(), "iterator should have one element");
            it.next();
            it.remove();
            check(queue.isEmpty(), "remove() after next() must remove the element");

            threw = false;
            try {
                it.remove();
            } catch (IllegalStateException expected) {
                threw = true;
            }
            check(threw, "second remove() without next() must throw IllegalStateException");
        } finally {
            pool.shutdownNow();
        }
    }
}
