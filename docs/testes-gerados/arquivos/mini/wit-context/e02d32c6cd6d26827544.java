/*
 * @test
 * @summary Test StampedLock.unlock(long) for correct exception on invalid stamp
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {
    public static void main(String[] args) {
        StampedLock lock = new StampedLock();

        // Test unlock with valid write lock stamp
        long writeStamp = lock.writeLock();
        try {
            lock.unlock(writeStamp); // should succeed without exception
        } catch (IllegalMonitorStateException e) {
            throw new RuntimeException("Unexpected IllegalMonitorStateException on valid write unlock", e);
        }

        // Test unlock with valid read lock stamp
        long readStamp = lock.readLock();
        try {
            lock.unlock(readStamp); // should succeed without exception
        } catch (IllegalMonitorStateException e) {
            throw new RuntimeException("Unexpected IllegalMonitorStateException on valid read unlock", e);
        }

        // Test unlock with invalid stamp (zero)
        try {
            lock.unlock(0L);
            throw new RuntimeException("Expected IllegalMonitorStateException not thrown for stamp=0");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }

        // Test unlock with invalid stamp (random invalid value)
        long invalidStamp = 123456789L;
        try {
            lock.unlock(invalidStamp);
            throw new RuntimeException("Expected IllegalMonitorStateException not thrown for invalid stamp");
        } catch (IllegalMonitorStateException expected) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
