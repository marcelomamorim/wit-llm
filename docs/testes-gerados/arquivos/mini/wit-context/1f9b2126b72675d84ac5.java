/*
 * @test
 * @summary Test java.util.concurrent.Semaphore.acquireUninterruptibly(int) for argument validation and basic behavior
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) {
        testAcquireUninterruptiblyNegativePermits();
        testAcquireUninterruptiblyNonNegativePermits();
    }

    private static void testAcquireUninterruptiblyNegativePermits() {
        Semaphore sem = new Semaphore(1);
        try {
            sem.acquireUninterruptibly(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative permits");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testAcquireUninterruptiblyNonNegativePermits() {
        Semaphore sem = new Semaphore(1);
        // The method should not throw IllegalArgumentException for zero or positive permits
        // It may throw UnsupportedOperationException if sync.acquireShared is unsupported
        try {
            sem.acquireUninterruptibly(0);
        } catch (UnsupportedOperationException uoe) {
            // Acceptable per WIT expath
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for zero permits", e);
        }

        try {
            sem.acquireUninterruptibly(1);
        } catch (UnsupportedOperationException uoe) {
            // Acceptable per WIT expath
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for positive permits", e);
        }
    }
}
