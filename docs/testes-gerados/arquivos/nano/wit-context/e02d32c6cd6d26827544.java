/* @test    @summary Test for StampedLock.unlock(long) method to verify correct behavior and exception handling    @run main StampedLockWitupTest */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testUnlockWithWriteStamp();
        testUnlockWithReadStamp();
        testUnlockWithInvalidStamp();
    }

    private static void testUnlockWithWriteStamp() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        try {
            lock.unlock(stamp);
            System.out.println("testUnlockWithWriteStamp passed");
        } catch (Exception e) {
            System.err.println("testUnlockWithWriteStamp failed: " + e);
        }
    }

    private static void testUnlockWithReadStamp() {
        StampedLock lock = new StampedLock();
        long stamp = lock.readLock();
        try {
            lock.unlock(stamp);
            System.out.println("testUnlockWithReadStamp passed");
        } catch (Exception e) {
            System.err.println("testUnlockWithReadStamp failed: " + e);
        }
    }

    private static void testUnlockWithInvalidStamp() {
        StampedLock lock = new StampedLock();
        long invalidStamp = 999999L; // an arbitrary invalid stamp
        try {
            lock.unlock(invalidStamp);
            System.err.println("testUnlockWithInvalidStamp failed: no exception thrown");
        } catch (IllegalMonitorStateException e) {
            System.out.println("testUnlockWithInvalidStamp passed: caught expected exception");
        } catch (Exception e) {
            System.err.println("testUnlockWithInvalidStamp failed: caught unexpected exception: " + e);
        }
    }
}
