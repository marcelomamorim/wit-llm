/*
 * @test
 * @summary Test ReentrantLock.hasWaiters method behavior
 * @run main ReentrantLockWitupTest_Pf161da71d47d
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ReentrantLockWitupTest_Pf161da71d47d {
    public static void main(String[] args) throws Exception {
        testNullConditionThrowsNPE();
        testNonConditionObjectThrowsIAE();
        testHasWaitersReturnsFalseInitially();
        testHasWaitersReturnsTrueAfterAwait();
    }

    private static void testNullConditionThrowsNPE() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.hasWaiters(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNonConditionObjectThrowsIAE() {
        ReentrantLock lock = new ReentrantLock();
        Condition fakeCondition = new Condition() {
            public void await() throws InterruptedException { throw new UnsupportedOperationException(); }
            public void awaitUninterruptibly() { throw new UnsupportedOperationException(); }
            public long awaitNanos(long nanosTimeout) throws InterruptedException { throw new UnsupportedOperationException(); }
            public boolean await(long time, java.util.concurrent.TimeUnit unit) throws InterruptedException { throw new UnsupportedOperationException(); }
            public boolean awaitUntil(java.util.Date deadline) throws InterruptedException { throw new UnsupportedOperationException(); }
            public void signal() { throw new UnsupportedOperationException(); }
            public void signalAll() { throw new UnsupportedOperationException(); }
        };
        try {
            lock.hasWaiters(fakeCondition);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testHasWaitersReturnsFalseInitially() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            Condition condition = lock.newCondition();
            boolean result = lock.hasWaiters(condition);
            if (result) {
                throw new RuntimeException("Expected hasWaiters to return false when no waiters");
            }
        } finally {
            lock.unlock();
        }
    }

    private static void testHasWaitersReturnsTrueAfterAwait() throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        final boolean[] hasWaitersResult = new boolean[1];

        Thread waiter = new Thread(new Runnable() {
            public void run() {
                lock.lock();
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        });

        lock.lock();
        try {
            waiter.start();
            // Wait for the waiter thread to start and await
            Thread.sleep(100);
            boolean result = lock.hasWaiters(condition);
            hasWaitersResult[0] = result;
            if (!result) {
                throw new RuntimeException("Expected hasWaiters to return true when there are waiters");
            }
            condition.signal();
        } finally {
            lock.unlock();
        }

        waiter.join();
    }
}
