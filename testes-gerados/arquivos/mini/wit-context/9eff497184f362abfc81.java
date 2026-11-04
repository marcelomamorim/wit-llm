/*
 * @test
 * @summary Test ReentrantLock.lock() method for expected behavior and exception
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {
    public static void main(String[] args) {
        testLockBasicFunctionality();
    }

    private static void testLockBasicFunctionality() {
        final ReentrantLock lock = new ReentrantLock();

        // Test that lock() acquires the lock without throwing
        lock.lock();
        try {
            // The current thread should hold the lock
            if (!lock.isHeldByCurrentThread()) {
                throw new AssertionError("Current thread should hold the lock after lock()");
            }

            // The hold count should be 1
            if (lock.getHoldCount() != 1) {
                throw new AssertionError("Hold count should be 1 after first lock()");
            }

            // Reentrant lock: lock again and check hold count increments
            lock.lock();
            if (lock.getHoldCount() != 2) {
                throw new AssertionError("Hold count should be 2 after second lock()");
            }

        } finally {
            // Unlock twice to release the lock fully
            lock.unlock();
            lock.unlock();
        }

        // After unlocking, the lock should not be held by current thread
        if (lock.isHeldByCurrentThread()) {
            throw new AssertionError("Lock should not be held by current thread after unlock()");
        }
    }
}
