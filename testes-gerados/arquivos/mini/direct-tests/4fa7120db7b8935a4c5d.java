/*
 * @test
 * @summary Test PriorityBlockingQueue.forEachRemaining with various Consumers
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(10);
        queue.add(5);
        queue.add(20);

        final AtomicInteger sum = new AtomicInteger(0);
        Consumer<Integer> consumer = new Consumer<Integer>() {
            public void accept(Integer i) {
                sum.addAndGet(i);
            }
        };

        // Call forEachRemaining and verify sum of elements
        queue.iterator().forEachRemaining(consumer);

        // The sum should be 10 + 5 + 20 = 35
        if (sum.get() != 35) {
            throw new AssertionError("Sum mismatch: expected 35 but got " + sum.get());
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.iterator().forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null Consumer");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
