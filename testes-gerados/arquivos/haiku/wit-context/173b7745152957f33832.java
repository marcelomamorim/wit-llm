/*
 * @test
 * @summary Checks ReentrantReadWriteLock read-lock unlock behavior for unmatched and matched unlock calls
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        testUnlockWithoutLockThrowsIllegalMonitorState();
        testUnlockAfterLockReleasesReadLock();
        testUnlockAfterLockReleasesReadLockFair();
        testReentrantReadUnlockCountsDown();
    }

    static void testUnlockWithoutLockThrowsIllegalMonitorState() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        boolean thrown = false;
        try {
            lock.readLock().unlock();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalMonitorStateException on unmatched read unlock");
        }
    }

    static void testUnlockAfterLockReleasesReadLock() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        lock.readLock().unlock();
        if (!lock.writeLock().tryLock()) {
            throw new RuntimeException("Write lock should be available after read unlock");
        }
        lock.writeLock().unlock();
    }

    static void testUnlockAfterLockReleasesReadLockFair() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        lock.readLock().lock();
        lock.readLock().unlock();
        if (!lock.writeLock().tryLock()) {
            throw new RuntimeException("Fair write lock should be available after read unlock");
        }
        lock.writeLock().unlock();
    }

    static void testReentrantReadUnlockCountsDown() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        lock.readLock().lock();
        lock.readLock().unlock();
        if (lock.writeLock().tryLock()) {
            lock.writeLock().unlock();
            throw new RuntimeException("Write lock should still be blocked while one read hold remains");
        }
        lock.readLock().unlock();
        if (!lock.writeLock().tryLock()) {
            throw new RuntimeException("Write lock should be available after all read holds are released");
        }
        lock.writeLock().unlock();

        boolean thrown = false;
        try {
            lock.readLock().unlock();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalMonitorStateException on extra read unlock");
        }
    }
}
