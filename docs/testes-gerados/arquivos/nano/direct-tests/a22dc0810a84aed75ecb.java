/* @test    @summary Test for ArrayDeque's forEachRemaining method to ensure it correctly applies the Consumer to all remaining elements.    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyDeque();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithElements() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e);
        deque.forEachRemaining(consumer);
        // Since forEachRemaining is called once, it should process all elements
        // The deque remains unchanged, but the cursor advances to the end
        // We verify that the consumer was applied to all elements
        assert result.toString().equals("123") : "Expected '123', got '" + result.toString() + "'";
    }

    static void testForEachRemainingWithEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e);
        deque.forEachRemaining(consumer);
        // No elements to process, result should be empty
        assert result.toString().isEmpty() : "Expected empty string, got '" + result.toString() + "'";
    }

    static void testForEachRemainingWithNullConsumer() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        try {
            deque.forEachRemaining(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
