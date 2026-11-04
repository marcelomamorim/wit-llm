/*
 * @test
 * @summary Test LinkedBlockingDeque.peekLast() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testPeekLastEmptyDeque();
        testPeekLastNonEmptyDeque();
    }

    private static void testPeekLastEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        // On empty deque, peekLast() should return null
        String result = deque.peekLast();
        if (result != null) {
            throw new AssertionError("peekLast() on empty deque should return null but returned: " + result);
        }
    }

    private static void testPeekLastNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String lastElement = "last";
        String firstElement = "first";
        deque.addFirst(firstElement);
        deque.addLast(lastElement);
        // peekLast() should return the last element added
        String result = deque.peekLast();
        if (!lastElement.equals(result)) {
            throw new AssertionError("peekLast() should return last element added but returned: " + result);
        }
    }
}
