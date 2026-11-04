/*
 * @test
 * @summary Test java.util.concurrent.Semaphore.release(int) method
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) throws Exception {
        testReleasePositivePermits();
        testReleaseZeroPermits();
        testReleaseNegativePermitsThrows();
    }

    static void testReleasePositivePermits() {
        Semaphore sem = new Semaphore(0);
        // Release 3 permits
        sem.release(3);
        // After release, tryAcquire(3) should succeed
        boolean acquired = sem.tryAcquire(3);
        if (!acquired) {
            throw new AssertionError("Failed to acquire permits after release(3)");
        }
    }

    static void testReleaseZeroPermits() {
        Semaphore sem = new Semaphore(0);
        // Release 0 permits should not change state
        sem.release(0);
        // tryAcquire(1) should fail because no permits were added
        boolean acquired = sem.tryAcquire(1);
        if (acquired) {
            throw new AssertionError("Acquired permit after release(0), expected none");
        }
    }

    static void testReleaseNegativePermitsThrows() {
        Semaphore sem = new Semaphore(0);
        try {
            sem.release(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative permits");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
