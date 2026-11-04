/*
 * @test
 * @summary Checks observable behaviour of CyclicBarrier.getNumberWaiting()
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierWitupTest {

    public static void main(String[] args) throws Exception {
        testFreshBarrierHasNoWaiters();
        testWaitingCountReflectsArrivedThread();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFreshBarrierHasNoWaiters() {
        CyclicBarrier barrier = new CyclicBarrier(3);
        check(barrier.getNumberWaiting() == 0, "fresh barrier should have 0 waiters");

        CyclicBarrier withAction = new CyclicBarrier(3, new Runnable() {
            public void run() { }
        });
        check(withAction.getNumberWaiting() == 0, "fresh barrier with action should have 0 waiters");
    }

    static void testWaitingCountReflectsArrivedThread() throws Exception {
        final AtomicInteger actionRuns = new AtomicInteger();
        final CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {
            public void run() { actionRuns.incrementAndGet(); }
        });

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    barrier.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();

        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(30);
        while (barrier.getNumberWaiting() != 1) {
            check(System.nanoTime() < deadline, "timed out waiting for one party to arrive");
            Thread.sleep(10);
        }
        check(barrier.getNumberWaiting() == 1, "exactly one party should be waiting");
        check(actionRuns.get() == 0, "barrier action must not run before the trip");

        barrier.await();
        t.join(TimeUnit.SECONDS.toMillis(30));
        check(!t.isAlive(), "waiting thread should be released after trip");
        check(actionRuns.get() == 1, "barrier action should run once on trip");
        check(barrier.getNumberWaiting() == 0, "no parties should be waiting after trip");
    }
}
