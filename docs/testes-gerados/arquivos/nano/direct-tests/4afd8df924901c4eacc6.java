/* @test    @summary Test peekFirst() method of LinkedBlockingDeque for correct behavior and null return when empty    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.lang.AssertionError;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPeekFirstEmptyDeque();
        testPeekFirstWithElements();
    }

    static void testPeekFirstEmptyDeque() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        Object result = deque.peekFirst();
        if (result != null) {
            throw new AssertionError("Expected null when peeking first on empty deque, but got: " + result);
        }
    }

    static void testPeekFirstWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        deque.add("element2");
        Object result = deque.peekFirst();
        if (!"element1".equals(result)) {
            throw new AssertionError("Expected 'element1' as first element, but got: " + result);
        }
        // Verify that peekFirst does not remove the element
        Object resultAfter = deque.peekFirst();
        if (!"element1".equals(resultAfter)) {
            throw new AssertionError("peekFirst() should not remove element. Expected 'element1', got: " + resultAfter);
        }
    }
}
