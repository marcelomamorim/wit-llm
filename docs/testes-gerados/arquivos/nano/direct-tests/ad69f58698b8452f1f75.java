/*
 * @test
 * @summary Test removeIf method of ConcurrentLinkedDeque
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Predicate;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoMatchingElements();
        testRemoveIfNullPredicate();
    }

    static void testRemoveIfRemovesMatchingElements() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        deque.add(4);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        boolean result = deque.removeIf(isEven);
        assert result : "Expected removeIf to return true when elements are removed";
        assert deque.size() == 2 : "Deque should have 2 elements after removal";
        assert deque.contains(1) : "Deque should contain 1";
        assert deque.contains(3) : "Deque should contain 3";
        assert !deque.contains(2) : "Deque should not contain 2";
        assert !deque.contains(4) : "Deque should not contain 4";
    }

    static void testRemoveIfNoMatchingElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        Predicate<String> isNumber = s -> s.matches("\\d+");
        boolean result = deque.removeIf(isNumber);
        assert !result : "Expected removeIf to return false when no elements are removed";
        assert deque.size() == 2 : "Deque should still have 2 elements";
        assert deque.contains("a") : "Deque should contain 'a'";
        assert deque.contains("b") : "Deque should contain 'b'";
    }

    static void testRemoveIfNullPredicate() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        try {
            deque.removeIf(null);
            assert false : "Expected NullPointerException when predicate is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
