/*
 * @test
 * @summary Test ReentrantLock.getWaitQueueLength(Condition) for null and invalid condition
 * @run main ReentrantLockWitupTest_Pb00420c67d51
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ReentrantLockWitupTest_Pb00420c67d51 {
    public static void main(String[] args) {
        testNullConditionThrowsNPE();
        testInvalidConditionThrowsIllegalArgumentException();
    }

    private static void testNullConditionThrowsNPE() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.getWaitQueueLength(null);
            throw new Error("Expected NullPointerException for null condition");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testInvalidConditionThrowsIllegalArgumentException() {
        ReentrantLock lock = new ReentrantLock();
        Condition invalidCondition = new Condition() {
            public void await() throws InterruptedException { throw new UnsupportedOperationException(); }
            public void awaitUninterruptibly() { throw new UnsupportedOperationException(); }
            public long awaitNanos(long nanosTimeout) throws InterruptedException { throw new UnsupportedOperationException(); }
            public boolean awaitUntil(java.util.Date deadline) throws InterruptedException { throw new UnsupportedOperationException(); }
            public boolean await(long time, java.util.concurrent.TimeUnit unit) throws InterruptedException { throw new UnsupportedOperationException(); }
            public void signal() { throw new UnsupportedOperationException(); }
            public void signalAll() { throw new UnsupportedOperationException(); }
        };
        try {
            lock.getWaitQueueLength(invalidCondition);
            throw new Error("Expected IllegalArgumentException for invalid condition");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
