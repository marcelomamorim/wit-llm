/* @test    @summary Test for StampedLock.unlockWrite to verify exception throwing on invalid stamps    @run main StampedLockWitupTest */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testUnlockWriteWithInvalidStamp();
    }

    private static void testUnlockWriteWithInvalidStamp() {
        StampedLock lock = new StampedLock();
        long validStamp = lock.writeLock();
        long invalidStamp = validStamp + 1;
        boolean exceptionThrown = false;
        try {
            lock.unlockWrite(invalidStamp);
        } catch (IllegalMonitorStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalMonitorStateException was not thrown for invalid stamp.");
        }
        // Clean up
        lock.unlockWrite(validStamp);
    }
}
