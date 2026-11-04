/*
 * @test
 * @summary Test tryAdvance method of PriorityBlockingQueue
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceEmpty();
        testTryAdvanceNullConsumer();
    }

    static void testTryAdvanceWithElements() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        final boolean[] called = {false};
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                called[0] = true;
                assert t != null : "Element should not be null";
                assert (t == 1 || t == 2 || t == 3) : "Unexpected element: " + t;
            }
        };
        boolean result = pq.tryAdvance(consumer);
        assert result : "tryAdvance should return true when elements are present";
        assert called[0] : "Consumer should be called";
    }

    static void testTryAdvanceEmpty() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        final boolean[] called = {false};
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                called[0] = true;
            }
        };
        boolean result = pq.tryAdvance(consumer);
        assert !result : "tryAdvance should return false when queue is empty";
        assert !called[0] : "Consumer should not be called when queue is empty";
    }

    static void testTryAdvanceNullConsumer() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        boolean exceptionThrown = false;
        try {
            pq.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "tryAdvance should throw NullPointerException when consumer is null";
    }
}
