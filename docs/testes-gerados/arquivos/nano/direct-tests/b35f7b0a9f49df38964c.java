/*
 * @test
 * @summary Test for ConcurrentLinkedDeque.forEachRemaining method
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmpty();
        testForEachRemainingNullConsumer();
    }

    static void testForEachRemainingWithElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s);
        deque.forEachRemaining(consumer);
        String expected = "abc";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected '" + expected + "', but got '" + result + "'");
        }
    }

    static void testForEachRemainingEmpty() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s);
        deque.forEachRemaining(consumer); // should not throw
        if (result.length() != 0) {
            throw new AssertionError("Expected empty result, but got '" + result + "'");
        }
    }

    static void testForEachRemainingNullConsumer() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        boolean exceptionThrown = false;
        try {
            deque.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null consumer");
        }
    }
}
