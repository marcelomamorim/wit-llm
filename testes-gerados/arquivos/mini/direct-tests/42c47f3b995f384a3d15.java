/*
 * @test
 * @summary Test ReentrantReadWriteLock.lock() method basic behavior
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) throws Exception {
        testLockNonFair();
        testLockFair();
    }

    static void testLockNonFair() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        // Lock should be acquired without blocking
        lock.lock();
        // After locking, the write lock should be held by current thread
        if (!lock.isWriteLockedByCurrentThread()) {
            throw new AssertionError("Write lock not held by current thread after lock()");
        }
        lock.writeLock().unlock();
    }

    static void testLockFair() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        // Lock should be acquired without blocking
        lock.lock();
        if (!lock.isWriteLockedByCurrentThread()) {
            throw new AssertionError("Write lock not held by current thread after lock() in fair mode");
        }
        lock.writeLock().unlock();
    }
}
