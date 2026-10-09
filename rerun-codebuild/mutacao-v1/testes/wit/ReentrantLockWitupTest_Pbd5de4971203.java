/* @test    @summary Test for ReentrantLock.hasWaiters(Condition) method behavior and exception paths    @run main ReentrantLockWitupTest_Pbd5de4971203 */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReentrantLockWitupTest_Pbd5de4971203 {

    public static void main(String[] args) {
        testHasWaitersWithNullCondition();
        testHasWaitersWithInvalidCondition();
        testHasWaitersWithValidCondition();
    }

    private static void testHasWaitersWithNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.hasWaiters(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    private static void testHasWaitersWithInvalidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition invalidCondition = new Condition() {
            @Override
            public void await() throws InterruptedException {
                throw new UnsupportedOperationException();
            }
            @Override
            public void signal() {}
            @Override
            public void signalAll() {}
        };
        try {
            lock.hasWaiters(invalidCondition);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    private static void testHasWaitersWithValidCondition() {
        ReentrantLock lock = new ReentrantLock();
        // Create a valid Condition object
        Condition condition = lock.newCondition();
        try {
            boolean result = lock.hasWaiters(condition);
            // Result is boolean, no exception expected
            // No specific assertion on result, just ensure no exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
