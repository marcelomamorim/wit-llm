/*
 * @test
 * @summary Test for Semaphore.acquireUninterruptibly()
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) {
        testAcquireUninterruptiblyBasic();
        testAcquireUninterruptiblyWithPermit();
        testAcquireUninterruptiblyWithZeroPermits();
    }

    private static void testAcquireUninterruptiblyBasic() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquireUninterruptibly();
            if (semaphore.availablePermits() != 0) {
                throw new AssertionError("Permits should be 0 after acquireUninterruptibly()");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testAcquireUninterruptiblyBasic", e);
        }
    }

    private static void testAcquireUninterruptiblyWithPermit() {
        Semaphore semaphore = new Semaphore(2);
        try {
            semaphore.acquireUninterruptibly();
            semaphore.acquireUninterruptibly();
            if (semaphore.availablePermits() != 0) {
                throw new AssertionError("Permits should be 0 after two acquireUninterruptibly()");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testAcquireUninterruptiblyWithPermit", e);
        }
    }

    private static void testAcquireUninterruptiblyWithZeroPermits() {
        Semaphore semaphore = new Semaphore(0);
        Thread t = new Thread(() -> {
            try {
                semaphore.acquireUninterruptibly();
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception in thread", e);
            }
        });
        t.start();
        try {
            Thread.sleep(50); // ensure thread is waiting
            if (t.getState() != Thread.State.WAITING && t.getState() != Thread.State.BLOCKED) {
                throw new AssertionError("Thread should be waiting or blocked on acquireUninterruptibly()");
            }
            semaphore.release();
            t.join(100);
            if (t.isAlive()) {
                throw new AssertionError("Thread did not complete after release()");
            }
        } catch (InterruptedException e) {
            throw new AssertionError("Test interrupted", e);
        }
    }
}
