/*
 * @test    @summary Test removeAll(Collection<?>) method of ConcurrentLinkedQueue
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Collection;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithExistingElements();
        testRemoveAllWithNoMatchingElements();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNullCollection();
    }

    static void testRemoveAllWithExistingElements() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Collection<Integer> c = java.util.Arrays.asList(2, 3);
        boolean changed = queue.removeAll(c);
        assert changed : "Expected queue to be changed";
        assert queue.size() == 1 : "Expected size 1";
        assert queue.contains(1) : "Queue should contain 1";
        assert !queue.contains(2) : "Queue should not contain 2";
        assert !queue.contains(3) : "Queue should not contain 3";
    }

    static void testRemoveAllWithNoMatchingElements() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        queue.add(2);
        Collection<Integer> c = java.util.Arrays.asList(3, 4);
        boolean changed = queue.removeAll(c);
        assert !changed : "Expected queue not to be changed";
        assert queue.size() == 2 : "Expected size 2";
        assert queue.contains(1) : "Queue should contain 1";
        assert queue.contains(2) : "Queue should contain 2";
    }

    static void testRemoveAllWithEmptyCollection() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        queue.add(2);
        Collection<Integer> c = java.util.Collections.emptyList();
        boolean changed = queue.removeAll(c);
        assert !changed : "Expected queue not to be changed";
        assert queue.size() == 2 : "Expected size 2";
        assert queue.contains(1) : "Queue should contain 1";
        assert queue.contains(2) : "Queue should contain 2";
    }

    static void testRemoveAllWithNullCollection() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        queue.add(2);
        try {
            queue.removeAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
