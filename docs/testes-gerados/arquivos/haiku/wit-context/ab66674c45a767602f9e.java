/*
 * @test
 * @summary Witup-derived checks for ReentrantLock.getWaitQueueLength(Condition) null and foreign-condition guards
 * @run main ReentrantLockWitupTest
 */

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWitupTest {

    static final class ForeignCondition implements Condition {
        public void await() throws InterruptedException { }
        public void awaitUninterruptibly() { }
        public long awaitNanos(long nanosTimeout) throws InterruptedException { return 0L; }
        public boolean await(long time, TimeUnit unit) throws InterruptedException { return false; }
        public boolean awaitUntil(Date deadline) throws InterruptedException { return false; }
        public void signal() { }
        public void signalAll() { }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    static void testNullConditionThrowsNpe() {
        ReentrantLock lock = new ReentrantLock();
        boolean thrown = false;
        try {
            lock.getWaitQueueLength(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null condition");
    }

    static void testForeignConditionThrowsIae() {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        boolean thrown = false;
        try {
            lock.getWaitQueueLength(new ForeignCondition());
        } catch (IllegalArgumentException e) {
            thrown = true;
        } finally {
            lock.unlock();
        }
        check(thrown, "expected IllegalArgumentException for non-ConditionObject");
    }

    static void testNewConditionHasEmptyWaitQueue() {
        ReentrantLock lock = new ReentrantLock();
        Condition c = lock.newCondition();
        lock.lock();
        try {
            check(lock.getWaitQueueLength(c) == 0, "fresh condition should have wait queue length 0");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        testNullConditionThrowsNpe();
        testForeignConditionThrowsIae();
        testNewConditionHasEmptyWaitQueue();
    }
}
