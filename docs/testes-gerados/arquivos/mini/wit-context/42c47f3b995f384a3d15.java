/*
 * @test
 * @summary Test ReentrantReadWriteLock.lock() method behavior
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            // The lock() method calls sync.acquireShared(1) which in turn calls ReadLock.lock()
            // According to WIT, ReadLock.lock() throws UnsupportedOperationException
            // We test that calling lock() results in UnsupportedOperationException
            lock.lock();
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected path: test passes
        }

        // Also test the fair variant to confirm consistent behavior
        ReentrantReadWriteLock fairLock = new ReentrantReadWriteLock(true);
        try {
            fairLock.lock();
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected path: test passes
        }
    }
}
