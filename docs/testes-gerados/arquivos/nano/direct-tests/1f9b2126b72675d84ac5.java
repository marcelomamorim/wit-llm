/* @test    @summary Test acquireUninterruptibly with valid permits    @run main SemaphoreWitupTest */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) {
        testAcquireUninterruptiblyWithPositivePermits();
        testAcquireUninterruptiblyWithZeroPermits();
        testAcquireUninterruptiblyWithNegativePermits();
    }

    static void testAcquireUninterruptiblyWithPositivePermits() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquireUninterruptibly(1);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for positive permits", e);
        }
    }

    static void testAcquireUninterruptiblyWithZeroPermits() {
        Semaphore semaphore = new Semaphore(0);
        try {
            semaphore.acquireUninterruptibly(0);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero permits", e);
        }
    }

    static void testAcquireUninterruptiblyWithNegativePermits() {
        Semaphore semaphore = new Semaphore(1);
        boolean exceptionThrown = false;
        try {
            semaphore.acquireUninterruptibly(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative permits");
        }
    }
}
