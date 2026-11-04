/*
 * @test
 * @summary Test for PriorityBlockingQueue's forEachRemaining method to ensure it applies the consumer to all elements.
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmpty();
        testForEachRemainingNullConsumer();
    }

    static void testForEachRemainingWithElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e);
        queue.forEachRemaining(consumer);
        boolean containsAll = result.toString().contains("1") && result.toString().contains("2") && result.toString().contains("3");
        if (!containsAll) {
            throw new AssertionError("forEachRemaining did not process all elements: " + result.toString());
        }
    }

    static void testForEachRemainingEmpty() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e);
        queue.forEachRemaining(consumer); // should not throw
        if (result.length() != 0) {
            throw new AssertionError("Expected no elements to be processed, but got: " + result.toString());
        }
    }

    static void testForEachRemainingNullConsumer() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        try {
            queue.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
