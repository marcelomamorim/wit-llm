/*
 * @test
 * @summary remove() on the DelayedWorkQueue iterator throws IllegalStateException when no element was returned by next(), and removes the last returned task otherwise
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            ScheduledFuture<?> f = ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            if (ex.getQueue().size() != 1) {
                throw new AssertionError("expected one queued task");
            }

            Iterator<Runnable> it = ex.getQueue().iterator();

            boolean threw = false;
            try {
                it.remove();
            } catch (IllegalStateException expected) {
                threw = true;
            }
            if (!threw) {
                throw new AssertionError("remove() before next() must throw IllegalStateException");
            }

            Runnable r = it.next();
            if (r != f) {
                throw new AssertionError("next() should return the scheduled task");
            }
            it.remove();
            if (ex.getQueue().size() != 0) {
                throw new AssertionError("remove() should remove the last returned task");
            }

            threw = false;
            try {
                it.remove();
            } catch (IllegalStateException expected) {
                threw = true;
            }
            if (!threw) {
                throw new AssertionError("second remove() without next() must throw IllegalStateException");
            }
        } finally {
            ex.shutdownNow();
        }
    }
}
