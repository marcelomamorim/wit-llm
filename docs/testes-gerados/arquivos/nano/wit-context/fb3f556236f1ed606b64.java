/*
 * @test
 * @summary Test for LinkedBlockingQueue forEachRemaining method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    private static void testForEachRemainingWithNullAction() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.offer(1);
        queue.offer(2);
        boolean exceptionThrown = false;
        try {
            queue.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testForEachRemainingWithValidAction() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.offer(10);
        queue.offer(20);
        final StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e).append(",");
        queue.forEachRemaining(consumer);
        String output = result.toString();
        if (!output.equals("10,20,")) {
            throw new AssertionError("Unexpected output: " + output);
        }
        // Verify that subsequent call does not process again
        result.setLength(0);
        queue.forEachRemaining(consumer); // Should not process any elements
        if (result.length() != 0) {
            throw new AssertionError("Expected no processing after exhaustion, but got: " + result);
        }
    }
}
