/*
 * @test
 * @summary Test for Semaphore.tryAcquire(int permits)
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
            boolean result = semaphore.tryAcquire(-1);
            // Expecting IllegalArgumentException, so if no exception, test fails
            System.err.println("Expected IllegalArgumentException for permits < 0, but got result: " + result);
        } catch (IllegalArgumentException e) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    private static void testTryAcquireWithZeroPermits() {
        Semaphore semaphore = new Semaphore(1);
        boolean result = semaphore.tryAcquire(0);
        // Should succeed immediately, returning true
        if (result != true) {
            System.err.println("Expected true for tryAcquire(0), but got: " + result);
        }
    }

    private static void testTryAcquireWithPositivePermits() {
        Semaphore semaphore = new Semaphore(1);
        boolean firstAttempt = semaphore.tryAcquire(1);
        if (!firstAttempt) {
            System.err.println("Failed to acquire permit when available.");
        }
        boolean secondAttempt = semaphore.tryAcquire(1);
        // Since permit was acquired, second attempt should fail
        if (secondAttempt) {
            System.err.println("Unexpectedly acquired permit when none should be available.");
        }
        // Release permit and try again
        semaphore.release();
        boolean thirdAttempt = semaphore.tryAcquire(1);
        if (!thirdAttempt) {
            System.err.println("Failed to acquire permit after release.");
        }
    }
}
