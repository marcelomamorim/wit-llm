/*
 * @test
 * @summary WITUP-derived tests for Semaphore.acquire(int)
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativePermitsThrowsIllegalArgumentException();
        testNegativePermitsDoesNotChangeAvailablePermits();
        testZeroPermitsDoesNotConsumePermits();
        testAcquireConsumesRequestedPermits();
        testInterruptedThreadThrowsInterruptedException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativePermitsThrowsIllegalArgumentException() {
        Semaphore s = new Semaphore(2);
        try {
            s.acquire(-1);
            check(false, "expected IllegalArgumentException for acquire(-1)");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (InterruptedException e) {
            throw new RuntimeException("unexpected InterruptedException", e);
        }
    }

    static void testNegativePermitsDoesNotChangeAvailablePermits() throws Exception {
        Semaphore s = new Semaphore(2);
        try {
            s.acquire(-5);
        } catch (IllegalArgumentException e) {
            // expected
        }
        check(s.availablePermits() == 2,
              "available permits changed after rejected acquire: " + s.availablePermits());
    }

    static void testZeroPermitsDoesNotConsumePermits() throws Exception {
        Semaphore s = new Semaphore(3);
        s.acquire(0);
        check(s.availablePermits() == 3,
              "acquire(0) consumed permits: " + s.availablePermits());
    }

    static void testAcquireConsumesRequestedPermits() throws Exception {
        Semaphore s = new Semaphore(3);
        s.acquire(2);
        check(s.availablePermits() == 1,
              "expected 1 available after acquire(2), got " + s.availablePermits());
    }

    static void testInterruptedThreadThrowsInterruptedException() throws Exception {
        Semaphore s = new Semaphore(1);
        Thread.currentThread().interrupt();
        try {
            s.acquire(1);
            check(false, "expected InterruptedException for interrupted thread");
        } catch (InterruptedException e) {
            // expected
        }
        check(!Thread.currentThread().isInterrupted(),
              "interrupt status not cleared after InterruptedException");
        check(s.availablePermits() == 1,
              "permits changed after interrupted acquire: " + s.availablePermits());
    }
}
