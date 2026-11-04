/*
 * @test
 * @summary Behavioral checks for Semaphore.tryAcquire(int permits)
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativePermitsThrows() {
        Semaphore s = new Semaphore(5);
        boolean thrown = false;
        try {
            s.tryAcquire(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "tryAcquire(-1) must throw IllegalArgumentException");
        check(s.availablePermits() == 5, "permits must be unchanged after rejected call");
    }

    static void testNegativePermitsThrowsFair() {
        Semaphore s = new Semaphore(2, true);
        boolean thrown = false;
        try {
            s.tryAcquire(-3);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "fair: tryAcquire(-3) must throw IllegalArgumentException");
        check(s.availablePermits() == 2, "fair: permits must be unchanged");
    }

    static void testZeroPermitsReturnsTrue() {
        Semaphore s = new Semaphore(0);
        check(s.tryAcquire(0), "tryAcquire(0) should return true");
        check(s.availablePermits() == 0, "tryAcquire(0) must not change permits");
    }

    static void testSufficientPermitsAcquired() {
        Semaphore s = new Semaphore(3);
        check(s.tryAcquire(2), "tryAcquire(2) with 3 permits should succeed");
        check(s.availablePermits() == 1, "expected 1 permit left, got " + s.availablePermits());
    }

    static void testInsufficientPermitsNotAcquired() {
        Semaphore s = new Semaphore(3);
        check(s.tryAcquire(2), "first tryAcquire(2) should succeed");
        check(!s.tryAcquire(2), "second tryAcquire(2) with 1 permit should fail");
        check(s.availablePermits() == 1, "failed tryAcquire must not consume permits");
    }

    static void testNoPermitsAvailable() {
        Semaphore s = new Semaphore(0);
        check(!s.tryAcquire(1), "tryAcquire(1) on empty semaphore should return false");
        check(s.availablePermits() == 0, "permits must remain 0");
    }

    static void testExactPermitsAcquired() {
        Semaphore s = new Semaphore(4, true);
        check(s.tryAcquire(4), "fair: tryAcquire(4) with 4 permits should succeed");
        check(s.availablePermits() == 0, "fair: expected 0 permits left");
    }

    public static void main(String[] args) {
        testNegativePermitsThrows();
        testNegativePermitsThrowsFair();
        testZeroPermitsReturnsTrue();
        testSufficientPermitsAcquired();
        testInsufficientPermitsNotAcquired();
        testNoPermitsAvailable();
        testExactPermitsAcquired();
    }
}
