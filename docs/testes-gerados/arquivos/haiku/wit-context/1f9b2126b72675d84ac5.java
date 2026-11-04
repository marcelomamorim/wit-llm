/*
 * @test
 * @summary Witup-generated tests for Semaphore.acquireUninterruptibly(int)
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativePermitsThrows();
        testNegativeMinValueThrows();
        testZeroPermitsNoChange();
        testAcquireReducesPermits();
        testAcquireAllPermits();
        testBlockedAcquireCompletesAfterRelease();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativePermitsThrows() {
        Semaphore s = new Semaphore(5);
        try {
            s.acquireUninterruptibly(-1);
            throw new RuntimeException("expected IllegalArgumentException for -1");
        } catch (IllegalArgumentException expected) {
        }
        check(s.availablePermits() == 5, "permits changed after rejected call");
    }

    static void testNegativeMinValueThrows() {
        Semaphore s = new Semaphore(3);
        try {
            s.acquireUninterruptibly(Integer.MIN_VALUE);
            throw new RuntimeException("expected IllegalArgumentException for MIN_VALUE");
        } catch (IllegalArgumentException expected) {
        }
        check(s.availablePermits() == 3, "permits changed after rejected call");
    }

    static void testZeroPermitsNoChange() {
        Semaphore s = new Semaphore(2);
        s.acquireUninterruptibly(0);
        check(s.availablePermits() == 2, "acquire(0) changed permits");
    }

    static void testAcquireReducesPermits() {
        Semaphore s = new Semaphore(5);
        s.acquireUninterruptibly(3);
        check(s.availablePermits() == 2, "expected 2 permits after acquiring 3 of 5");
    }

    static void testAcquireAllPermits() {
        Semaphore s = new Semaphore(4);
        s.acquireUninterruptibly(4);
        check(s.availablePermits() == 0, "expected 0 permits after acquiring all");
        check(!s.tryAcquire(), "tryAcquire should fail when no permits remain");
    }

    static void testBlockedAcquireCompletesAfterRelease() throws InterruptedException {
        final Semaphore s = new Semaphore(2);
        Thread t = new Thread(() -> s.acquireUninterruptibly(3));
        t.start();
        s.release(1);
        t.join();
        check(!t.isAlive(), "acquiring thread did not complete");
        check(s.availablePermits() == 0, "expected 0 permits after blocked acquire of 3 from 2+1");
    }
}
