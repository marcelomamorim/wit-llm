/* @test    @summary Test getNumberWaiting() method of CyclicBarrier    @run main CyclicBarrierWitupTest */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {
    public static void main(String[] args) {
        testGetNumberWaiting();
    }

    public static void testGetNumberWaiting() {
        int parties = 3;
        CyclicBarrier barrier = new CyclicBarrier(parties);
        int initialCount = barrier.getNumberWaiting();
        if (initialCount != 0) {
            throw new AssertionError("Expected initial number waiting to be 0, but was " + initialCount);
        }
        // Use reflection to access private lock if needed, but here we test observable behavior
        // Since getNumberWaiting() returns parties - count, and count is initially 0,
        // the expected value is parties - 0 = parties.
        int expected = parties;
        int actual = barrier.getNumberWaiting();
        if (actual != expected) {
            throw new AssertionError("Expected getNumberWaiting() to return " + expected + ", but was " + actual);
        }
        // To test that getNumberWaiting decreases after a thread arrives, simulate a thread arriving
        // Since the actual thread waiting is complex, we can simulate by calling await() in a thread
        Thread t = new Thread(() -> {
            try {
                barrier.await();
            } catch (Exception e) {
                // ignore
            }
        });
        t.start();
        try {
            Thread.sleep(50); // wait for thread to reach await
        } catch (InterruptedException e) {
            // ignore
        }
        int waitingCount = barrier.getNumberWaiting();
        if (waitingCount != 1) {
            throw new AssertionError("Expected number waiting to be 1 after one thread called await(), but was " + waitingCount);
        }
        try {
            t.join();
        } catch (InterruptedException e) {
            // ignore
        }
        // After thread passes await, number waiting should be 0 again
        int finalCount = barrier.getNumberWaiting();
        if (finalCount != 0) {
            throw new AssertionError("Expected final number waiting to be 0, but was " + finalCount);
        }
    }
}
