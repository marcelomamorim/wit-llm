/* @test    @summary Test ReentrantReadWriteLock lock method for basic functionality    @run main ReentrantReadWriteLockWitupTest */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        testLockMethod();
    }

    public static void testLockMethod() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            lock.lock();
            // If lock is acquired, the thread should proceed without exception
            System.out.println("Lock acquired successfully.");
        } finally {
            // Ensure the lock is released
            lock.unlock();
        }
    }
}
