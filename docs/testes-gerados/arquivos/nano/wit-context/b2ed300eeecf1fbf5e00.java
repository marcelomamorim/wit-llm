/*
 * @test
 * @summary Test for java.util.concurrent.locks.StampedLock.newCondition()
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;
import java.util.concurrent.locks.Condition;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testNewConditionThrowsException();
    }

    public static void testNewConditionThrowsException() {
        StampedLock lock = new StampedLock();
        try {
            lock.newCondition();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
