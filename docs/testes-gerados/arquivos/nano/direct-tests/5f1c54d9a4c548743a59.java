/*
 * @test    @summary Test retainAll method of ArrayBlockingQueue
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithExistingElements();
        testRetainAllWithEmptyCollection();
        testRetainAllWithNoMatchingElements();
        testRetainAllWithNullCollection();
    }

    static void testRetainAllWithExistingElements() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        queue.addAll(Arrays.asList(1, 2, 3, 4, 5));
        Collection<Integer> c = Arrays.asList(2, 4);
        boolean changed = queue.retainAll(c);
        assert changed : "Expected queue to change";
        assert queue.size() == 2 : "Expected size 2";
        assert queue.contains(2) : "Queue should contain 2";
        assert queue.contains(4) : "Queue should contain 4";
    }

    static void testRetainAllWithEmptyCollection() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        queue.addAll(Arrays.asList(10, 20, 30));
        Collection<Integer> c = Arrays.asList();
        boolean changed = queue.retainAll(c);
        assert changed : "Expected queue to change when retaining empty collection";
        assert queue.isEmpty() : "Queue should be empty after retainAll with empty collection";
    }

    static void testRetainAllWithNoMatchingElements() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(4);
        queue.addAll(Arrays.asList(1, 2, 3, 4));
        Collection<Integer> c = Arrays.asList(5, 6);
        boolean changed = queue.retainAll(c);
        assert changed : "Expected queue to change when no elements match";
        assert queue.isEmpty() : "Queue should be empty when no elements match";
    }

    static void testRetainAllWithNullCollection() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.addAll(Arrays.asList(7, 8));
        try {
            queue.retainAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
