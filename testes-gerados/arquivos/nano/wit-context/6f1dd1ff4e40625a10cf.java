/*
 * @test
 * @summary Test for Semaphore.tryAcquire() method.
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) {
        testTryAcquireReturnsTrueWhenPermitAvailable();
        testTryAcquireReturnsFalseWhenNoPermit();
    }

    private static void testTryAcquireReturnsTrueWhenPermitAvailable() {
        Semaphore semaphore = new Semaphore(1);
        boolean result = semaphore.tryAcquire();
        if (!result) {
            throw new AssertionError("Expected tryAcquire() to return true when permit is available");
        }
        if (semaphore.availablePermits() != 0) {
            throw new AssertionError("Expected availablePermits() to be 0 after successful tryAcquire()");
        }
        semaphore.release();
    }

    private static void testTryAcquireReturnsFalseWhenNoPermit() {
        Semaphore semaphore = new Semaphore(0);
        boolean result = semaphore.tryAcquire();
        if (result) {
            throw new AssertionError("Expected tryAcquire() to return false when no permit is available");
        }
        if (semaphore.availablePermits() != 0) {
            throw new AssertionError("Expected availablePermits() to remain 0 after failed tryAcquire()");
        }
    }
}
