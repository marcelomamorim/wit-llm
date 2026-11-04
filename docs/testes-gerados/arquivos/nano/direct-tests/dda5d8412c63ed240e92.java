/* @test    @summary Test removeAll method of ArrayDeque for various collection inputs    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithMatchingElements();
        testRemoveAllWithNonMatchingElements();
        testRemoveAllWithNullCollection();
    }

    static void testRemoveAllWithEmptyCollection() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3));
        Collection<Integer> empty = Arrays.asList();
        boolean changed = deque.removeAll(empty);
        assert !changed : "Removing empty collection should not change deque";
        assert deque.size() == 3 : "Deque size should remain 3";
    }

    static void testRemoveAllWithMatchingElements() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3, 4, 5));
        Collection<Integer> toRemove = Arrays.asList(2, 4);
        boolean changed = deque.removeAll(toRemove);
        assert changed : "Deque should be changed after removing matching elements";
        assert deque.size() == 3 : "Deque size should be 3 after removal";
        assert deque.contains(1) && deque.contains(3) && deque.contains(5) : "Remaining elements should be 1, 3, 5";
    }

    static void testRemoveAllWithNonMatchingElements() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3));
        Collection<Integer> toRemove = Arrays.asList(4, 5);
        boolean changed = deque.removeAll(toRemove);
        assert !changed : "Deque should not change when no matching elements";
        assert deque.size() == 3 : "Deque size should remain 3";
    }

    static void testRemoveAllWithNullCollection() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3));
        try {
            deque.removeAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
