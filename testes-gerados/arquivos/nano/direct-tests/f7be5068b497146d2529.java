/*
 * @test
 * @summary Basic functionality test for CountDownLatch
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testInitialCountZero();
        testInitialCountPositive();
        testCountDownDecrements();
        testAwaitBlocksUntilCountZero();
        testAwaitReturnsImmediatelyWhenCountZero();
        testCountDownThrowsOnNegativeCount();
    }

    static void testInitialCountZero() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(0);
        // Await should return immediately
        latch.await();
        // Count should be zero
        assert latch.getCount() == 0 : "Expected count to be 0";
    }

    static void testInitialCountPositive() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        // Await should block until count reaches zero
        Thread t = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        Thread.sleep(50); // ensure thread is waiting
        assert t.isAlive() : "Thread should be waiting";
        latch.countDown();
        Thread.sleep(50); // allow thread to proceed
        assert t.isAlive() == false : "Thread should have finished";
        assert latch.getCount() == 1 : "Count should be 1 after one countDown";
    }

    static void testCountDownDecrements() {
        CountDownLatch latch = new CountDownLatch(3);
        latch.countDown();
        assert latch.getCount() == 2 : "Count should be 2 after one countDown";
        latch.countDown();
        assert latch.getCount() == 1 : "Count should be 1 after two countDowns";
        latch.countDown();
        assert latch.getCount() == 0 : "Count should be 0 after three countDowns";
    }

    static void testAwaitBlocksUntilCountZero() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        Thread.sleep(50);
        assert t.isAlive() : "Thread should be waiting";
        latch.countDown();
        t.join(100);
        assert t.getState() == Thread.State.TERMINATED : "Thread should have terminated";
    }

    static void testAwaitReturnsImmediatelyWhenCountZero() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(0);
        long start = System.nanoTime();
        latch.await();
        long duration = System.nanoTime() - start;
        assert duration < 1_000_000 : "Await on zero count should return immediately";
    }

    static void testCountDownThrowsOnNegativeCount() {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            // Reflection is not used; simulate invalid count by subclassing or similar if needed
            // but constructor throws IllegalArgumentException on negative count, so test that
            new CountDownLatch(-1);
            assert false : "Expected IllegalArgumentException for negative count";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
