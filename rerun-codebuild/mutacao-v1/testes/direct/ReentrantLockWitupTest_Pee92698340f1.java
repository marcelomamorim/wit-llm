/* @test    @summary Test getWaitQueueLength method of ReentrantLock for null, invalid, and valid conditions    @run main ReentrantLockWitupTest_Pee92698340f1 */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReentrantLockWitupTest_Pee92698340f1 {

    public static void main(String[] args) {
        testNullCondition();
        testInvalidCondition();
        testValidCondition();
    }

    static void testNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.getWaitQueueLength(null);
            throw new AssertionError("Expected NullPointerException for null condition");
        } catch (NullPointerException e) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for null condition", t);
        }
    }

    static void testInvalidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition fakeCondition = new Condition() {
            @Override
            public void await() { throw new UnsupportedOperationException(); }
            @Override
            public void awaitUninterruptibly() { throw new UnsupportedOperationException(); }
            @Override
            public long awaitNanos(long nanosTimeout) { throw new UnsupportedOperationException(); }
            @Override
            public boolean await(long time, java.util.concurrent.TimeUnit unit) { throw new UnsupportedOperationException(); }
            @Override
            public boolean awaitUntil(java.util.Date deadline) { throw new UnsupportedOperationException(); }
            @Override
            public void signal() { throw new UnsupportedOperationException(); }
            @Override
            public void signalAll() { throw new UnsupportedOperationException(); }
        };
        try {
            lock.getWaitQueueLength(fakeCondition);
            throw new AssertionError("Expected IllegalArgumentException for invalid condition");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for invalid condition", t);
        }
    }

    static void testValidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        int length = lock.getWaitQueueLength(condition);
        if (length != 0) {
            throw new AssertionError("Expected wait queue length 0, but got " + length);
        }
    }
}
