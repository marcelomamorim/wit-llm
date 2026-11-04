/* @test    @summary Test for StampedLock.newCondition() method    @run main StampedLockWitupTest */

import java.util.concurrent.locks.StampedLock;
import java.util.concurrent.locks.Condition;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testNewConditionReturnsUnsupportedOperationException();
    }

    private static void testNewConditionReturnsUnsupportedOperationException() {
        StampedLock lock = new StampedLock();
        try {
            lock.newCondition();
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
