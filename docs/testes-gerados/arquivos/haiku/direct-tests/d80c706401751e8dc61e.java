/*
 * @test
 * @summary Checks WriteLock.unlock() observable behavior through the public ReentrantReadWriteLock API
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        testUnlockWithoutLockThrowsNonFair();
        testUnlockWithoutLockThrowsFair();
        testUnlockReleasesWriteLock();
        testReentrantUnlockDecrementsHoldCount();
        testUnlockOnFairLockReleasesWriteLock();
    }

    static void testUnlockWithoutLockThrowsNonFair() {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        boolean thrown = false;
        try {
            rwl.writeLock().unlock();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "unlock without holding write lock must throw IllegalMonitorStateException");
        check(!rwl.isWriteLocked(), "lock state must remain unlocked after failed unlock");
    }

    static void testUnlockWithoutLockThrowsFair() {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
        boolean thrown = false;
        try {
            rwl.writeLock().unlock();
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "fair lock: unlock without holding write lock must throw IllegalMonitorStateException");
        check(!rwl.isWriteLocked(), "fair lock: lock state must remain unlocked after failed unlock");
    }

    static void testUnlockReleasesWriteLock() {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        rwl.writeLock().lock();
        check(rwl.isWriteLocked(), "write lock must be held after lock()");
        check(rwl.isWriteLockedByCurrentThread(), "write lock must be owned by current thread");
        rwl.writeLock().unlock();
        check(!rwl.isWriteLocked(), "write lock must be released after unlock()");
        check(rwl.getWriteHoldCount() == 0, "write hold count must be 0 after unlock()");
    }

    static void testReentrantUnlockDecrementsHoldCount() {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        rwl.writeLock().lock();
        rwl.writeLock().lock();
        check(rwl.getWriteHoldCount() == 2, "hold count must be 2 after two lock() calls");
        rwl.writeLock().unlock();
        check(rwl.getWriteHoldCount() == 1, "hold count must be 1 after one unlock()");
        check(rwl.isWriteLocked(), "write lock must still be held after partial unlock");
        rwl.writeLock().unlock();
        check(rwl.getWriteHoldCount() == 0, "hold count must be 0 after second unlock()");
        check(!rwl.isWriteLocked(), "write lock must be free after final unlock()");
    }

    static void testUnlockOnFairLockReleasesWriteLock() {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
        rwl.writeLock().lock();
        check(rwl.isWriteLockedByCurrentThread(), "fair lock: write lock must be owned after lock()");
        rwl.writeLock().unlock();
        check(!rwl.isWriteLocked(), "fair lock: write lock must be released after unlock()");
        rwl.writeLock().lock();
        check(rwl.isWriteLocked(), "fair lock: write lock must be reacquirable after unlock()");
        rwl.writeLock().unlock();
        check(!rwl.isWriteLocked(), "fair lock: final state must be unlocked");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
