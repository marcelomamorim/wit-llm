/*
 * @test
 * @summary Deterministic tests for java.util.concurrent.Semaphore.tryAcquire()
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) throws Exception {
        testTryAcquireReturnsTrueWhenPermitsAvailable();
        testTryAcquireReturnsFalseWhenNoPermits();
    }

    private static void testTryAcquireReturnsTrueWhenPermitsAvailable() {
        Semaphore sem = new Semaphore(1);
        boolean acquired = sem.tryAcquire();
        if (!acquired) {
            throw new AssertionError("tryAcquire() should return true when permits are available");
        }
    }

    private static void testTryAcquireReturnsFalseWhenNoPermits() {
        Semaphore sem = new Semaphore(0);
        boolean acquired = sem.tryAcquire();
        if (acquired) {
            throw new AssertionError("tryAcquire() should return false when no permits are available");
        }
    }
}
