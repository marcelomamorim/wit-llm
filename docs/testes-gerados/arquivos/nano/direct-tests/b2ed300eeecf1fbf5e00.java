/* @test    @summary Test for StampedLock.newCondition() method to ensure it throws UnsupportedOperationException as specified.    @run main StampedLockWitupTest */

import java.util.concurrent.locks.StampedLock;

public class StampedLockWitupTest {

    public static void main(String[] args) {
        testNewConditionThrows();
    }

    public static void testNewConditionThrows() {
        StampedLock lock = new StampedLock();
        try {
            lock.newCondition();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes as exception is expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
