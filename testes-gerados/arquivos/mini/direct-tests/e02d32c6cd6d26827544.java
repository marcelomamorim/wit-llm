/*
 * @test
 * @summary Test unlock(long) method of StampedLock for read and write unlock
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {
    public static void main(String[] args) {
        StampedLock lock = new StampedLock();

        // Test write lock unlock
        long writeStamp = lock.writeLock();
        if (writeStamp == 0L) {
            throw new RuntimeException("Failed to acquire write lock");
        }
        lock.unlock(writeStamp); // should unlock write lock without exception

        // Test read lock unlock
        long readStamp = lock.readLock();
        if (readStamp == 0L) {
            throw new RuntimeException("Failed to acquire read lock");
        }
        lock.unlock(readStamp); // should unlock read lock without exception

        // Test unlock with invalid stamp (zero) - no exception expected as per method
        // but no effect; just call to verify no crash
        lock.unlock(0L);

        // Test unlock with a stamp that looks like a write stamp but invalid
        // We create a stamp with WBIT set but invalid value
        long invalidWriteStamp = StampedLock.WBIT;
        try {
            lock.unlock(invalidWriteStamp);
        } catch (IllegalMonitorStateException e) {
            // expected if stamp invalid
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }

        // Test unlock with a stamp that looks like a read stamp but invalid
        long invalidReadStamp = 1L; // low bit clear, but likely invalid
        try {
            lock.unlock(invalidReadStamp);
        } catch (IllegalMonitorStateException e) {
            // expected if stamp invalid
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
