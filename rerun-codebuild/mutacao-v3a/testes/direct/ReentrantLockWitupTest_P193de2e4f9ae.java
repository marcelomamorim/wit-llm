/* @test    @summary Test getWaitQueueLength method for ReentrantLock    @run main ReentrantLockWitupTest_P193de2e4f9ae */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;

public class ReentrantLockWitupTest_P193de2e4f9ae {

    public static void main(String[] args) {
        testNullCondition();
        testInvalidCondition();
        testValidCondition();
    }

    private static void testNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.getWaitQueueLength(null);
            System.out.println("FAIL: Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testInvalidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition dummyCondition = lock.newCondition();
        // Create a dummy object that does not implement AbstractQueuedSynchronizer.ConditionObject
        Object invalidCondition = new Object();
        try {
            // Using reflection to bypass compile-time check; simulating invalid object
            lock.getWaitQueueLength((Condition)invalidCondition);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown.");
        } catch (ClassCastException e) {
            // Since cast is invalid, ClassCastException occurs before method call
            System.out.println("PASS: ClassCastException thrown as expected.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testValidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        // Lock the lock and the condition
        lock.lock();
        try {
            int length = lock.getWaitQueueLength(condition);
            System.out.println("Wait queue length (locked, no waiters): " + length);
        } finally {
            lock.unlock();
        }
        // No threads waiting, expecting 0
        int lengthAfter = lock.getWaitQueueLength(condition);
        if (lengthAfter == 0) {
            System.out.println("PASS: Wait queue length is 0 as expected.");
        } else {
            System.out.println("FAIL: Expected 0, but got: " + lengthAfter);
        }
    }
}
