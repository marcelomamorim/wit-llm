/*
 * @test
 * @summary Test LinkedBlockingQueue.forEachRemaining with various queue states and consumers
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue.Node;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingEmptyQueue();
        testForEachRemainingSingleElement();
        testForEachRemainingMultipleElements();
        testForEachRemainingConsumerNull();
    }

    static void testForEachRemainingEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        // Create iterator from queue
        LinkedBlockingQueue<String>.Itr itr = queue.new Itr();
        final AtomicInteger count = new AtomicInteger(0);
        itr.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
            }
        });
        if (count.get() != 0) {
            throw new AssertionError("Expected 0 elements processed for empty queue, got " + count.get());
        }
    }

    static void testForEachRemainingSingleElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        LinkedBlockingQueue<String>.Itr itr = queue.new Itr();
        final AtomicInteger count = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();
        itr.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
                sb.append(s);
            }
        });
        if (count.get() != 1) {
            throw new AssertionError("Expected 1 element processed, got " + count.get());
        }
        if (!"one".equals(sb.toString())) {
            throw new AssertionError("Expected 'one' but got '" + sb.toString() + "'");
        }
    }

    static void testForEachRemainingMultipleElements() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");
        LinkedBlockingQueue<String>.Itr itr = queue.new Itr();
        final StringBuilder sb = new StringBuilder();
        itr.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });
        String result = sb.toString();
        if (!result.contains("one") || !result.contains("two") || !result.contains("three")) {
            throw new AssertionError("Expected all elements processed but got: " + result);
        }
    }

    static void testForEachRemainingConsumerNull() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<String>.Itr itr = queue.new Itr();
        try {
            itr.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
