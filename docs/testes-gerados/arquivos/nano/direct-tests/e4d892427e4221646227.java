/*
 * @test    @summary Test removeAll method of ArrayBlockingQueue
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithExistingElements();
        testRemoveAllWithNoMatchingElements();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNullCollection();
    }

    static void testRemoveAllWithExistingElements() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        queue.addAll(Arrays.asList(1, 2, 3, 4, 5));
        Collection<Integer> toRemove = Arrays.asList(2, 4);
        boolean result = queue.removeAll(toRemove);
        assert result : "Expected removeAll to return true when elements are removed";
        assert queue.size() == 3 : "Queue size should be 3 after removal";
        assert queue.containsAll(Arrays.asList(1, 3, 5)) : "Queue should contain 1, 3, 5";
    }

    static void testRemoveAllWithNoMatchingElements() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        queue.addAll(Arrays.asList(10, 20, 30));
        Collection<Integer> toRemove = Arrays.asList(40, 50);
        boolean result = queue.removeAll(toRemove);
        assert !result : "Expected removeAll to return false when no elements are removed";
        assert queue.size() == 3 : "Queue size should remain 3";
        assert queue.containsAll(Arrays.asList(10, 20, 30)) : "Queue should contain original elements";
    }

    static void testRemoveAllWithEmptyCollection() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.addAll(Arrays.asList(7, 8));
        Collection<Integer> toRemove = Arrays.asList();
        boolean result = queue.removeAll(toRemove);
        assert !result : "Expected removeAll to return false when collection is empty";
        assert queue.size() == 2 : "Queue size should remain 2";
        assert queue.containsAll(Arrays.asList(7, 8)) : "Queue should contain 7 and 8";
    }

    static void testRemoveAllWithNullCollection() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.addAll(Arrays.asList(1, 2));
        try {
            queue.removeAll(null);
            assert false : "Expected NullPointerException when collection is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
