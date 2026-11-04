/*
 * @test
 * @summary Tests ReentrantLock.getWaitQueueLength(Condition) for null, foreign Condition, and waiting-thread observable behavior
 * @run main ReentrantLockWitupTest
 */

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.getWaitQueueLength(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForeignCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition foreign = new Condition() {
            public void await() throws InterruptedException { }
            public void awaitUninterruptibly() { }
            public long awaitNanos(long nanosTimeout) throws InterruptedException { return 0L; }
            public boolean await(long time, TimeUnit unit) throws InterruptedException { return false; }
            public boolean awaitUntil(Date deadline) throws InterruptedException { return false; }
            public void signal() { }
            public void signalAll() { }
        };
        try {
            lock.getWaitQueueLength(foreign);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testEmptyQueueWhenHeld() {
        ReentrantLock lock = new ReentrantLock();
        Condition cond = lock.newCondition();
        lock.lock();
        try {
            check(lock.getWaitQueueLength(cond) == 0, "expected empty wait queue");
        } finally {
            lock.unlock();
        }
    }

    static void testOneWaiter() throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        final Condition cond = lock.newCondition();
        Thread waiter = new Thread(() -> {
            lock.lock();
            try {
                cond.await();
            } catch (InterruptedException e) {
                // ignore
            } finally {
                lock.unlock();
            }
        });
        waiter.start();

        int observed = -1;
        for (int i = 0; i < 10000; i++) {
            lock.lock();
            try {
                observed = lock.getWaitQueueLength(cond);
            } finally {
                lock.unlock();
            }
            if (observed == 1) {
                break;
            }
            Thread.sleep(1);
        }
        check(observed == 1, "expected wait queue length 1, got " + observed);

        lock.lock();
        try {
            cond.signal();
            check(lock.getWaitQueueLength(cond) == 0,
                  "expected wait queue length 0 after signal");
        } finally {
            lock.unlock();
        }
        waiter.join(10000);
        check(!waiter.isAlive(), "waiter did not terminate");
    }

    public static void main(String[] args) throws Exception {
        testNullCondition();
        testForeignCondition();
        testEmptyQueueWhenHeld();
        testOneWaiter();
    }
}
