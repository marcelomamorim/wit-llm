/*
 * @test
 * @summary Test ReentrantLock.getWaitQueueLength(Condition) method behavior
 * @run main ReentrantLockWitupTest
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ReentrantLockWitupTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        // Test normal call returns int >= 0
        int length = lock.getWaitQueueLength(condition);
        if (length < 0) {
            throw new AssertionError("Wait queue length should be >= 0 but was " + length);
        }

        // Test passing null throws NullPointerException
        try {
            lock.getWaitQueueLength(null);
            throw new AssertionError("Expected NullPointerException for null condition");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test passing Condition not from this lock throws IllegalArgumentException
        ReentrantLock otherLock = new ReentrantLock();
        Condition otherCondition = otherLock.newCondition();
        try {
            lock.getWaitQueueLength(otherCondition);
            throw new AssertionError("Expected IllegalArgumentException for condition not owned by this lock");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
