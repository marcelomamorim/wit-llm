/* @test
   @summary Tests getWaitQueueLength method of ReentrantLock.
   @run main ReentrantLockWitupTest_P9a7c11199c99
 */
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.Objects;

public class ReentrantLockWitupTest_P9a7c11199c99 {
    public static void main(String[] args) {
        testNullCondition();
        testInvalidCondition();
        testValidCondition();
    }

    private static void testNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.getWaitQueueLength(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testInvalidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition fakeCondition = new Condition() {
            @Override public void await() { }
            @Override public void signal() { }
            @Override public void signalAll() { }
            @Override public boolean isOwnedByCurrentThread() { return false; }
            @Override public int getWaitQueueLength() { return 0; }
        };
        try {
            lock.getWaitQueueLength(fakeCondition);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidCondition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        int length = lock.getWaitQueueLength(condition);
        if (length != 0) {
            throw new AssertionError("Expected wait queue length 0, but got " + length);
        }
    }
}
