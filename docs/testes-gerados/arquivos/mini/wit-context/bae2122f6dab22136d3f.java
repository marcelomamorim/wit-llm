/*
 * @test
 * @summary Test ReentrantLock.hasQueuedThread(Thread) for null argument and normal usage
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        // Test normal usage: no thread queued initially
        boolean result = lock.hasQueuedThread(Thread.currentThread());
        if (result) {
            throw new AssertionError("Expected no queued thread for current thread initially");
        }

        // Test null argument triggers NullPointerException as per current implementation
        try {
            lock.hasQueuedThread(null);
            throw new AssertionError("Expected NullPointerException when passing null to hasQueuedThread");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        System.out.println("ReentrantLockWitupTest passed");
    }
}
