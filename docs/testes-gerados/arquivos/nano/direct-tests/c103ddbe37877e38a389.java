/*
 * @test    @summary Test retainAll method of LinkedBlockingDeque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Collection;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRetainAllWithEmptyDeque();
        testRetainAllWithAllElementsRetained();
        testRetainAllWithSomeElementsRemoved();
        testRetainAllWithNullCollection();
    }

    static void testRetainAllWithEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        Collection<Integer> c = java.util.Arrays.asList(1, 2, 3);
        boolean changed = deque.retainAll(c);
        assert !changed : "RetainAll on empty deque should return false";
        assert deque.isEmpty() : "Deque should remain empty";
    }

    static void testRetainAllWithAllElementsRetained() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Collection<Integer> c = java.util.Arrays.asList(1, 2, 3);
        boolean changed = deque.retainAll(c);
        assert !changed : "RetainAll with all elements retained should return false";
        assert deque.size() == 3 : "Deque should contain 3 elements";
    }

    static void testRetainAllWithSomeElementsRemoved() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Collection<Integer> c = java.util.Arrays.asList(2, 3);
        boolean changed = deque.retainAll(c);
        assert changed : "RetainAll with some elements removed should return true";
        assert deque.size() == 2 : "Deque should contain 2 elements";
        assert deque.contains(2) : "Deque should contain 2";
        assert deque.contains(3) : "Deque should contain 3";
        assert !deque.contains(1) : "Deque should not contain 1";
    }

    static void testRetainAllWithNullCollection() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        try {
            deque.retainAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
