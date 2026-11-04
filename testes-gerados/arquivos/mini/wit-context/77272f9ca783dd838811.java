/*
 * @test
 * @summary Test StampedLock.unlockWrite(long) for correct exception and normal behavior
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {
    public static void main(String[] args) {
        testUnlockWriteThrowsOnInvalidStamp();
        testUnlockWriteSucceedsOnValidStamp();
    }

    private static void testUnlockWriteThrowsOnInvalidStamp() {
        StampedLock lock = new StampedLock();
        long invalidStamp = 0L; // zero is never a valid write stamp
        boolean thrown = false;
        try {
            lock.unlockWrite(invalidStamp);
        } catch (IllegalMonitorStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalMonitorStateException for invalid stamp");
        }
    }

    private static void testUnlockWriteSucceedsOnValidStamp() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        try {
            lock.unlockWrite(stamp);
        } catch (IllegalMonitorStateException e) {
            throw new AssertionError("Did not expect IllegalMonitorStateException for valid stamp");
        }
        // After unlock, try to acquire write lock again to confirm lock is released
        long stamp2 = lock.tryWriteLock();
        if (stamp2 == 0L) {
            throw new AssertionError("Lock was not properly released after unlockWrite");
        }
        lock.unlockWrite(stamp2);
    }
}
