/*
 * @test
 * @summary Test ConcurrentLinkedQueue.CLQSpliterator.forEachRemaining behavior including null action
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.Objects;

public class ConcurrentLinkedQueueWitupTest {

    static class DummyConsumer<E> implements Consumer<E> {
        int count = 0;
        public void accept(E e) {
            count++;
        }
    }

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingWithElements();
    }

    // Test that forEachRemaining throws NullPointerException if action is null
    private static void testForEachRemainingNullAction() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);

        // Obtain spliterator from queue
        java.util.Spliterator<Integer> spliterator = queue.spliterator();

        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Test that forEachRemaining consumes all remaining elements
    private static void testForEachRemainingWithElements() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(10);
        queue.add(20);
        queue.add(30);

        java.util.Spliterator<Integer> spliterator = queue.spliterator();
        DummyConsumer<Integer> consumer = new DummyConsumer<>();

        spliterator.forEachRemaining(consumer);

        if (consumer.count != 3) {
            throw new RuntimeException("forEachRemaining did not consume all elements, count=" + consumer.count);
        }

        // Subsequent call should consume zero elements
        DummyConsumer<Integer> consumer2 = new DummyConsumer<>();
        spliterator.forEachRemaining(consumer2);
        if (consumer2.count != 0) {
            throw new RuntimeException("forEachRemaining after exhaustion should consume zero elements");
        }
    }
}
