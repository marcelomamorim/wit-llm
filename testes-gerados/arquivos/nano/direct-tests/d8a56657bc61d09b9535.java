/*
 * @test
 * @summary Test removeAll(Collection<?>) method of ConcurrentLinkedDeque
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Collection;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithExistingElements();
        testRemoveAllWithNoMatchingElements();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNullCollection();
    }

    static void testRemoveAllWithExistingElements() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3, 4, 5));
        Collection<Integer> toRemove = Arrays.asList(2, 4);
        boolean result = deque.removeAll(toRemove);
        assert result : "Expected removeAll to return true when elements are removed";
        assert deque.size() == 3 : "Deque size should be 3 after removal";
        assert deque.contains(1) : "Deque should contain 1";
        assert deque.contains(3) : "Deque should contain 3";
        assert deque.contains(5) : "Deque should contain 5";
        assert !deque.contains(2) : "Deque should not contain 2";
        assert !deque.contains(4) : "Deque should not contain 4";
    }

    static void testRemoveAllWithNoMatchingElements() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3));
        Collection<Integer> toRemove = Arrays.asList(4, 5);
        boolean result = deque.removeAll(toRemove);
        assert !result : "Expected removeAll to return false when no elements are removed";
        assert deque.size() == 3 : "Deque size should remain 3";
        assert deque.contains(1) : "Deque should contain 1";
        assert deque.contains(2) : "Deque should contain 2";
        assert deque.contains(3) : "Deque should contain 3";
    }

    static void testRemoveAllWithEmptyCollection() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3));
        Collection<Integer> toRemove = Arrays.asList();
        boolean result = deque.removeAll(toRemove);
        assert !result : "Expected removeAll to return false when collection is empty";
        assert deque.size() == 3 : "Deque size should remain 3";
        assert deque.contains(1) : "Deque should contain 1";
        assert deque.contains(2) : "Deque should contain 2";
        assert deque.contains(3) : "Deque should contain 3";
    }

    static void testRemoveAllWithNullCollection() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3));
        try {
            deque.removeAll(null);
            assert false : "Expected NullPointerException when passing null";
        } catch (NullPointerException e) {
            // expected
        }
        assert deque.size() == 3 : "Deque size should remain 3 after exception";
        assert deque.contains(1) : "Deque should contain 1";
        assert deque.contains(2) : "Deque should contain 2";
        assert deque.contains(3) : "Deque should contain 3";
    }
}
