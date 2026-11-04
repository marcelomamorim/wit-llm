/*
 * @test    @summary Test for LinkedTransferQueue.forEachRemaining method
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyQueue();
    }

    static void testForEachRemainingWithElements() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
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

    static void testForEachRemainingWithEmptyQueue() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e).append(",");
        queue.forEachRemaining(consumer);
        String output = result.toString();
        if (!output.isEmpty()) {
            throw new AssertionError("Expected empty string, but got: " + output);
        }
    }
}
