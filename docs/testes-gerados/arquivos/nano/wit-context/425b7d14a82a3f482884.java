/* @test    @summary Testando o método putFirst de LinkedBlockingDeque para comportamento esperado e exceções.    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testPutFirstWithNull();
        testPutFirstWithValidElement();
    }

    private static void testPutFirstWithNull() throws InterruptedException {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.putFirst(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when putting null element");
        }
    }

    private static void testPutFirstWithValidElement() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "element";
        deque.putFirst(element);
        String retrieved = deque.takeFirst();
        if (!element.equals(retrieved)) {
            throw new AssertionError("Element putFirst not retrieved correctly");
        }
    }
}
