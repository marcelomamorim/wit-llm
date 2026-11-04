/*
 * @test
 * @summary Test LinkedTransferQueue.forEach with various Consumers
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.Objects;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithEmptyQueue();
        testForEachWithMultipleElements();
    }

    static void testForEachWithNullConsumer() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.forEach(null);
            throw new Error("Expected NullPointerException for null Consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachWithEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        final AtomicInteger count = new AtomicInteger(0);
        queue.forEach(new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
            }
        });
        if (count.get() != 0) {
            throw new Error("forEach on empty queue should not invoke Consumer");
        }
    }

    static void testForEachWithMultipleElements() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        final StringBuilder sb = new StringBuilder();
        queue.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        String result = sb.toString();
        if (!result.contains("a") || !result.contains("b") || !result.contains("c")) {
            throw new Error("forEach did not visit all elements: " + result);
        }
    }
}
