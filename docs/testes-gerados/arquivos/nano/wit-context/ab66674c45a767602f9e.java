/* @test    @summary Test getWaitQueueLength with null condition to verify NullPointerException    @run main ReentrantLockWitupTest */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReentrantLockWitupTest {

    public static void main(String[] args) {
        testGetWaitQueueLengthWithNullCondition();
    }

    public static void testGetWaitQueueLengthWithNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.getWaitQueueLength(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException caught as expected");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
