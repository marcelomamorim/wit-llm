/*
 * @test
 * @summary Test ReentrantReadWriteLock.newCondition() throws UnsupportedOperationException
 * @run main ReentrantReadWriteLockWitupTest
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Condition;

public class ReentrantReadWriteLockWitupTest {
    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        try {
            Condition cond = lock.newCondition();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception caught, test passes
        }
    }
}
