/*
 * @test
 * @summary Test StampedLock.newCondition() throws UnsupportedOperationException
 * @run main StampedLockWitupTest
 */

import java.util.concurrent.locks.StampedLock;
import java.util.concurrent.locks.Condition;

public class StampedLockWitupTest {
    public static void main(String[] args) {
        StampedLock lock = new StampedLock();
        try {
            Condition cond = lock.newCondition();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        System.out.println("Test passed");
    }
}
