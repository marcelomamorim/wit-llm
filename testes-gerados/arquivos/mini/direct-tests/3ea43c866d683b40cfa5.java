/*
 * @test
 * @summary Test java.util.concurrent.Semaphore.acquire(int) method
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) throws Exception {
        testAcquireWithValidPermits();
        testAcquireWithZeroPermits();
        testAcquireWithNegativePermits();
    }

    static void testAcquireWithValidPermits() throws Exception {
        final Semaphore semaphore = new Semaphore(5);
        // Acquire 3 permits, should succeed without blocking
        semaphore.acquire(3);
        // After acquiring 3 permits, available permits should be 2
        int available = semaphore.availablePermits();
        if (available != 2) {
            throw new RuntimeException("Expected 2 permits available but found " + available);
        }
        // Release 3 permits back
        semaphore.release(3);
        if (semaphore.availablePermits() != 5) {
            throw new RuntimeException("Expected 5 permits available after release but found " + semaphore.availablePermits());
        }
    }

    static void testAcquireWithZeroPermits() throws Exception {
        final Semaphore semaphore = new Semaphore(1);
        // Acquire 0 permits should not block and not change permits
        semaphore.acquire(0);
        if (semaphore.availablePermits() != 1) {
            throw new RuntimeException("Expected 1 permit available after acquire(0) but found " + semaphore.availablePermits());
        }
    }

    static void testAcquireWithNegativePermits() {
        final Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquire(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative permits");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } catch (InterruptedException ie) {
            throw new RuntimeException("Unexpected InterruptedException", ie);
        }
    }
}
