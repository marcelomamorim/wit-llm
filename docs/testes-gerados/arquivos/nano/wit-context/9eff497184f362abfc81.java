/* @test    @summary Test the lock() method of ReentrantLock for normal behavior and exception path    @run main ReentrantLockWitupTest */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {

    public static void main(String[] args) {
        testLockNormal();
        testLockThrowsUnsupportedOperationException();
    }

    private static void testLockNormal() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.lock();
            if (!lock.isHeldByCurrentThread()) {
                throw new AssertionError("Lock should be held by current thread after lock() call");
            }
        } finally {
            lock.unlock();
        }
    }

    private static void testLockThrowsUnsupportedOperationException() {
        ReentrantLock lock = new ReentrantLock() {
            @Override
            public void lock() {
                throw new UnsupportedOperationException("Simulated exception for testing");
            }
        };
        boolean threw = false;
        try {
            lock.lock();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
