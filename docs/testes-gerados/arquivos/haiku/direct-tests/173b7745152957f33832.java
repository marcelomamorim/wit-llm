/*
 * @test
 * @summary Verify ReentrantReadWriteLock.ReadLock.unlock releases read holds and rejects unmatched unlocks
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        testSingleReadUnlockReleasesReadLock(false);
        testSingleReadUnlockReleasesReadLock(true);
        testReentrantReadUnlockKeepsLockUntilLastRelease(false);
        testReentrantReadUnlockKeepsLockUntilLastRelease(true);
        testUnmatchedReadUnlockThrowsAndLockStaysUsable(false);
        testUnmatchedReadUnlockThrowsAndLockStaysUsable(true);
    }

    static void testSingleReadUnlockReleasesReadLock(boolean fair) {
        ReentrantReadWriteLock rw = new ReentrantReadWriteLock(fair);
        rw.readLock().lock();
        rw.readLock().unlock();
        if (!rw.writeLock().tryLock()) {
            throw new RuntimeException("write lock should be available after single read unlock");
        }
        rw.writeLock().unlock();
    }

    static void testReentrantReadUnlockKeepsLockUntilLastRelease(boolean fair) {
        ReentrantReadWriteLock rw = new ReentrantReadWriteLock(fair);
        rw.readLock().lock();
        rw.readLock().lock();
        rw.readLock().unlock();
        if (rw.writeLock().tryLock()) {
            rw.writeLock().unlock();
            throw new RuntimeException("write lock must not be available while one read hold remains");
        }
        rw.readLock().unlock();
        if (!rw.writeLock().tryLock()) {
            throw new RuntimeException("write lock should be available after last read unlock");
        }
        rw.writeLock().unlock();
    }

    static void testUnmatchedReadUnlockThrowsAndLockStaysUsable(boolean fair) {
        ReentrantReadWriteLock rw = new ReentrantReadWriteLock(fair);
        boolean thrown = false;
        try {
            rw.readLock().unlock();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("unmatched read unlock should throw IllegalMonitorStateException");
        }
        if (!rw.writeLock().tryLock()) {
            throw new RuntimeException("lock should remain usable after unmatched read unlock");
        }
        rw.writeLock().unlock();
    }
}
