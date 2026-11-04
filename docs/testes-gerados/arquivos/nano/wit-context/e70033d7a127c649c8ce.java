/*
 * @test
 * @summary Test for LinkedBlockingDeque.peekLast() method.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.lang.AssertionError;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPeekLastEmptyDeque();
        testPeekLastWithElements();
    }

    static void testPeekLastEmptyDeque() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        Object result = deque.peekLast();
        if (result != null) {
            throw new AssertionError("Expected null for empty deque, but got: " + result);
        }
    }

    static void testPeekLastWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("first");
        deque.add("second");
        Object result = deque.peekLast();
        if (!"second".equals(result)) {
            throw new AssertionError("Expected 'second', but got: " + result);
        }
    }
}
