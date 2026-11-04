/*
 * @test
 * @summary Tests for ArrayDeque.tryAdvance method to ensure correct iteration behavior.
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceEmptyDeque();
        testTryAdvanceNullConsumer();
    }

    static void testTryAdvanceWithElements() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = e -> sb.append(e);
        boolean result;
        do {
            result = deque.tryAdvance(consumer);
        } while (result);
        assert sb.toString().equals("123") : "Expected '123', got '" + sb.toString() + "'";
    }

    static void testTryAdvanceEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = e -> sb.append(e);
        boolean result = deque.tryAdvance(consumer);
        assert result == false : "Expected false for empty deque";
        assert sb.toString().isEmpty() : "Consumer should not be called";
    }

    static void testTryAdvanceNullConsumer() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        try {
            deque.tryAdvance(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
