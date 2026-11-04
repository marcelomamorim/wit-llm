/*
 * @test    @summary Test removeAll(Collection<?>) method of PriorityBlockingQueue
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithExistingElements();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNonExistingElements();
        testRemoveAllWithNull();
    }

    static void testRemoveAllWithExistingElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3, 4, 5));
        Collection<Integer> toRemove = Arrays.asList(2, 4);
        boolean changed = queue.removeAll(toRemove);
        assert changed : "Expected queue to be changed";
        assert queue.size() == 3 : "Expected size 3, but got " + queue.size();
        assert !queue.contains(2) : "Queue should not contain 2";
        assert !queue.contains(4) : "Queue should not contain 4";
        assert queue.containsAll(Arrays.asList(1, 3, 5)) : "Queue should contain 1, 3, 5";
    }

    static void testRemoveAllWithEmptyCollection() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.addAll(Arrays.asList(10, 20, 30));
        Collection<Integer> empty = Arrays.asList();
        boolean changed = queue.removeAll(empty);
        assert !changed : "Expected no change when removing empty collection";
        assert queue.size() == 3 : "Size should remain 3";
    }

    static void testRemoveAllWithNonExistingElements() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.addAll(Arrays.asList(7, 8, 9));
        Collection<Integer> nonExisting = Arrays.asList(1, 2);
        boolean changed = queue.removeAll(nonExisting);
        assert !changed : "Expected no change when removing non-existing elements";
        assert queue.size() == 3 : "Size should remain 3";
    }

    static void testRemoveAllWithNull() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.addAll(Arrays.asList(1, 2, 3));
        try {
            queue.removeAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
