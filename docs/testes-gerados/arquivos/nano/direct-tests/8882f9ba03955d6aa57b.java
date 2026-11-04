/*
 * @test
 * @summary Test the forEach method of PriorityBlockingQueue for correct iteration and action application.
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmptyQueue();
        testForEachNullAction();
    }

    static void testForEachWithElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(3);
        queue.add(1);
        queue.add(2);
        StringBuilder result = new StringBuilder();
        queue.forEach(e -> result.append(e).append(","));
        String output = result.toString();
        boolean condition = output.contains("1,") && output.contains("2,") && output.contains("3,");
        if (!condition) {
            throw new AssertionError("forEach did not process all elements correctly: " + output);
        }
    }

    static void testForEachWithEmptyQueue() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        StringBuilder result = new StringBuilder();
        queue.forEach(e -> result.append(e));
        if (result.length() != 0) {
            throw new AssertionError("forEach on empty queue should not process any elements.");
        }
    }

    static void testForEachNullAction() {
        PriorityBlockingQueue<Double> queue = new PriorityBlockingQueue<>();
        queue.add(1.1);
        try {
            queue.forEach(null);
            throw new AssertionError("Expected NullPointerException when passing null to forEach.");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
