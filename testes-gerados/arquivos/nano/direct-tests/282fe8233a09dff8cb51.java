/*
 * @test    @summary Test retainAll method of LinkedBlockingQueue
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRetainAllWithEmptyCollection();
        testRetainAllWithMatchingElements();
        testRetainAllWithNonMatchingElements();
        testRetainAllWithNullCollection();
    }

    static void testRetainAllWithEmptyCollection() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        boolean changed = queue.retainAll(java.util.Collections.emptyList());
        assert changed : "Expected retainAll to return true when elements are removed";
        assert queue.size() == 0 : "Queue should be empty after retainAll with empty collection";
    }

    static void testRetainAllWithMatchingElements() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Collection<String> c = java.util.Arrays.asList("a", "b", "c");
        boolean changed = queue.retainAll(c);
        assert !changed : "Expected retainAll to return false when no elements are removed";
        assert queue.size() == 3 : "Queue should retain all elements";
    }

    static void testRetainAllWithNonMatchingElements() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Collection<Integer> c = java.util.Arrays.asList(4, 5, 6);
        boolean changed = queue.retainAll(c);
        assert changed : "Expected retainAll to return true when elements are removed";
        assert queue.size() == 0 : "Queue should be empty after removing non-matching elements";
    }

    static void testRetainAllWithNullCollection() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(1);
        try {
            queue.retainAll(null);
            assert false : "Expected NullPointerException when passing null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
