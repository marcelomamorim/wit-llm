/*
 * @test
 * @summary Test ReentrantReadWriteLock.unlock() behavior including exception path
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) {
        testUnlockThrowsUnsupportedOperationException();
    }

    private static void testUnlockThrowsUnsupportedOperationException() {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        // Acquire read lock to enable unlock call
        readLock.lock();
        try {
            // The unlock method of ReentrantReadWriteLock calls sync.releaseShared(1),
            // which in ReadLock.unlock() path throws UnsupportedOperationException.
            // We test that calling unlock on ReadLock throws this exception.
            try {
                readLock.unlock();
                throw new AssertionError("Expected UnsupportedOperationException not thrown");
            } catch (UnsupportedOperationException expected) {
                // Expected exception caught, test passes
            }
        } finally {
            // Ensure lock is released if still held
            if (lock.getReadHoldCount() > 0) {
                readLock.unlock();
            }
        }
    }
}
