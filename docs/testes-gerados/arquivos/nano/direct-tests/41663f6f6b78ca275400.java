/*
 * @test
 * @summary Test retainAll method of ConcurrentLinkedDeque for correct behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Collection;
import java.util.Arrays;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRetainAllWithExistingElements();
        testRetainAllWithEmptyCollection();
        testRetainAllWithNoMatchingElements();
    }

    static void testRetainAllWithExistingElements() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3, 4, 5));
        Collection<Integer> c = Arrays.asList(2, 4);
        boolean changed = deque.retainAll(c);
        assert changed : "Expected retainAll to return true when elements are removed";
        assert deque.size() == 2 : "Deque should contain 2 elements after retainAll";
        assert deque.contains(2) : "Deque should contain 2";
        assert deque.contains(4) : "Deque should contain 4";
        assert !deque.contains(1) : "Deque should not contain 1";
        assert !deque.contains(3) : "Deque should not contain 3";
        assert !deque.contains(5) : "Deque should not contain 5";
    }

    static void testRetainAllWithEmptyCollection() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3));
        Collection<Integer> c = Arrays.asList();
        boolean changed = deque.retainAll(c);
        assert changed : "Expected retainAll to return true when all elements are removed";
        assert deque.isEmpty() : "Deque should be empty after retainAll with empty collection";
    }

    static void testRetainAllWithNoMatchingElements() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.addAll(Arrays.asList(1, 2, 3));
        Collection<Integer> c = Arrays.asList(4, 5);
        boolean changed = deque.retainAll(c);
        assert changed : "Expected retainAll to return true when all elements are removed";
        assert deque.isEmpty() : "Deque should be empty after retainAll with no matching elements";
    }
}
