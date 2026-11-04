/* @test    @summary Test ReentrantLock lock method for mutual exclusion    @run main ReentrantLockWitupTest */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {

    public static void main(String[] args) {
        testLockBlocksOtherThreads();
        testLockAllowsReentrant();
    }

    private static void testLockBlocksOtherThreads() {
        final ReentrantLock lock = new ReentrantLock();
        final boolean[] threadAcquiredLock = {false};
        Thread t = new Thread(() -> {
            lock.lock();
            try {
                threadAcquiredLock[0] = true;
                // Hold the lock for a short time
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // ignore
                }
            } finally {
                lock.unlock();
            }
        });
        t.start();
        // Wait for thread to acquire lock
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // ignore
        }
        // Main thread attempts to acquire lock; should block until thread releases
        long startTime = System.currentTimeMillis();
        lock.lock();
        long duration = System.currentTimeMillis() - startTime;
        try {
            if (!threadAcquiredLock[0]) {
                throw new AssertionError("Thread did not acquire lock as expected");
            }
            if (duration < 10) {
                throw new AssertionError("lock() did not block as expected");
            }
        } finally {
            lock.unlock();
        }
        try {
            t.join();
        } catch (InterruptedException e) {
            // ignore
        }
    }

    private static void testLockAllowsReentrant() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            // Reentrant lock: lock() should succeed immediately
            lock.lock();
            try {
                if (lock.getHoldCount() != 2) {
                    throw new AssertionError("Hold count should be 2 after reentrant lock");
                }
            } finally {
                lock.unlock();
            }
        } finally {
            lock.unlock();
            if (lock.isHeldByCurrentThread()) {
                throw new AssertionError("Lock should be released after unlocks");
            }
        }
    }
}
