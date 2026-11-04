/*
 * @test
 * @summary Verify StampedLock.unlock(long) releases write and read stamps and rejects stale stamps
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testUnlockWriteStampReleasesLock();
        testUnlockReadStampReleasesLock();
        testUnlockWriteStampTwiceThrows();
        testUnlockReadStampTwiceThrows();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testUnlockWriteStampReleasesLock() {
        StampedLock lock = new StampedLock();
        long ws = lock.writeLock();
        check(ws != 0L, "writeLock must return non-zero stamp");
        check(lock.isWriteLocked(), "lock must be write-locked before unlock");

        lock.unlock(ws);

        check(!lock.isWriteLocked(), "unlock(writeStamp) must release write lock");
        check(!lock.validate(ws), "write stamp must be invalid after unlock");

        long optimistic = lock.tryOptimisticRead();
        check(optimistic != 0L, "optimistic read must succeed after write unlock");
        check(lock.validate(optimistic), "optimistic stamp must validate after write unlock");
    }

    static void testUnlockReadStampReleasesLock() {
        StampedLock lock = new StampedLock();
        long rs = lock.readLock();
        check(rs != 0L, "readLock must return non-zero stamp");
        check(lock.isReadLocked(), "lock must be read-locked before unlock");

        lock.unlock(rs);

        check(!lock.isReadLocked(), "unlock(readStamp) must release read lock");
        long ws = lock.tryWriteLock();
        check(ws != 0L, "write lock must be available after read unlock");
        lock.unlock(ws);
    }

    static void testUnlockWriteStampTwiceThrows() {
        StampedLock lock = new StampedLock();
        long ws = lock.writeLock();
        lock.unlock(ws);
        boolean thrown = false;
        try {
            lock.unlock(ws);
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "second unlock of a released write stamp must throw IllegalMonitorStateException");
    }

    static void testUnlockReadStampTwiceThrows() {
        StampedLock lock = new StampedLock();
        long rs = lock.readLock();
        lock.unlock(rs);
        boolean thrown = false;
        try {
            lock.unlock(rs);
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        check(thrown, "second unlock of a released read stamp must throw IllegalMonitorStateException");
    }
}
