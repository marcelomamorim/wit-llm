/*
 * @test
 * @summary Test ReentrantReadWriteLock.unlock() behavior including exception path
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) {
        testUnlockNormalAndException();
    }

    private static void testUnlockNormalAndException() {
        // Create a ReentrantReadWriteLock instance
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        WriteLock writeLock = lock.writeLock();

        // Acquire the write lock
        writeLock.lock();

        // Unlock once - should succeed normally
        writeLock.unlock();

        // Unlock again without locking - according to WIT expath, unlock() calls sync.release(1)
        // which calls WriteLock.unlock() that throws UnsupportedOperationException
        // We test that this exception is thrown as expected
        boolean exceptionThrown = false;
        try {
            writeLock.unlock();
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }

        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown on unlock without lock");
        }
    }
}
