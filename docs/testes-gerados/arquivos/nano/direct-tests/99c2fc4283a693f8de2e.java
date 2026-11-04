/*
 * @test
 * @summary Tests tryAdvance method of LinkedTransferQueue for correct behavior.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceWithEmptyQueue();
        testTryAdvanceWithNullConsumer();
    }

    static void testTryAdvanceWithElements() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        final boolean[] called = {false};
        Consumer<Integer> consumer = (Integer e) -> {
            called[0] = true;
            assert e != null : "Element should not be null";
            assert e >= 1 && e <= 3 : "Element out of expected range";
        };
        boolean result = queue.tryAdvance(consumer);
        assert result : "tryAdvance should return true when elements are present";
        assert called[0] : "Consumer should be called";
    }

    static void testTryAdvanceWithEmptyQueue() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        final boolean[] called = {false};
        Consumer<Integer> consumer = (Integer e) -> {
            called[0] = true;
        };
        boolean result = queue.tryAdvance(consumer);
        assert !result : "tryAdvance should return false when queue is empty";
        assert !called[0] : "Consumer should not be called when queue is empty";
    }

    static void testTryAdvanceWithNullConsumer() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.offer(10);
        try {
            queue.tryAdvance(null);
            assert false : "NullPointerException expected";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
