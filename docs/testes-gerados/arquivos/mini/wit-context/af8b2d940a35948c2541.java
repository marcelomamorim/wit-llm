/*
 * @test
 * @summary Test java.util.concurrent.Semaphore.release(int) for argument validation and behavior
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) {
        testReleaseNegativePermits();
        testReleaseZeroPermits();
        testReleasePositivePermits();
    }

    private static void testReleaseNegativePermits() {
        Semaphore sem = new Semaphore(1);
        try {
            sem.release(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative permits");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        }
    }

    private static void testReleaseZeroPermits() {
        Semaphore sem = new Semaphore(1);
        // release(0) should not throw IllegalArgumentException
        // but behavior depends on sync.releaseShared(0) which may throw UnsupportedOperationException
        try {
            sem.release(0);
        } catch (UnsupportedOperationException uoe) {
            // Acceptable according to WIT expath
        }
    }

    private static void testReleasePositivePermits() {
        Semaphore sem = new Semaphore(1);
        // release with positive permits should succeed or throw UnsupportedOperationException
        try {
            sem.release(2);
        } catch (UnsupportedOperationException uoe) {
            // Acceptable according to WIT expath
        }
    }
}
