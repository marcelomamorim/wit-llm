/* @test    @summary Test tryAcquire with valid and invalid permits    @run main SemaphoreWitupTest */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) {
        testTryAcquirePositivePermits();
        testTryAcquireZeroPermits();
        testTryAcquireNegativePermits();
    }

    static void testTryAcquirePositivePermits() {
        Semaphore semaphore = new Semaphore(1);
        boolean result = semaphore.tryAcquire(1);
        if (!result) {
            throw new AssertionError("Expected tryAcquire(1) to return true when permits are available");
        }
    }

    static void testTryAcquireZeroPermits() {
        Semaphore semaphore = new Semaphore(1);
        boolean result = semaphore.tryAcquire(0);
        if (result) {
            throw new AssertionError("Expected tryAcquire(0) to return false as permits must be positive");
        }
    }

    static void testTryAcquireNegativePermits() {
        Semaphore semaphore = new Semaphore(1);
        boolean exceptionThrown = false;
        try {
            semaphore.tryAcquire(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected tryAcquire(-1) to throw IllegalArgumentException");
        }
    }
}
