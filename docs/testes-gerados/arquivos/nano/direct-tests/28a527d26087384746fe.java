/* @test    @summary Test ArrayDeque forEachRemaining method for correct behavior and exception handling    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingNullConsumer();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNormal() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer i) -> sb.append(i);
        deque.forEachRemaining(consumer);
        assert sb.toString().equals("123") : "Expected '123', got '" + sb.toString() + "'";
    }

    static void testForEachRemainingNullConsumer() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        boolean exceptionThrown = false;
        try {
            deque.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException was not thrown for null consumer";
    }

    static void testForEachRemainingConcurrentModification() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Consumer<Integer> consumer = (Integer i) -> {
            if (i == 2) {
                deque.add(4); // modify during iteration
            }
        };
        boolean exceptionThrown = false;
        try {
            deque.forEachRemaining(consumer);
        } catch (ConcurrentModificationException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "ConcurrentModificationException was not thrown during modification";
    }
}
