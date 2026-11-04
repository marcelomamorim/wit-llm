/* @test    @summary Test for LinkedBlockingDeque.forEachRemaining method    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmpty();
        testForEachRemainingNullAction();
    }

    static void testForEachRemainingWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s).append(",");
        deque.forEachRemaining(consumer);
        String output = result.toString();
        if (!output.equals("one,two,three,")) {
            throw new AssertionError("Expected 'one,two,three,', but got: " + output);
        }
    }

    static void testForEachRemainingEmpty() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s).append(",");
        deque.forEachRemaining(consumer);
        if (result.length() != 0) {
            throw new AssertionError("Expected empty result, but got: " + result.toString());
        }
    }

    static void testForEachRemainingNullAction() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("test");
        try {
            deque.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
