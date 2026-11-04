/* @test    @summary Test getWaitQueueLength method of ReentrantLock    @run main ReentrantLockWitupTest */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;

public class ReentrantLockWitupTest {

    public static void main(String[] args) {
        testGetWaitQueueLengthWithNullCondition();
        testGetWaitQueueLengthWithInvalidCondition();
        testGetWaitQueueLengthWithValidCondition();
    }

    static void testGetWaitQueueLengthWithNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.getWaitQueueLength(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testGetWaitQueueLengthWithInvalidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition fakeCondition = new Condition() {
            public void await() {}
            public void awaitUninterruptibly() {}
            public long awaitNanos(long nanosTimeout) { return 0; }
            public boolean await(long time, TimeUnit unit) { return false; }
            public boolean awaitUntil(java.util.Date deadline) { return false; }
            public void signal() {}
            public void signalAll() {}
        };
        try {
            lock.getWaitQueueLength(fakeCondition);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testGetWaitQueueLengthWithValidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        int length = lock.getWaitQueueLength(condition);
        if (length >= 0) {
            System.out.println("PASS: getWaitQueueLength returned: " + length);
        } else {
            System.out.println("FAIL: getWaitQueueLength returned negative value: " + length);
        }
    }
}
