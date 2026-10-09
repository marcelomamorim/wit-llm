/*
 * @test
 * @summary Test for ReentrantLock.hasWaiters method
 * @run main ReentrantLockWitupTest_P75f74f50cbbc
 */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReentrantLockWitupTest_P75f74f50cbbc {

    public static void main(String[] args) {
        testHasWaitersWithNullCondition();
        testHasWaitersWithInvalidCondition();
        testHasWaitersWithValidCondition();
    }

    static void testHasWaitersWithNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.hasWaiters(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testHasWaitersWithInvalidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition fakeCondition = new Condition() {
            public void await() { throw new UnsupportedOperationException(); }
            public void signal() { throw new UnsupportedOperationException(); }
            public void signalAll() { throw new UnsupportedOperationException(); }
            public boolean await(long time, java.util.concurrent.TimeUnit unit) { throw new UnsupportedOperationException(); }
            public boolean isOwnedByCurrentThread() { return false; }
            public boolean isSignalled() { return false; }
        };
        try {
            lock.hasWaiters(fakeCondition);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testHasWaitersWithValidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        boolean result = lock.hasWaiters(condition);
        // Since no thread is waiting, expect false
        if (result != false) {
            throw new AssertionError("Expected false, but got " + result);
        }
        // To test waiters, spawn a thread that waits on the condition
        Thread waiter = new Thread(() -> {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });
        waiter.start();
        try {
            // Give the thread time to start and wait
            Thread.sleep(50);
            boolean hasWaiters = lock.hasWaiters(condition);
            if (!hasWaiters) {
                throw new AssertionError("Expected true for hasWaiters, but got false");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Cleanup
            lock.lock();
            try {
                condition.signalAll();
            } finally {
                lock.unlock();
            }
            try {
                waiter.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
