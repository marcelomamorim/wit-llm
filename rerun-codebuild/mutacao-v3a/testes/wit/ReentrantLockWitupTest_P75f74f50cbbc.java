/* @test    @summary Testa o método hasWaiters de ReentrantLock com condição nula.
 * @run main ReentrantLockWitupTest_P75f74f50cbbc
 */

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ReentrantLockWitupTest_P75f74f50cbbc {
    public static void main(String[] args) {
        testHasWaitersWithNullCondition();
    }

    public static void testHasWaitersWithNullCondition() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.hasWaiters(null);
        } catch (NullPointerException expected) {
            System.out.println("NullPointerException capturada com sucesso");
            return;
        }
        throw new AssertionError("Esperado NullPointerException ao passar null para hasWaiters");
    }
}
