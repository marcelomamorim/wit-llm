/* @test    @summary Test drainTo(Collection<? super E> c) for DelayQueue with null argument    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.Collection;
import java.util.ArrayList;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testDrainToNull();
    }

    public static void testDrainToNull() {
        DelayQueue<Object> dq = new DelayQueue<>();
        try {
            dq.drainTo(null);
            // Se não lançar exceção, o teste falha
            System.err.println("Failed: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Esperado
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
