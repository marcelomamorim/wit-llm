/*
 * @test
 * @summary Witup-generated checks for StampedLock.unlockWrite(long) exception and release behavior
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testValidWriteStampUnlocks();
        testWrongStampThrowsWhileWriteLocked();
        testZeroStampThrowsWhileWriteLocked();
        testUnlockWithoutWriteLockThrows();
        testReadStampThrowsIllegalMonitorStateException();
    }

    static void testValidWriteStampUnlocks() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        if (stamp == 0L) {
            throw new RuntimeException("writeLock returned zero stamp");
        }
        lock.unlockWrite(stamp);
    }

    static void testWrongStampThrowsWhileWriteLocked() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        try {
            lock.unlockWrite(stamp + 1L);
            throw new RuntimeException("Expected IllegalMonitorStateException for wrong stamp");
        } catch (IllegalMonitorStateException expected) {
            // expected
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    static void testZeroStampThrowsWhileWriteLocked() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        try {
            lock.unlockWrite(0L);
            throw new RuntimeException("Expected IllegalMonitorStateException for zero stamp");
        } catch (IllegalMonitorStateException expected) {
            // expected
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    static void testUnlockWithoutWriteLockThrows() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        lock.unlockWrite(stamp);
        try {
            lock.unlockWrite(stamp);
            throw new RuntimeException("Expected IllegalMonitorStateException on double unlock");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }
    }

    static void testReadStampThrowsIllegalMonitorStateException() {
        StampedLock lock = new StampedLock();
        long readStamp = lock.readLock();
        try {
            lock.unlockWrite(readStamp);
            throw new RuntimeException("Expected IllegalMonitorStateException for read stamp");
        } catch (IllegalMonitorStateException expected) {
            // expected
        } finally {
            lock.unlockRead(readStamp);
        }
    }
}
