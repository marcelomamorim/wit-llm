/*
 * @test
 * @summary Test ReentrantReadWriteLock.lock() method basic behavior
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) throws Exception {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        // Test that lock() acquires the write lock without throwing
        lock.lock();
        try {
            // Reentrant acquisition: lock again in same thread
            lock.lock();
        } finally {
            // Release twice to match two lock() calls
            lock.writeLock().unlock();
            lock.writeLock().unlock();
        }

        // Test fair lock construction and lock acquisition
        ReentrantReadWriteLock fairLock = new ReentrantReadWriteLock(true);
        fairLock.lock();
        try {
            fairLock.lock();
        } finally {
            fairLock.writeLock().unlock();
            fairLock.writeLock().unlock();
        }

        // Test that lock() blocks if already held by another thread
        final ReentrantReadWriteLock blockingLock = new ReentrantReadWriteLock();
        blockingLock.lock();
        Thread t = new Thread(new Runnable() {
            public void run() {
                // This will block until main thread unlocks
                blockingLock.lock();
                blockingLock.writeLock().unlock();
            }
        });
        t.start();
        Thread.sleep(100); // Give thread time to block
        blockingLock.writeLock().unlock();
        t.join();
    }
}
