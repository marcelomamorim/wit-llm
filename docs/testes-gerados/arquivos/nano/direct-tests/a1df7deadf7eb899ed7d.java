/*
 * @test    @summary Test for ConcurrentLinkedQueue.forEachRemaining method
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmpty();
        testForEachRemainingNullAction();
    }

    static void testForEachRemainingWithElements() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e).append(",");
        queue.forEachRemaining(consumer);
        String output = result.toString();
        if (!output.equals("1,2,3,")) {
            throw new AssertionError("Expected '1,2,3,', but got: " + output);
        }
    }

    static void testForEachRemainingEmpty() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e).append(",");
        queue.forEachRemaining(consumer);
        String output = result.toString();
        if (!output.isEmpty()) {
            throw new AssertionError("Expected empty string, but got: " + output);
        }
    }

    static void testForEachRemainingNullAction() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        boolean exceptionThrown = false;
        try {
            queue.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException to be thrown");
        }
    }
}
