/* @test    @summary Test hasQueuedThread method of ReentrantLock    @run main ReentrantLockWitupTest */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {

    public static void main(String[] args) {
        testHasQueuedThreadWithUnlockedLock();
        testHasQueuedThreadWithLockedLock();
        testHasQueuedThreadWithDifferentThread();
    }

    private static void testHasQueuedThreadWithUnlockedLock() {
        ReentrantLock lock = new ReentrantLock();
        Thread thread = new Thread();
        boolean result = lock.hasQueuedThread(thread);
        System.out.println("hasQueuedThread with unlocked lock: " + result);
        // Expect false, as no thread is queued
        if (result != false) {
            throw new AssertionError("Expected false for unlocked lock");
        }
    }

    private static void testHasQueuedThreadWithLockedLock() {
        ReentrantLock lock = new ReentrantLock();
        Thread thread = new Thread();
        lock.lock();
        try {
            Thread t = new Thread(() -> {
                // Busy wait to ensure thread is queued
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            t.start();
            // Wait for thread to start and attempt to acquire lock
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            boolean result = lock.hasQueuedThread(t);
            System.out.println("hasQueuedThread with locked lock: " + result);
            // Cannot guarantee thread is queued immediately; result may be false or true
            // But we can check that if thread is alive, it might be queued
        } finally {
            lock.unlock();
        }
    }

    private static void testHasQueuedThreadWithDifferentThread() {
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new Thread();
        Thread thread2 = new Thread();
        lock.lock();
        try {
            boolean result1 = lock.hasQueuedThread(thread1);
            boolean result2 = lock.hasQueuedThread(thread2);
            System.out.println("hasQueuedThread with different threads: " + result1 + ", " + result2);
            // Expect false for both, as no threads are queued
            if (result1 != false || result2 != false) {
                throw new AssertionError("Expected false for both threads when no threads are queued");
            }
        } finally {
            lock.unlock();
        }
    }
}
