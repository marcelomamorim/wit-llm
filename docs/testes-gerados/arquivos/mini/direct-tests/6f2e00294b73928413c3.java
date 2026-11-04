/*
 * @test
 * @summary Test LinkedBlockingQueue.forEach with various Consumers
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithCountingConsumer();
        testForEachWithEmptyQueue();
    }

    static void testForEachWithNullConsumer() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachWithCountingConsumer() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        final AtomicInteger count = new AtomicInteger(0);
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                Objects.requireNonNull(s);
                count.incrementAndGet();
            }
        };

        queue.forEach(consumer);

        if (count.get() != 3) {
            throw new RuntimeException("forEach did not visit all elements, visited: " + count.get());
        }
    }

    static void testForEachWithEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        final AtomicInteger count = new AtomicInteger(0);
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
            }
        };
        queue.forEach(consumer);
        if (count.get() != 0) {
            throw new RuntimeException("forEach on empty queue should not call consumer");
        }
    }
}
