/* @test    @summary Test tryAdvance method of LinkedBlockingDeque for correct iteration behavior    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceWithEmptyDeque();
        testTryAdvanceNullConsumer();
    }

    static void testTryAdvanceWithElements() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        final StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e);
        boolean hasNext = true;
        while (hasNext) {
            hasNext = deque.tryAdvance(consumer);
        }
        assert result.toString().equals("123") : "Expected '123', but got '" + result.toString() + "'";
    }

    static void testTryAdvanceWithEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        final StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> result.append(e);
        boolean hasNext = deque.tryAdvance(consumer);
        assert !hasNext : "Expected false for empty deque";
        assert result.toString().equals("") : "Expected empty result, but got '" + result.toString() + "'";
    }

    static void testTryAdvanceNullConsumer() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        boolean exceptionThrown = false;
        try {
            deque.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when passing null consumer";
    }
}
