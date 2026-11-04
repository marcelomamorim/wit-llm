/*
 * @test
 * @summary Test StampedLock.unlockWrite(long) behavior
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {
    public static void main(String[] args) {
        StampedLock lock = new StampedLock();

        // Acquire write lock
        long stamp = lock.writeLock();

        // Correct unlockWrite call should succeed without exception
        lock.unlockWrite(stamp);

        // After unlock, try to unlock again with same stamp - should throw
        try {
            lock.unlockWrite(stamp);
            throw new RuntimeException("Expected IllegalMonitorStateException not thrown");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }

        // Try unlockWrite with zero stamp - should throw
        try {
            lock.unlockWrite(0L);
            throw new RuntimeException("Expected IllegalMonitorStateException not thrown for zero stamp");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }

        // Try unlockWrite with random invalid stamp - should throw
        try {
            lock.unlockWrite(12345L);
            throw new RuntimeException("Expected IllegalMonitorStateException not thrown for invalid stamp");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }
    }
}
