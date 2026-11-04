/*
 * @test
 * @summary Test ReentrantReadWriteLock.unlock() method behavior
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) throws Exception {
        testUnlockReadLock();
        testUnlockWriteLock();
    }

    private static void testUnlockReadLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        readLock.lock();
        try {
            // Unlock via ReentrantReadWriteLock.unlock() indirectly by releasing shared lock
            lock.unlock();
        } catch (IllegalMonitorStateException e) {
            throw new RuntimeException("unlock() failed on read lock held by current thread", e);
        }

        // After unlock, try to lock again to verify lock is free
        readLock.lock();
        readLock.unlock();
    }

    private static void testUnlockWriteLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        writeLock.lock();
        try {
            // Unlock via ReentrantReadWriteLock.unlock() indirectly by releasing shared lock
            lock.unlock();
        } catch (IllegalMonitorStateException e) {
            throw new RuntimeException("unlock() failed on write lock held by current thread", e);
        }

        // After unlock, try to lock again to verify lock is free
        writeLock.lock();
        writeLock.unlock();
    }
}
