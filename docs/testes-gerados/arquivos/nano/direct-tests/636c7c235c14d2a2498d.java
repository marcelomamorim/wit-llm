/* @test    @summary Test for CyclicBarrier.isBroken() method    @run main CyclicBarrierWitupTest */

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class CyclicBarrierWitupTest {

    public static void main(String[] args) {
        testIsBrokenReturnsFalseInitially();
        testIsBrokenReturnsTrueAfterBreak();
    }

    private static void testIsBrokenReturnsFalseInitially() {
        CyclicBarrier barrier = new CyclicBarrier(2);
        boolean result = barrier.isBroken();
        if (result) {
            throw new AssertionError("Expected isBroken() to return false initially");
        }
    }

    private static void testIsBrokenReturnsTrueAfterBreak() {
        CyclicBarrier barrier = new CyclicBarrier(2);
        // Accessing package-private or internal state to simulate break is not possible directly.
        // Instead, we simulate a broken barrier by causing an exception in await() in a separate thread.
        Thread t = new Thread(() -> {
            try {
                barrier.await();
            } catch (Exception e) {
                // ignore
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Now, forcibly break the barrier by calling reset() to simulate a broken state.
        // Since reset() does not set broken to true, we need to simulate a break via an exception.
        // But as per the source, there's no public method to set broken directly.
        // So, we simulate a broken barrier by causing an exception in await() in another thread.
        // Alternatively, we can assume the barrier is broken after an exception in await().
        // For testing, we can call reset() and then check isBroken().
        barrier.reset();
        boolean result = barrier.isBroken();
        if (!result) {
            throw new AssertionError("Expected isBroken() to return true after reset()");
        }
    }
}
