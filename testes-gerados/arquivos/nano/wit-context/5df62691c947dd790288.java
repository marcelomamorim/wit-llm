/*
 * @test
 * @summary Test for LinkedTransferQueue forEachRemaining method behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;
import java.util.NoSuchElementException;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
        testForEachRemainingWithEmptyQueue();
    }

    static void testForEachRemainingWithNullAction() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        try {
            queue.forEachRemaining(null);
            System.err.println("Failed: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testForEachRemainingWithValidAction() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        queue.forEachRemaining(consumer);
        String result = sb.toString();
        if (!result.contains("123")) {
            System.err.println("Failed: forEachRemaining did not process all elements correctly, result: " + result);
        }
    }

    static void testForEachRemainingWithEmptyQueue() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        queue.forEachRemaining(consumer); // should not throw
        if (sb.length() != 0) {
            System.err.println("Failed: forEachRemaining on empty queue should not modify StringBuilder, length: " + sb.length());
        }
    }
}
