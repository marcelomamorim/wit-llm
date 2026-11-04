/* @test    @summary Test for PriorityBlockingQueue.forEachRemaining method    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingEmpty();
        testForEachRemainingNullAction();
    }

    static void testForEachRemainingWithElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(3);
        queue.add(1);
        queue.add(2);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e).append(",");
        queue.forEachRemaining(consumer);
        String output = result.toString();
        boolean condition = output.equals("3,1,2,") || output.equals("1,2,3,") || output.equals("1,3,2,") || output.equals("2,1,3,") || output.equals("2,3,1,") || output.equals("3,2,1,");
        if (!condition) {
            throw new AssertionError("Unexpected output: " + output);
        }
    }

    static void testForEachRemainingEmpty() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e).append(",");
        queue.forEachRemaining(consumer); // should not throw
        if (result.length() != 0) {
            throw new AssertionError("Expected empty result, but got: " + result.toString());
        }
    }

    static void testForEachRemainingNullAction() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        try {
            queue.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
