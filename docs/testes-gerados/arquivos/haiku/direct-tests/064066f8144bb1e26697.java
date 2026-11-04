/*
 * @test
 * @summary Verifies observable behavior of DelayedWorkQueue.offer via ScheduledThreadPoolExecutor.getQueue()
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testNonScheduledRunnableThrowsClassCastException();
        testScheduledTasksAreOfferedInDelayOrder();
    }

    static void testNullThrowsNullPointerException() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            ex.getQueue().offer(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            ex.shutdownNow();
        }
    }

    static void testNonScheduledRunnableThrowsClassCastException() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            Runnable plain = new Runnable() {
                public void run() { }
            };
            ex.getQueue().offer(plain);
            throw new RuntimeException("expected ClassCastException");
        } catch (ClassCastException expected) {
            // expected
        } finally {
            ex.shutdownNow();
        }
    }

    static void testScheduledTasksAreOfferedInDelayOrder() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            ScheduledFuture<?> f1 = ex.schedule(new Runnable() {
                public void run() { }
            }, 1, TimeUnit.HOURS);
            if (ex.getQueue().size() != 1) {
                throw new RuntimeException("expected queue size 1 after first schedule");
            }
            ScheduledFuture<?> f2 = ex.schedule(new Runnable() {
                public void run() { }
            }, 2, TimeUnit.HOURS);
            if (ex.getQueue().size() != 2) {
                throw new RuntimeException("expected queue size 2 after second schedule");
            }
            Runnable head = ex.getQueue().peek();
            if (head != f1) {
                throw new RuntimeException("expected earliest-delay task at head of queue");
            }
            if (!(f2 instanceof RunnableScheduledFuture)) {
                throw new RuntimeException("returned future is not a RunnableScheduledFuture");
            }
        } finally {
            ex.shutdownNow();
        }
    }
}
