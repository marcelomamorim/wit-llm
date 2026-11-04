/*
 * @test
 * @summary Tests observable behavior of StampedLock.unlock(long) for write and read stamps, including IllegalMonitorStateException on invalid or stale write stamps
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testUnlockSameWriteStampTwiceThrows();
        testUnlockStaleWriteStampThrows();
        testUnlockReadStampReleasesReadLock();
        testUnlockWriteStampReleasesWriteLock();
        System.out.println("PASS");
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testUnlockSameWriteStampTwiceThrows() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        lock.unlock(stamp);
        try {
            lock.unlock(stamp);
            fail("expected IllegalMonitorStateException on second unlock of same write stamp");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }
        if (lock.isWriteLocked()) {
            fail("lock must not be write-locked after release");
        }
    }

    static void testUnlockStaleWriteStampThrows() {
        StampedLock lock = new StampedLock();
        long first = lock.writeLock();
        lock.unlock(first);
        long second = lock.writeLock();
        try {
            lock.unlock(first);
            fail("expected IllegalMonitorStateException for stale write stamp");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }
        if (!lock.isWriteLocked()) {
            fail("stale unlock must not release the current write lock");
        }
        lock.unlock(second);
        if (lock.isWriteLocked()) {
            fail("current write stamp must release the lock");
        }
    }

    static void testUnlockReadStampReleasesReadLock() {
        StampedLock lock = new StampedLock();
        long stamp = lock.readLock();
        if (lock.getReadLockCount() != 1) {
            fail("expected one read lock after readLock()");
        }
        lock.unlock(stamp);
        if (lock.getReadLockCount() != 0) {
            fail("expected zero read locks after unlock(readStamp)");
        }
        long probe = lock.tryWriteLock();
        if (probe == 0L) {
            fail("lock should be available for write after read unlock");
        }
        lock.unlock(probe);
    }

    static void testUnlockWriteStampReleasesWriteLock() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        if (!lock.isWriteLocked()) {
            fail("expected write-locked state after writeLock()");
        }
        lock.unlock(stamp);
        if (lock.isWriteLocked()) {
            fail("expected unlocked state after unlock(writeStamp)");
        }
        long probe = lock.tryReadLock();
        if (probe == 0L) {
            fail("read lock should be available after write unlock");
        }
        lock.unlock(probe);
    }
}
