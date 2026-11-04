/*
 * @test    @summary Test retainAll method of PriorityBlockingQueue
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithExistingElements();
        testRetainAllWithNoMatchingElements();
        testRetainAllWithEmptyCollection();
        testRetainAllWithNullCollection();
    }

    static void testRetainAllWithExistingElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3, 4, 5));
        Collection<Integer> c = Arrays.asList(2, 4);
        boolean changed = queue.retainAll(c);
        assert changed : "Expected retainAll to return true when elements are removed";
        assert queue.size() == 2 : "Queue should contain 2 elements after retainAll";
        assert queue.contains(2) : "Queue should contain 2";
        assert queue.contains(4) : "Queue should contain 4";
        assert !queue.contains(1) : "Queue should not contain 1";
        assert !queue.contains(3) : "Queue should not contain 3";
        assert !queue.contains(5) : "Queue should not contain 5";
    }

    static void testRetainAllWithNoMatchingElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        Collection<Integer> c = Arrays.asList(4, 5);
        boolean changed = queue.retainAll(c);
        assert changed : "Expected retainAll to return true when all elements are removed";
        assert queue.isEmpty() : "Queue should be empty after retainAll with no matching elements";
    }

    static void testRetainAllWithEmptyCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        Collection<Integer> c = Arrays.asList();
        boolean changed = queue.retainAll(c);
        assert changed : "Expected retainAll to return true when all elements are removed";
        assert queue.isEmpty() : "Queue should be empty after retainAll with empty collection";
    }

    static void testRetainAllWithNullCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        try {
            queue.retainAll(null);
            assert false : "Expected NullPointerException when passing null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
