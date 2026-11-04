/*
 * @test
 * @summary Behavioral checks for Semaphore.acquire(int permits)
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNegativePermitsThrowsIllegalArgument() {
        Semaphore s = new Semaphore(5);
        try {
            s.acquire(-1);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (InterruptedException e) {
            throw new RuntimeException("unexpected InterruptedException", e);
        }
        check(s.availablePermits() == 5, "permits must be unchanged after rejected acquire");
    }

    static void testZeroPermitsReturnsImmediately() throws InterruptedException {
        Semaphore s = new Semaphore(3);
        s.acquire(0);
        check(s.availablePermits() == 3, "acquire(0) must not change permits");
    }

    static void testAcquireReducesPermits() throws InterruptedException {
        Semaphore s = new Semaphore(5);
        s.acquire(2);
        check(s.availablePermits() == 3, "expected 3 permits after acquire(2)");
        s.acquire(3);
        check(s.availablePermits() == 0, "expected 0 permits after acquire(3)");
    }

    static void testAcquireOnFairSemaphore() throws InterruptedException {
        Semaphore s = new Semaphore(4, true);
        s.acquire(4);
        check(s.availablePermits() == 0, "expected 0 permits on fair semaphore");
    }

    static void testInterruptedThreadThrowsInterruptedException() {
        Semaphore s = new Semaphore(0);
        Thread.currentThread().interrupt();
        try {
            s.acquire(1);
            throw new RuntimeException("expected InterruptedException");
        } catch (InterruptedException e) {
            // expected
        }
        check(!Thread.currentThread().isInterrupted(), "interrupt status must be cleared by exception");
        check(s.availablePermits() == 0, "permits must be unchanged after interrupted acquire");
    }

    public static void main(String[] args) throws Exception {
        testNegativePermitsThrowsIllegalArgument();
        testZeroPermitsReturnsImmediately();
        testAcquireReducesPermits();
        testAcquireOnFairSemaphore();
        testInterruptedThreadThrowsInterruptedException();
    }
}
