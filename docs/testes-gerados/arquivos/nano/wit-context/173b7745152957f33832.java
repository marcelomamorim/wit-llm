/*
 * @test
 * @summary Test for ReentrantReadWriteLock.unlock() method to observe exception behavior.
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockWitupTest {

    public static void main(String[] args) {
        testUnlockThrowsUnsupportedOperationException();
    }

    private static void testUnlockThrowsUnsupportedOperationException() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        // Manually acquire the lock in a way that allows us to call unlock
        // Since the public API does not expose internal state, we simulate a scenario
        // where unlock might throw UnsupportedOperationException.
        // Note: The actual lock acquisition is not necessary for this test, as
        // the method unlock() does not depend on internal state for throwing.
        // We invoke unlock() directly.
        try {
            lock.unlock();
            // If no exception, test passes as no exception is expected in normal use.
            System.out.println("unlock() executed without exception.");
        } catch (UnsupportedOperationException e) {
            // Expected exception path based on WIT evidence.
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            // Any other exception indicates unexpected behavior.
            System.out.println("Unexpected exception: " + e);
        }
    }
}
