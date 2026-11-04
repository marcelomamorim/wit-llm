/*
 * @test
 * @summary Test ReentrantReadWriteLock.unlock() basic behavior
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        // Test 1: Unlock without lock held - expect IllegalMonitorStateException
        boolean illegalMonitorStateThrown = false;
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException ex) {
            illegalMonitorStateThrown = true;
        }
        if (!illegalMonitorStateThrown) {
            throw new RuntimeException("Expected IllegalMonitorStateException when unlocking without holding lock");
        }

        // Test 2: Lock and unlock write lock
        writeLock.lock();
        try {
            lock.unlock(); // should succeed
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception unlocking write lock", e);
        }

        // Test 3: Lock and unlock read lock
        readLock.lock();
        try {
            lock.unlock(); // should succeed
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception unlocking read lock", e);
        }

        // Test 4: Reentrant locking and unlocking write lock
        writeLock.lock();
        writeLock.lock();
        try {
            lock.unlock(); // first unlock
            lock.unlock(); // second unlock
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during reentrant unlock", e);
        }

        // Test 5: Reentrant locking and unlocking read lock
        readLock.lock();
        readLock.lock();
        try {
            lock.unlock(); // first unlock
            lock.unlock(); // second unlock
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during reentrant unlock", e);
        }
    }
}
