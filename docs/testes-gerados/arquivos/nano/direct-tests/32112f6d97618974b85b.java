/* @test    @summary Test for ArrayDeque's forEachRemaining method to ensure it correctly applies a Consumer to remaining elements without concurrent modification.    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingAppliesConsumer();
        testForEachRemainingWithEmptyDeque();
        testForEachRemainingThrowsOnConcurrentModification();
    }

    static void testForEachRemainingAppliesConsumer() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = sb::append;
        deque.forEachRemaining(consumer);
        // Since remaining is set to 0 after forEachRemaining, no elements should be processed now.
        // To test the effect, we invoke forEachRemaining again with empty deque.
        assert sb.toString().equals("123") : "Consumer should have appended all elements";
    }

    static void testForEachRemainingWithEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        deque.forEachRemaining(sb::append);
        assert sb.toString().isEmpty() : "No elements to process, string should be empty";
    }

    static void testForEachRemainingThrowsOnConcurrentModification() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        try {
            deque.forEachRemaining(e -> {
                deque.add(4); // Modifying deque during iteration should cause exception
            });
            assert false : "Expected ConcurrentModificationException";
        } catch (ConcurrentModificationException e) {
            // Expected exception
        }
    }
}
