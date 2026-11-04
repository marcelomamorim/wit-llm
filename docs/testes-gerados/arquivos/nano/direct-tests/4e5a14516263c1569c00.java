/*
 * @test
 * @summary Test for ArrayBlockingQueue.forEachRemaining to ensure it applies the consumer to all elements.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmpty();
        testForEachRemainingNullConsumer();
    }

    static void testForEachRemainingWithElements() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e);
        queue.forEachRemaining(consumer);
        assert result.toString().equals("123") : "Expected '123', got '" + result + "'";
    }

    static void testForEachRemainingEmpty() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e);
        queue.forEachRemaining(consumer);
        assert result.toString().isEmpty() : "Expected empty string, got '" + result + "'";
    }

    static void testForEachRemainingNullConsumer() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        queue.add(42);
        try {
            queue.forEachRemaining(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
