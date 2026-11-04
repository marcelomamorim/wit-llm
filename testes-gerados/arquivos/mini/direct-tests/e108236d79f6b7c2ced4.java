/*
 * @test
 * @summary Test LinkedTransferQueue.forEachRemaining with various Consumers
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Objects;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithEmptyQueue();
        testForEachRemainingWithNonEmptyQueue();
    }

    static void testForEachRemainingWithNullConsumer() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.forEachRemaining(null);
            throw new Error("Expected NullPointerException for null Consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingWithEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        final AtomicInteger count = new AtomicInteger(0);
        queue.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
            }
        });
        if (count.get() != 0) {
            throw new Error("forEachRemaining on empty queue should not invoke Consumer");
        }
    }

    static void testForEachRemainingWithNonEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        final StringBuilder sb = new StringBuilder();
        queue.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        String result = sb.toString();
        if (!result.contains("a") || !result.contains("b") || !result.contains("c")) {
            throw new Error("forEachRemaining did not process all elements: " + result);
        }
    }
}
