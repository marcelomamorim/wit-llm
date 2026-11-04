/*
 * @test
 * @summary Verifies StampedLock.unlockWrite throws IllegalMonitorStateException for stamps that do not match the write-locked state and releases the write lock for the matching stamp
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testWrongStampWhileWriteLockedThrows();
        testUnlockWithStaleStampThrows();
        testReadStampPassedToUnlockWriteThrows();
        testMatchingStampReleasesWriteLock();
        testOptimisticReadValidAfterUnlockWrite();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testWrongStampWhileWriteLockedThrows() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        boolean threw = false;
        try {
            lock.unlockWrite(stamp + 1);
        } catch (IllegalMonitorStateException e) {
            threw = true;
        }
        check(threw, "expected IllegalMonitorStateException for mismatched stamp");
        check(lock.isWriteLocked(), "write lock must remain held after failed unlock");
        lock.unlockWrite(stamp);
        check(!lock.isWriteLocked(), "write lock must be released by matching stamp");
    }

    static void testUnlockWithStaleStampThrows() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        lock.unlockWrite(stamp);
        boolean threw = false;
        try {
            lock.unlockWrite(stamp);
        } catch (IllegalMonitorStateException e) {
            threw = true;
        }
        check(threw, "expected IllegalMonitorStateException for stale stamp");
        check(!lock.isWriteLocked(), "lock must not be write-locked");
    }

    static void testReadStampPassedToUnlockWriteThrows() {
        StampedLock lock = new StampedLock();
        long readStamp = lock.readLock();
        boolean threw = false;
        try {
            lock.unlockWrite(readStamp);
        } catch (IllegalMonitorStateException e) {
            threw = true;
        }
        check(threw, "expected IllegalMonitorStateException when read stamp is used");
        check(lock.isReadLocked(), "read lock must remain held after failed unlockWrite");
        lock.unlockRead(readStamp);
        check(!lock.isReadLocked(), "read lock must be released by unlockRead");
    }

    static void testMatchingStampReleasesWriteLock() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        lock.unlockWrite(stamp);
        check(!lock.isWriteLocked(), "write lock must be free after unlockWrite");
        long again = lock.tryWriteLock();
        check(again != 0L, "tryWriteLock must succeed after unlockWrite");
        lock.unlockWrite(again);
    }

    static void testOptimisticReadValidAfterUnlockWrite() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        lock.unlockWrite(stamp);
        long optimistic = lock.tryOptimisticRead();
        check(optimistic != 0L, "optimistic read must succeed when no writer holds the lock");
        check(lock.validate(optimistic), "optimistic stamp must validate without intervening writers");
    }
}
