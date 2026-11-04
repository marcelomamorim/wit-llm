/* @test    @summary Test for StampedLock.newCondition() method to verify it throws UnsupportedOperationException as expected.    @run main StampedLockWitupTest */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testNewConditionThrowsException();
    }

    public static void testNewConditionThrowsException() {
        StampedLock lock = new StampedLock();
        try {
            lock.newCondition();
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
