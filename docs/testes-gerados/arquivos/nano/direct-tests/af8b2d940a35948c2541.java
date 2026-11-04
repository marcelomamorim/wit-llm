/* @test    @summary Test Semaphore.release(int permits) with valid permits    @run main SemaphoreWitupTest */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) {
        testReleaseWithPositivePermits();
        testReleaseWithZeroPermits();
        testReleaseWithMultiplePermits();
        testReleaseWithNegativePermitsThrows();
    }

    static void testReleaseWithPositivePermits() {
        Semaphore semaphore = new Semaphore(0);
        semaphore.release(1);
        if (semaphore.availablePermits() != 1) {
            throw new AssertionError("Expected permits to be 1 after release(1)");
        }
    }

    static void testReleaseWithZeroPermits() {
        Semaphore semaphore = new Semaphore(0);
        semaphore.release(0);
        if (semaphore.availablePermits() != 0) {
            throw new AssertionError("Expected permits to remain 0 after release(0)");
        }
    }

    static void testReleaseWithMultiplePermits() {
        Semaphore semaphore = new Semaphore(2);
        semaphore.release(3);
        if (semaphore.availablePermits() != 5) {
            throw new AssertionError("Expected permits to be 5 after release(3) on initial 2");
        }
    }

    static void testReleaseWithNegativePermitsThrows() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.release(-1);
            throw new AssertionError("Expected IllegalArgumentException for release(-1)");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
