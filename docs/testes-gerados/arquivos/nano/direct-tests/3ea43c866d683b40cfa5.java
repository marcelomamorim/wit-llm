/* @test    @summary Test Semaphore.acquire(int permits) for normal operation and exception handling    @run main SemaphoreWitupTest */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testAcquireWithValidPermits();
        testAcquireWithNegativePermits();
    }

    private static void testAcquireWithValidPermits() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        semaphore.acquire(1);
        // After acquiring 1 permit, available permits should be 0
        int permitsAvailable = semaphore.availablePermits();
        if (permitsAvailable != 0) {
            throw new AssertionError("Expected 0 permits available, but got " + permitsAvailable);
        }
    }

    private static void testAcquireWithNegativePermits() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquire(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative permits");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (InterruptedException e) {
            throw new AssertionError("Unexpected InterruptedException", e);
        }
    }
}
