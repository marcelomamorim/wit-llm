/*
 * @test
 * @summary Test basic unlock behavior of ReentrantLock
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        // Test unlocking without locking first should throw IllegalMonitorStateException
        boolean threw = false;
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalMonitorStateException when unlocking without lock");
        }

        // Test lock and unlock normal usage
        lock.lock();
        try {
            if (!lock.isHeldByCurrentThread()) {
                throw new RuntimeException("Lock should be held by current thread after lock()");
            }
        } finally {
            lock.unlock();
        }

        // After unlock, lock should not be held
        if (lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Lock should not be held by current thread after unlock()");
        }

        // Test reentrant locking and unlocking
        lock.lock();
        lock.lock();
        try {
            int holdCount = lock.getHoldCount();
            if (holdCount != 2) {
                throw new RuntimeException("Hold count should be 2 after locking twice, but was " + holdCount);
            }
        } finally {
            lock.unlock();
            if (lock.getHoldCount() != 1) {
                throw new RuntimeException("Hold count should be 1 after one unlock");
            }
            lock.unlock();
        }

        if (lock.isHeldByCurrentThread()) {
            throw new RuntimeException("Lock should not be held after fully unlocking");
        }
    }
}
