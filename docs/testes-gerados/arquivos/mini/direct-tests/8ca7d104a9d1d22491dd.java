/*
 * @test
 * @summary Test java.util.concurrent.Semaphore.tryAcquire(int) method
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) throws Exception {
        testTryAcquirePositivePermits();
        testTryAcquireZeroPermits();
        testTryAcquireNegativePermits();
    }

    static void testTryAcquirePositivePermits() {
        Semaphore sem = new Semaphore(2);
        boolean acquired = sem.tryAcquire(1);
        if (!acquired) {
            throw new RuntimeException("tryAcquire(1) should succeed when permits are available");
        }
        // try to acquire more permits than available
        boolean acquiredTooMany = sem.tryAcquire(5);
        if (acquiredTooMany) {
            throw new RuntimeException("tryAcquire(5) should fail when permits are insufficient");
        }
    }

    static void testTryAcquireZeroPermits() {
        Semaphore sem = new Semaphore(1);
        boolean acquired = sem.tryAcquire(0);
        if (!acquired) {
            throw new RuntimeException("tryAcquire(0) should always succeed");
        }
    }

    static void testTryAcquireNegativePermits() {
        Semaphore sem = new Semaphore(1);
        try {
            sem.tryAcquire(-1);
            throw new RuntimeException("tryAcquire(-1) should throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
