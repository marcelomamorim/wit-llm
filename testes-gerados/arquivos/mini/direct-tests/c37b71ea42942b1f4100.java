/*
 * @test
 * @summary Test java.util.concurrent.Semaphore.acquireUninterruptibly() basic behavior
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        // Acquire uninterruptibly should succeed immediately when permit is available
        semaphore.acquireUninterruptibly();
        // After acquiring, available permits should be zero
        if (semaphore.availablePermits() != 0) {
            throw new AssertionError("Expected 0 permits after acquireUninterruptibly, found: " + semaphore.availablePermits());
        }
        // Release permit
        semaphore.release();
        // After release, permits should be 1
        if (semaphore.availablePermits() != 1) {
            throw new AssertionError("Expected 1 permit after release, found: " + semaphore.availablePermits());
        }
        // Acquire uninterruptibly twice, second call will block if no permits
        // but since permits are 1, first call acquires it, second call will block
        // To test blocking behavior, create a thread that acquires uninterruptibly
        final Semaphore sem = new Semaphore(1);
        sem.acquireUninterruptibly(); // acquire the only permit

        Thread t = new Thread(new Runnable() {
            public void run() {
                // This will block until main thread releases
                sem.acquireUninterruptibly();
                // After acquiring, permits should be 0
                if (sem.availablePermits() != 0) {
                    throw new AssertionError("Expected 0 permits after second acquireUninterruptibly, found: " + sem.availablePermits());
                }
                sem.release();
            }
        });
        t.start();

        try {
            // Sleep briefly to ensure thread is blocked on acquireUninterruptibly
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new AssertionError("Unexpected interruption", e);
        }

        // Release permit to unblock thread
        sem.release();

        try {
            t.join(1000);
        } catch (InterruptedException e) {
            throw new AssertionError("Unexpected interruption", e);
        }

        if (t.isAlive()) {
            throw new AssertionError("Thread did not complete acquireUninterruptibly as expected");
        }
    }
}
