/*
 * @test
 * @summary Test LinkedBlockingDeque.pollFirst() behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testPollFirstEmptyDequeReturnsNull();
        testPollFirstReturnsElementsInOrder();
    }

    static void testPollFirstEmptyDequeReturnsNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String result = deque.pollFirst();
        if (result != null) {
            throw new AssertionError("Expected null when polling first from empty deque, got: " + result);
        }
    }

    static void testPollFirstReturnsElementsInOrder() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("first");
        deque.addLast("second");

        String first = deque.pollFirst();
        if (!"first".equals(first)) {
            throw new AssertionError("Expected 'first' but got: " + first);
        }

        String second = deque.pollFirst();
        if (!"second".equals(second)) {
            throw new AssertionError("Expected 'second' but got: " + second);
        }

        String third = deque.pollFirst();
        if (third != null) {
            throw new AssertionError("Expected null after polling all elements, got: " + third);
        }
    }
}
