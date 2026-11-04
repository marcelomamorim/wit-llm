/*
 * @test
 * @summary Test ReentrantReadWriteLock.lock() method behavior including exception path
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) {
        testLockThrowsUnsupportedOperationException();
    }

    private static void testLockThrowsUnsupportedOperationException() {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        boolean unsupportedOperationThrown = false;
        try {
            lock.lock();
        } catch (UnsupportedOperationException e) {
            unsupportedOperationThrown = true;
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type", t);
        }
        if (!unsupportedOperationThrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown by lock()");
        }
    }
}
