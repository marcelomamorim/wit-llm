/*
 * @test
 * @summary Test ReentrantLock.hasQueuedThread(Thread) method
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {
    public static void main(String[] args) throws Exception {
        ReentrantLock lock = new ReentrantLock();
        Thread current = Thread.currentThread();

        // Initially, no thread is queued
        if (lock.hasQueuedThread(current)) {
            throw new Error("Expected no queued thread initially");
        }

        // Lock the lock in current thread
        lock.lock();
        try {
            // Spawn a thread that will try to acquire the lock and block
            final ReentrantLock lockRef = lock;
            Thread t = new Thread(new Runnable() {
                public void run() {
                    lockRef.lock();
                    try {
                        // do nothing
                    } finally {
                        lockRef.unlock();
                    }
                }
            });
            t.start();

            // Wait a bit to ensure thread t attempts to lock and blocks
            Thread.sleep(100);

            // Now the thread t should be queued
            if (!lock.hasQueuedThread(t)) {
                throw new Error("Expected thread t to be queued");
            }

            // The current thread is not queued
            if (lock.hasQueuedThread(current)) {
                throw new Error("Current thread should not be queued");
            }

            // Also test with a thread that never tried to lock
            Thread dummy = new Thread();
            if (lock.hasQueuedThread(dummy)) {
                throw new Error("Dummy thread should not be queued");
            }

            // Wait for thread t to finish
            t.interrupt(); // interrupt in case it is stuck
            t.join(1000);

        } finally {
            lock.unlock();
        }
    }
}
