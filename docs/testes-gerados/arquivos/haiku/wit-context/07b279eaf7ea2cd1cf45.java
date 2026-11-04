/*
 * @test
 * @summary Behavioral tests for Semaphore.release() using only public observable API
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testReleaseOnZeroPermitsMakesPermitAvailable() {
        Semaphore s = new Semaphore(0);
        check(s.availablePermits() == 0, "initial permits should be 0");
        s.release();
        check(s.availablePermits() == 1, "release() should add one permit");
        check(s.tryAcquire(), "tryAcquire() should succeed after release()");
        check(!s.tryAcquire(), "tryAcquire() should fail when no permits remain");
    }

    private static void testReleaseIncrementsExistingPermits() {
        Semaphore s = new Semaphore(2);
        s.release();
        s.release();
        s.release();
        check(s.availablePermits() == 5, "each release() should add exactly one permit");
    }

    private static void testReleaseOnFairSemaphore() {
        Semaphore s = new Semaphore(1, true);
        check(s.tryAcquire(), "first tryAcquire() should succeed");
        check(!s.tryAcquire(), "second tryAcquire() should fail before release()");
        s.release();
        check(s.availablePermits() == 1, "release() on fair semaphore should restore one permit");
        check(s.tryAcquire(), "tryAcquire() should succeed after release() on fair semaphore");
    }

    private static void testReleaseAfterAcquireRestoresCount() {
        Semaphore s = new Semaphore(3);
        s.acquireUninterruptibly();
        s.acquireUninterruptibly();
        check(s.availablePermits() == 1, "acquire should reduce permits");
        s.release();
        check(s.availablePermits() == 2, "release() should restore one permit after acquire");
    }

    public static void main(String[] args) {
        testReleaseOnZeroPermitsMakesPermitAvailable();
        testReleaseIncrementsExistingPermits();
        testReleaseOnFairSemaphore();
        testReleaseAfterAcquireRestoresCount();
    }
}
