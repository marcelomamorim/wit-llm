/* @test    @summary Test tryAdvance method of ConcurrentLinkedQueue for basic behavior    @run main ConcurrentLinkedQueueWitupTest */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceEmpty();
        testTryAdvanceNullConsumer();
    }

    static void testTryAdvanceWithElements() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s);
        boolean hasNext = queue.tryAdvance(consumer);
        assert hasNext : "Expected tryAdvance to return true when elements are present";
        assert result.toString().equals("a") : "Expected first element to be 'a', got '" + result.toString() + "'";
    }

    static void testTryAdvanceEmpty() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s);
        boolean hasNext = queue.tryAdvance(consumer);
        assert !hasNext : "Expected tryAdvance to return false when queue is empty";
        assert result.length() == 0 : "Expected no elements to be processed";
    }

    static void testTryAdvanceNullConsumer() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("x");
        try {
            queue.tryAdvance(null);
            assert false : "Expected NullPointerException when consumer is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
