/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.offer(Runnable x) method for basic behavior.
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testOfferWithValidRunnable();
        testOfferWithNullRunnable();
    }

    static void testOfferWithValidRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        AtomicBoolean executed = new AtomicBoolean(false);
        Runnable r = new Runnable() {
            public void run() {
                executed.set(true);
            }
        };
        boolean result = executor.offer(r);
        assert result : "offer should return true";
        executor.shutdown();
    }

    static void testOfferWithNullRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.offer(null);
            assert false : "offer with null should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        } finally {
            executor.shutdown();
        }
    }
}
