/*
 * @test
 * @summary Test ConcurrentLinkedDeque.forEachRemaining with null and non-null Consumer
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullConsumer();
        testForEachRemainingWithElements();
    }

    private static void testForEachRemainingNullConsumer() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        boolean npeThrown = false;
        try {
            deque.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null Consumer to forEachRemaining");
        }
    }

    private static void testForEachRemainingWithElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("one");
        deque.add("two");
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };
        deque.forEachRemaining(consumer);
        String result = sb.toString();
        if (!(result.contains("one") && result.contains("two"))) {
            throw new AssertionError("forEachRemaining did not process all elements: " + result);
        }
    }
}
