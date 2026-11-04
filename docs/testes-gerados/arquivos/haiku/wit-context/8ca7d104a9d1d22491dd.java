/*
 * @test
 * @summary Witup-derived behavioral tests for Semaphore.tryAcquire(int permits)
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativePermitsThrowsIllegalArgumentException();
        testNegativePermitsDoesNotChangeAvailablePermits();
        testZeroPermitsReturnsTrue();
        testAcquireWithinAvailablePermitsReturnsTrueAndDecrements();
        testAcquireMoreThanAvailableReturnsFalseAndKeepsPermits();
        testAcquireExactlyAvailablePermitsSucceeds();
        testAcquireFromEmptySemaphoreReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativePermitsThrowsIllegalArgumentException() {
        Semaphore s = new Semaphore(5);
        boolean thrown = false;
        try {
            s.tryAcquire(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "tryAcquire(-1) should throw IllegalArgumentException");
    }

    static void testNegativePermitsDoesNotChangeAvailablePermits() {
        Semaphore s = new Semaphore(5);
        try {
            s.tryAcquire(-3);
        } catch (IllegalArgumentException e) {
            // expected
        }
        check(s.availablePermits() == 5,
              "availablePermits changed after invalid tryAcquire: " + s.availablePermits());
    }

    static void testZeroPermitsReturnsTrue() {
        Semaphore s = new Semaphore(0);
        check(s.tryAcquire(0), "tryAcquire(0) should return true");
        check(s.availablePermits() == 0,
              "availablePermits changed after tryAcquire(0): " + s.availablePermits());
    }

    static void testAcquireWithinAvailablePermitsReturnsTrueAndDecrements() {
        Semaphore s = new Semaphore(5);
        check(s.tryAcquire(2), "tryAcquire(2) with 5 permits should return true");
        check(s.availablePermits() == 3,
              "expected 3 permits after acquiring 2, got " + s.availablePermits());
    }

    static void testAcquireMoreThanAvailableReturnsFalseAndKeepsPermits() {
        Semaphore s = new Semaphore(5);
        check(!s.tryAcquire(6), "tryAcquire(6) with 5 permits should return false");
        check(s.availablePermits() == 5,
              "permits should be unchanged after failed tryAcquire, got " + s.availablePermits());
    }

    static void testAcquireExactlyAvailablePermitsSucceeds() {
        Semaphore s = new Semaphore(4);
        check(s.tryAcquire(4), "tryAcquire(4) with 4 permits should return true");
        check(s.availablePermits() == 0,
              "expected 0 permits after acquiring all, got " + s.availablePermits());
    }

    static void testAcquireFromEmptySemaphoreReturnsFalse() {
        Semaphore s = new Semaphore(0);
        check(!s.tryAcquire(1), "tryAcquire(1) with 0 permits should return false");
        check(s.availablePermits() == 0,
              "permits should remain 0, got " + s.availablePermits());
    }
}
