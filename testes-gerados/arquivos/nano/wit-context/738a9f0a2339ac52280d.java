/* @test    @summary Test for LinkedBlockingDeque.put method to verify exception paths and behavior    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testPutWithNullElement();
        testPutWithValidElement();
    }

    private static void testPutWithNullElement() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        try {
            deque.put(null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException when putting null element");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testPutWithValidElement() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String element = "test";
        deque.put(element);
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should contain the element after put");
        }
        String retrieved = deque.take();
        if (!element.equals(retrieved)) {
            throw new AssertionError("Retrieved element does not match inserted element");
        }
    }
}
