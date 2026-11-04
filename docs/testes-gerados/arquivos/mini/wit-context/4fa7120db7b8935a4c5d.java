/*
 * @test
 * @summary Test PriorityBlockingQueue.forEachRemaining with null and normal Consumer
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testForEachRemainingWithNull();
        testForEachRemainingWithValidConsumer();
    }

    private static void testForEachRemainingWithNull() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testForEachRemainingWithValidConsumer() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(10);
        queue.add(5);
        queue.add(20);

        final StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = new Consumer<Integer>() {
            public void accept(Integer i) {
                sb.append(i).append(",");
            }
        };

        queue.forEachRemaining(consumer);

        String result = sb.toString();
        if (result.isEmpty()) {
            throw new RuntimeException("Consumer was not called as expected");
        }
    }
}
