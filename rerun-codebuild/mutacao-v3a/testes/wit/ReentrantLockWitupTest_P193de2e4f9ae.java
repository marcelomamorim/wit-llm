/* @test    @summary Test getWaitQueueLength with null condition expecting NullPointerException    @run main ReentrantLockWitupTest_P193de2e4f9ae */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReentrantLockWitupTest_P193de2e4f9ae {

    public static void main(String[] args) {
        testGetWaitQueueLengthNullCondition();
        testGetWaitQueueLengthValidCondition();
        testGetWaitQueueLengthInvalidCondition();
    }

    static void testGetWaitQueueLengthNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        boolean threwNPE = false;
        try {
            lock.getWaitQueueLength(null);
        } catch (NullPointerException e) {
            threwNPE = true;
        }
        if (!threwNPE) {
            throw new AssertionError("Expected NullPointerException for null condition");
        }
    }

    static void testGetWaitQueueLengthValidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        int length = lock.getWaitQueueLength(condition);
        if (length != 0) {
            throw new AssertionError("Expected wait queue length to be 0 at start, but was " + length);
        }
    }

    static void testGetWaitQueueLengthInvalidCondition() {
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();
        Condition condition = lock1.newCondition();
        boolean threwIllegalArg = false;
        try {
            lock2.getWaitQueueLength(condition);
        } catch (IllegalArgumentException e) {
            threwIllegalArg = true;
        }
        if (!threwIllegalArg) {
            throw new AssertionError("Expected IllegalArgumentException for condition from different lock");
        }
    }
}
