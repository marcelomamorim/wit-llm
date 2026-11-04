/*
 * @test
 * @summary Test java.util.concurrent.Semaphore.acquireUninterruptibly(int) method
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) {
        testAcquireUninterruptiblyValidPermits();
        testAcquireUninterruptiblyZeroPermits();
        testAcquireUninterruptiblyNegativePermits();
    }

    private static void testAcquireUninterruptiblyValidPermits() {
        Semaphore semaphore = new Semaphore(5);
        // Acquire 3 permits uninterruptibly
        semaphore.acquireUninterruptibly(3);
        // After acquiring 3 permits, available permits should be 2
        int available = semaphore.availablePermits();
        if (available != 2) {
            throw new AssertionError("Expected 2 permits available, but got " + available);
        }
    }

    private static void testAcquireUninterruptiblyZeroPermits() {
        Semaphore semaphore = new Semaphore(1);
        // Acquiring zero permits should not block or change permits
        semaphore.acquireUninterruptibly(0);
        int available = semaphore.availablePermits();
        if (available != 1) {
            throw new AssertionError("Expected 1 permit available after acquiring 0 permits, but got " + available);
        }
    }

    private static void testAcquireUninterruptiblyNegativePermits() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquireUninterruptibly(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative permits");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
