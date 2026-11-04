/*
 * @test
 * @summary Test java.util.concurrent.Semaphore.tryAcquire(int) for valid and invalid permits
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) {
        testTryAcquireWithNegativePermits();
        testTryAcquireWithZeroPermits();
        testTryAcquireWithPositivePermits();
    }

    private static void testTryAcquireWithNegativePermits() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.tryAcquire(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative permits");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testTryAcquireWithZeroPermits() {
        Semaphore semaphore = new Semaphore(1);
        // tryAcquire(0) should return true immediately (no permits requested)
        boolean result = semaphore.tryAcquire(0);
        if (!result) {
            throw new RuntimeException("tryAcquire(0) should return true");
        }
    }

    private static void testTryAcquireWithPositivePermits() {
        Semaphore semaphore = new Semaphore(2);
        // tryAcquire with 1 permit should succeed
        boolean acquired = semaphore.tryAcquire(1);
        if (!acquired) {
            throw new RuntimeException("tryAcquire(1) should succeed when permits are available");
        }
        // tryAcquire with 2 permits should fail now (only 1 permit left)
        boolean acquired2 = semaphore.tryAcquire(2);
        if (acquired2) {
            throw new RuntimeException("tryAcquire(2) should fail when insufficient permits");
        }
    }
}
