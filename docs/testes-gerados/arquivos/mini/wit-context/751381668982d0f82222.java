/*
 * @test
 * @summary Test ReentrantReadWriteLock.hasQueuedThread(Thread) for null argument and normal usage
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        // Test hasQueuedThread with null argument - expect NullPointerException
        boolean npeThrown = false;
        try {
            lock.hasQueuedThread(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to hasQueuedThread");
        }

        // Test hasQueuedThread with current thread - should return false as no thread is queued
        boolean result = lock.hasQueuedThread(Thread.currentThread());
        if (result) {
            throw new AssertionError("Expected hasQueuedThread to return false when no thread is queued");
        }

        // Test hasQueuedThread with a new thread that is not started - should return false
        Thread t = new Thread();
        if (lock.hasQueuedThread(t)) {
            throw new AssertionError("Expected hasQueuedThread to return false for a new thread not queued");
        }
    }
}
