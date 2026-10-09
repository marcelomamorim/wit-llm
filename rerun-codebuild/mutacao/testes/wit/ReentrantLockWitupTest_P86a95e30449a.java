/*
 * @test
 * @summary Test for ReentrantLock.hasWaiters(Condition)
 * @run main ReentrantLockWitupTest_P86a95e30449a
 */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ReentrantLockWitupTest_P86a95e30449a {

    public static void main(String[] args) {
        testHasWaitersWithNullCondition();
        testHasWaitersWithInvalidCondition();
        testHasWaitersWithValidCondition();
    }

    private static void testHasWaitersWithNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.hasWaiters(null);
            System.out.println("FAIL: Expected NullPointerException for null condition");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null condition");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null condition: " + t);
        }
    }

    private static void testHasWaitersWithInvalidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition invalidCondition = lock.newCondition();
        try {
            lock.hasWaiters(invalidCondition);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid condition");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected for invalid condition");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for invalid condition: " + t);
        }
    }

    private static void testHasWaitersWithValidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        try {
            boolean result = lock.hasWaiters(condition);
            System.out.println("PASS: hasWaiters returned: " + result);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for valid condition: " + t);
        }
    }
}
