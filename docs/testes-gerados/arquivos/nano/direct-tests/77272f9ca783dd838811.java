/* @test    @summary Test for StampedLock.unlockWrite method to ensure proper exception handling and behavior.    @run main StampedLockWitupTest */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testUnlockWriteWithInvalidStamp();
        testUnlockWriteWithValidStamp();
    }

    private static void testUnlockWriteWithInvalidStamp() {
        StampedLock lock = new StampedLock();
        long invalidStamp = 12345L; // arbitrary invalid stamp
        boolean exceptionThrown = false;
        try {
            lock.unlockWrite(invalidStamp);
        } catch (IllegalMonitorStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalMonitorStateException for invalid stamp") ;
        }
    }

    private static void testUnlockWriteWithValidStamp() {
        StampedLock lock = new StampedLock();
        long stamp = lock.writeLock();
        // Should not throw exception
        lock.unlockWrite(stamp);
    }
}
