/*
 * @test
 * @summary Test for peekFirst() method of LinkedBlockingDeque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPeekFirstEmpty();
        testPeekFirstWithElements();
    }

    private static void testPeekFirstEmpty() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        Object result = deque.peekFirst();
        if (result != null) {
            throw new AssertionError("Expected null for empty deque, but got: " + result);
        }
    }

    private static void testPeekFirstWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        deque.add("element2");
        Object result = deque.peekFirst();
        if (!"element1".equals(result)) {
            throw new AssertionError("Expected 'element1', but got: " + result);
        }
    }
}
