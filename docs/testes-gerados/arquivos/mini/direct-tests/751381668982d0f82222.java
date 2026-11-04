/*
 * @test
 * @summary Test ReentrantReadWriteLock.hasQueuedThread(Thread) method
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) throws Exception {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Thread current = Thread.currentThread();

        // Initially, no thread is queued
        if (lock.hasQueuedThread(current)) {
            throw new Error("Expected no queued thread initially");
        }

        // Start a thread that acquires the write lock and holds it
        final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        Thread t = new Thread(new Runnable() {
            public void run() {
                writeLock.lock();
                try {
                    // Hold the lock for a short time
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {}
                } finally {
                    writeLock.unlock();
                }
            }
        });
        t.start();

        // Wait for the thread to acquire the lock
        Thread.sleep(100);

        // Now main thread tries to acquire the write lock, will block and be queued
        Thread waiter = new Thread(new Runnable() {
            public void run() {
                writeLock.lock();
                writeLock.unlock();
            }
        });
        waiter.start();

        // Wait a bit to ensure waiter is queued
        Thread.sleep(100);

        // The waiter thread should be queued
        if (!lock.hasQueuedThread(waiter)) {
            throw new Error("Expected waiter thread to be queued");
        }

        // The current thread is not queued
        if (lock.hasQueuedThread(Thread.currentThread())) {
            throw new Error("Current thread should not be queued");
        }

        // Wait for threads to finish
        t.join();
        waiter.join();

        // After all done, no thread should be queued
        if (lock.hasQueuedThread(waiter)) {
            throw new Error("No thread should be queued after unlock");
        }

        System.out.println("Test passed");
    }
}
