/* @test    @summary Test acquireUninterruptibly() method of Semaphore for uninterruptible acquisition behavior    @run main SemaphoreWitupTest */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) {
        testAcquireUninterruptiblyBlocksAndReleases();
        testAcquireUninterruptiblyMultiplePermits();
    }

    private static void testAcquireUninterruptiblyBlocksAndReleases() {
        final Semaphore semaphore = new Semaphore(0);
        Thread t = new Thread(() -> {
            semaphore.acquireUninterruptibly();
        });
        t.start();
        try {
            Thread.sleep(100); // Ensure thread is blocked on acquireUninterruptibly
        } catch (InterruptedException e) {
            // Ignored for test
        }
        assert t.isAlive() : "Thread should be blocked on acquireUninterruptibly()";
        semaphore.release();
        try {
            t.join(1000);
        } catch (InterruptedException e) {
            // Ignored for test
        }
        assert !t.isAlive() : "Thread should have completed after release()";
    }

    private static void testAcquireUninterruptiblyMultiplePermits() {
        Semaphore semaphore = new Semaphore(2);
        semaphore.acquireUninterruptibly();
        semaphore.acquireUninterruptibly();
        assert semaphore.availablePermits() == 0 : "Permits should be 0 after acquiring all permits";
        Thread t = new Thread(() -> {
            semaphore.acquireUninterruptibly();
        });
        t.start();
        try {
            Thread.sleep(100); // Ensure thread is blocked
        } catch (InterruptedException e) {
            // Ignored for test
        }
        assert t.isAlive() : "Thread should be blocked when permits are exhausted";
        semaphore.release();
        try {
            t.join(1000);
        } catch (InterruptedException e) {
            // Ignored for test
        }
        assert !t.isAlive() : "Thread should have acquired permit after release()";
        assert semaphore.availablePermits() == 1 : "Permits should be 1 after release()";
    }
}
