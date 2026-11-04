/*
 * @test
 * @summary Tests the tryAdvance method of LinkedBlockingQueue for correct iteration behavior.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceWithEmptyQueue();
        testTryAdvanceWithNullConsumer();
    }

    static void testTryAdvanceWithElements() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        final StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e).append(",");
        boolean hasNext = queue.tryAdvance(consumer);
        assert hasNext : "Expected tryAdvance to return true when elements are present";
        assert result.toString().equals("1,") : "Expected first element to be processed";
    }

    static void testTryAdvanceWithEmptyQueue() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        final StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e).append(",");
        boolean hasNext = queue.tryAdvance(consumer);
        assert !hasNext : "Expected tryAdvance to return false when queue is empty";
        assert result.length() == 0 : "No elements should be processed when queue is empty";
    }

    static void testTryAdvanceWithNullConsumer() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.offer(1);
        try {
            queue.tryAdvance(null);
            assert false : "Expected NullPointerException when consumer is null";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
