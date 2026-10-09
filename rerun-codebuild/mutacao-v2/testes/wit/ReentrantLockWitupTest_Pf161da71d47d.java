/*
 * @test
 * @summary Test ReentrantLock.hasWaiters(Condition) for null and invalid condition
 * @run main ReentrantLockWitupTest_Pf161da71d47d
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ReentrantLockWitupTest_Pf161da71d47d {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        // Test 1: null condition should cause NullPointerException
        try {
            lock.hasWaiters(null);
            throw new RuntimeException("Expected NullPointerException for null condition");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: condition not instance of AbstractQueuedSynchronizer.ConditionObject should cause IllegalArgumentException
        Condition dummyCondition = new Condition() {
            public void await() throws InterruptedException { throw new UnsupportedOperationException(); }
            public void awaitUninterruptibly() { throw new UnsupportedOperationException(); }
            public long awaitNanos(long nanosTimeout) throws InterruptedException { throw new UnsupportedOperationException(); }
            public boolean awaitUntil(java.util.Date deadline) throws InterruptedException { throw new UnsupportedOperationException(); }
            public void signal() { throw new UnsupportedOperationException(); }
            public void signalAll() { throw new UnsupportedOperationException(); }
        };

        try {
            lock.hasWaiters(dummyCondition);
            throw new RuntimeException("Expected IllegalArgumentException for invalid condition type");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test 3: valid ConditionObject, no waiters initially, should return false
        Condition validCondition = lock.newCondition();
        boolean result = lock.hasWaiters(validCondition);
        if (result) {
            throw new RuntimeException("Expected hasWaiters to return false when no threads are waiting");
        }

        System.out.println("ReentrantLock.hasWaiters tests passed.");
    }
}
