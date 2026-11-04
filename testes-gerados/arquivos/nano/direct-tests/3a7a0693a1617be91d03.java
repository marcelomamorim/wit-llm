/*
 * @test
 * @summary Test tryAdvance method of ConcurrentLinkedDeque for correct iteration behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceWithEmptyDeque();
        testTryAdvanceNullConsumer();
    }

    static void testTryAdvanceWithElements() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        final boolean[] called = {false};
        Consumer<Integer> consumer = e -> {
            assert e != null : "Element should not be null";
            called[0] = true;
        };
        boolean result = deque.tryAdvance(consumer);
        assert result : "tryAdvance should return true when element is processed";
        assert called[0] : "Consumer should be called";
    }

    static void testTryAdvanceWithEmptyDeque() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        final boolean[] called = {false};
        Consumer<Integer> consumer = e -> {
            called[0] = true;
        };
        boolean result = deque.tryAdvance(consumer);
        assert !result : "tryAdvance should return false when deque is empty";
        assert !called[0] : "Consumer should not be called when deque is empty";
    }

    static void testTryAdvanceNullConsumer() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(10);
        boolean exceptionThrown = false;
        try {
            deque.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "tryAdvance should throw NullPointerException when consumer is null";
    }
}
