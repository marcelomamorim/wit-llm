/*
 * @test    @summary Test for LinkedBlockingQueue.forEach method
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmptyQueue();
        testForEachNullConsumer();
    }

    static void testForEachWithElements() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        queue.forEach(consumer);
        if (!sb.toString().equals("123")) {
            throw new AssertionError("Expected '123', but got: " + sb.toString());
        }
    }

    static void testForEachWithEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = (String e) -> sb.append(e);
        queue.forEach(consumer);
        if (sb.length() != 0) {
            throw new AssertionError("Expected empty string, but got: " + sb.toString());
        }
    }

    static void testForEachNullConsumer() {
        LinkedBlockingQueue<Double> queue = new LinkedBlockingQueue<>();
        queue.offer(1.1);
        queue.offer(2.2);
        boolean exceptionThrown = false;
        try {
            queue.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null consumer");
        }
    }
}
