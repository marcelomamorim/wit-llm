/* @test
   @summary Testa getWaitQueueLength com condição nula.
   @run main ReentrantLockWitupTest_P9a7c11199c99
 */
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReentrantLockWitupTest_P9a7c11199c99 {
    public static void main(String[] args) {
        testGetWaitQueueLengthWithNullCondition();
    }

    public static void testGetWaitQueueLengthWithNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.getWaitQueueLength(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
