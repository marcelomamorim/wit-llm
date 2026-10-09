/* @test
   @summary Testa o método putLast de LinkedBlockingDeque para entradas nulas e não nulas.
   @run main LinkedBlockingDequeWitupTest_P6a2cf8a917c2
 */
import java.util.concurrent.LinkedBlockingDeque;
public class LinkedBlockingDequeWitupTest_P6a2cf8a917c2 {
    public static void main(String[] args) {
        testPutLastNull();
        testPutLastValid();
    }
    private static void testPutLastNull() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.putLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when putLast(null)");
        }
    }
    private static void testPutLastValid() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "test";
        try {
            deque.putLast(element);
        } catch (InterruptedException e) {
            throw new AssertionError("Unexpected InterruptedException");
        }
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should contain the element after putLast");
        }
        String retrieved = deque.pollLast();
        if (!element.equals(retrieved)) {
            throw new AssertionError("Element retrieved does not match element added");
        }
    }
}
