/*
 * @test
 * @summary CyclicBarrier.getNumberWaiting reports the number of parties currently waiting at the barrier
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void waitForWaiting(CyclicBarrier barrier, int expected) throws InterruptedException {
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(30);
        while (barrier.getNumberWaiting() != expected) {
            if (System.nanoTime() > deadline) {
                throw new RuntimeException("timeout: expected " + expected
                        + " but was " + barrier.getNumberWaiting());
            }
            Thread.sleep(1);
        }
    }

    static Runnable awaitTask(CyclicBarrier barrier, AtomicInteger completed) {
        return () -> {
            try {
                barrier.await();
                completed.incrementAndGet();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        };
    }

    static void testInitialAndProgressive() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(3);
        check(barrier.getNumberWaiting() == 0, "initial count must be 0");

        AtomicInteger completed = new AtomicInteger();
        Thread t1 = new Thread(awaitTask(barrier, completed));
        t1.start();
        waitForWaiting(barrier, 1);
        check(barrier.getNumberWaiting() == 1, "expected 1 waiting");

        Thread t2 = new Thread(awaitTask(barrier, completed));
        t2.start();
        waitForWaiting(barrier, 2);
        check(barrier.getNumberWaiting() == 2, "expected 2 waiting");
        check(completed.get() == 0, "no party should be released before tripping");

        barrier.await();
        t1.join(TimeUnit.SECONDS.toMillis(30));
        t2.join(TimeUnit.SECONDS.toMillis(30));
        check(!t1.isAlive() && !t2.isAlive(), "worker threads did not finish");
        check(completed.get() == 2, "expected 2 released workers");
        check(barrier.getNumberWaiting() == 0, "count must be 0 after tripping");
    }

    static void testBarrierActionRunsOnce() throws Exception {
        AtomicInteger actionRuns = new AtomicInteger();
        CyclicBarrier barrier = new CyclicBarrier(2, actionRuns::incrementAndGet);
        check(barrier.getNumberWaiting() == 0, "initial count must be 0 with action");

        AtomicInteger completed = new AtomicInteger();
        Thread t = new Thread(awaitTask(barrier, completed));
        t.start();
        waitForWaiting(barrier, 1);
        barrier.await();
        t.join(TimeUnit.SECONDS.toMillis(30));
        check(!t.isAlive(), "worker thread did not finish");
        check(actionRuns.get() == 1, "barrier action must run exactly once");
        check(completed.get() == 1, "worker must be released");
        check(barrier.getNumberWaiting() == 0, "count must be 0 after tripping");
    }

    static void testSinglePartyNeverWaits() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(1);
        check(barrier.getNumberWaiting() == 0, "initial count must be 0");
        barrier.await();
        check(barrier.getNumberWaiting() == 0, "count must remain 0 for single party");
    }

    public static void main(String[] args) throws Exception {
        testInitialAndProgressive();
        testBarrierActionRunsOnce();
        testSinglePartyNeverWaits();
    }
}
