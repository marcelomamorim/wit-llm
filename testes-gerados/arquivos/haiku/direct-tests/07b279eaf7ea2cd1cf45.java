/*
 * @test
 * @summary Verify that Semaphore.release() increments the available permit count and makes permits acquirable
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) throws Exception {
        testReleaseFromZero();
        testReleaseIncrementsExistingPermits();
        testReleaseMultipleTimes();
        testReleaseMakesPermitAcquirable();
        testReleaseOnFairSemaphore();
    }

    static void testReleaseFromZero() {
        Semaphore s = new Semaphore(0);
        s.release();
        check(s.availablePermits() == 1, "release() on 0 permits should yield 1 permit");
    }

    static void testReleaseIncrementsExistingPermits() {
        Semaphore s = new Semaphore(2);
        s.release();
        check(s.availablePermits() == 3, "release() on 2 permits should yield 3 permits");
    }

    static void testReleaseMultipleTimes() {
        Semaphore s = new Semaphore(0);
        for (int i = 0; i < 5; i++) {
            s.release();
        }
        check(s.availablePermits() == 5, "five release() calls should yield 5 permits");
    }

    static void testReleaseMakesPermitAcquirable() {
        Semaphore s = new Semaphore(0);
        check(!s.tryAcquire(), "tryAcquire() on 0 permits should return false");
        s.release();
        check(s.tryAcquire(), "tryAcquire() after release() should return true");
        check(!s.tryAcquire(), "tryAcquire() after consuming the single permit should return false");
    }

    static void testReleaseOnFairSemaphore() {
        Semaphore s = new Semaphore(2, true);
        s.release();
        s.release();
        s.release();
        check(s.availablePermits() == 5, "release() on fair semaphore should increment permits");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
