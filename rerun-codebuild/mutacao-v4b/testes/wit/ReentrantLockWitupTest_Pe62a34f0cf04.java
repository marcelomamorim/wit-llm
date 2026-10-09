/* @test
   @summary Testa o método hasWaiters de ReentrantLock para condições nulas.
   @run main ReentrantLockWitupTest_Pe62a34f0cf04
 */
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReentrantLockWitupTest_Pe62a34f0cf04 {
    public static void main(String[] args) {
        testHasWaitersWithNullCondition();
    }

    public static void testHasWaitersWithNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.hasWaiters(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
