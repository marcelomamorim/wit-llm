/* @test
   @summary Test hasWaiters method of ReentrantLock.
   @run main ReentrantLockWitupTest_Pe62a34f0cf04
 */
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.Objects;

public class ReentrantLockWitupTest_Pe62a34f0cf04 {
    public static void main(String[] args) {
        testHasWaitersWithNullCondition();
        testHasWaitersWithInvalidCondition();
        testHasWaitersWithValidCondition();
    }

    private static void testHasWaitersWithNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.hasWaiters(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testHasWaitersWithInvalidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition invalidCondition = new Condition() {
            @Override
            public void await() throws InterruptedException { throw new UnsupportedOperationException(); }
            @Override
            public void signal() { throw new UnsupportedOperationException(); }
            @Override
            public void signalAll() { throw new UnsupportedOperationException(); }
        };
        try {
            lock.hasWaiters(invalidCondition);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testHasWaitersWithValidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        boolean result = lock.hasWaiters(condition);
        // Since no thread is waiting, expect false
        if (result != false) {
            throw new AssertionError("Expected false, but got " + result);
        }
        // Additional test: simulate waiters by starting a thread that waits on the condition
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
        lock.lock();
        try {
            waiter.start();
            // Wait briefly to ensure the thread is waiting
            Thread.sleep(50);
            boolean hasWaiters = lock.hasWaiters(condition);
            if (!hasWaiters) {
                throw new AssertionError("Expected true, but got false");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
            try {
                lock.lock();
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
        try {
            waiter.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
