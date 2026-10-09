/* @test    @summary Test for getWaitQueueLength method of ReentrantLock    @run main ReentrantLockWitupTest_Pee92698340f1 */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReentrantLockWitupTest_Pee92698340f1 {

    public static void main(String[] args) {
        testGetWaitQueueLengthWithNullCondition();
        testGetWaitQueueLengthWithValidCondition();
        testGetWaitQueueLengthWithInvalidCondition();
    }

    private static void testGetWaitQueueLengthWithNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.getWaitQueueLength(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testGetWaitQueueLengthWithValidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        int length = lock.getWaitQueueLength(condition);
        if (length != 0) {
            // The queue should be empty initially
            throw new AssertionError("Expected wait queue length 0, but got: " + length);
        }
    }

    private static void testGetWaitQueueLengthWithInvalidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition fakeCondition = new Condition() {
            @Override
            public void await() { throw new UnsupportedOperationException(); }
            @Override
            public void awaitUninterruptibly() { throw new UnsupportedOperationException(); }
            @Override
            public long awaitNanos(long nanosTimeout) { throw new UnsupportedOperationException(); }
            @Override
            public boolean await(long time, TimeUnit unit) { throw new UnsupportedOperationException(); }
            @Override
            public boolean awaitUntil(java.util.Date deadline) { throw new UnsupportedOperationException(); }
            @Override
            public void signal() { throw new UnsupportedOperationException(); }
            @Override
            public void signalAll() { throw new UnsupportedOperationException(); }
        };
        try {
            lock.getWaitQueueLength(fakeCondition);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
