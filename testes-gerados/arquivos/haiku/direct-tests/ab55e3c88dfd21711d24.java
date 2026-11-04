/*
 * @test
 * @summary Verifies observable behavior of CountDownLatch.countDown()
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCountDownDecrementsCount() throws Exception {
        CountDownLatch latch = new CountDownLatch(2);
        check(latch.getCount() == 2, "initial count should be 2");
        latch.countDown();
        check(latch.getCount() == 1, "count should be 1 after one countDown");
        latch.countDown();
        check(latch.getCount() == 0, "count should be 0 after two countDown calls");
    }

    static void testCountDownDoesNotGoNegative() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        latch.countDown();
        latch.countDown();
        latch.countDown();
        check(latch.getCount() == 0, "count must stay at 0 after extra countDown calls");
    }

    static void testCountDownOnZeroLatchIsNoOp() throws Exception {
        CountDownLatch latch = new CountDownLatch(0);
        check(latch.getCount() == 0, "initial count should be 0");
        latch.countDown();
        check(latch.getCount() == 0, "countDown on zero latch must keep count at 0");
    }

    static void testAwaitReturnsAfterCountReachesZero() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        latch.countDown();
        check(latch.await(1, TimeUnit.SECONDS), "await should return true once count is zero");
    }

    static void testAwaitTimesOutBeforeCountReachesZero() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        check(!latch.await(10, TimeUnit.MILLISECONDS), "await should time out while count is non-zero");
        check(latch.getCount() == 1, "count must be unchanged by await");
    }

    static void testCountDownFromOtherThreadReleasesWaiter() throws Exception {
        final int n = 4;
        final CountDownLatch latch = new CountDownLatch(n);
        Thread[] workers = new Thread[n];
        for (int i = 0; i < n; i++) {
            workers[i] = new Thread(new Runnable() {
                public void run() {
                    latch.countDown();
                }
            });
        }
        for (Thread t : workers) {
            t.start();
        }
        for (Thread t : workers) {
            t.join();
        }
        check(latch.getCount() == 0, "count should be 0 after all workers counted down");
        check(latch.await(1, TimeUnit.SECONDS), "await should return true after all workers counted down");
    }

    public static void main(String[] args) throws Exception {
        testCountDownDecrementsCount();
        testCountDownDoesNotGoNegative();
        testCountDownOnZeroLatchIsNoOp();
        testAwaitReturnsAfterCountReachesZero();
        testAwaitTimesOutBeforeCountReachesZero();
        testCountDownFromOtherThreadReleasesWaiter();
    }
}
