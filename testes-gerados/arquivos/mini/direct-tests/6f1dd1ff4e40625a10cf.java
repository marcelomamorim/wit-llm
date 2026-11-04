/*
 * @test
 * @summary Basic tests for Semaphore.tryAcquire()
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) throws Exception {
        testTryAcquireWithAvailablePermits();
        testTryAcquireWithNoPermits();
    }

    static void testTryAcquireWithAvailablePermits() {
        Semaphore sem = new Semaphore(1);
        boolean acquired = sem.tryAcquire();
        if (!acquired) {
            throw new RuntimeException("tryAcquire() should succeed when permits are available");
        }
        // After acquiring, tryAcquire should fail if no permits left
        boolean acquiredAgain = sem.tryAcquire();
        if (acquiredAgain) {
            throw new RuntimeException("tryAcquire() should fail when no permits are available");
        }
    }

    static void testTryAcquireWithNoPermits() {
        Semaphore sem = new Semaphore(0);
        boolean acquired = sem.tryAcquire();
        if (acquired) {
            throw new RuntimeException("tryAcquire() should fail when initialized with zero permits");
        }
    }
}
