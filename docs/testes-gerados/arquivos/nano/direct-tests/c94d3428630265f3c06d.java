/*
 * @test
 * @summary Test removeIf method of ArrayDeque to ensure it correctly removes elements based on predicate.
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.function.Predicate;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoMatchingElements();
        testRemoveIfAllElementsMatch();
        testRemoveIfEmptyDeque();
    }

    static void testRemoveIfRemovesMatchingElements() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        deque.add(4);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        boolean result = deque.removeIf(isEven);
        assert result : "Expected removeIf to return true when elements are removed";
        assert deque.size() == 2 : "Expected size 2 after removal";
        assert deque.contains(1) : "Deque should contain 1";
        assert deque.contains(3) : "Deque should contain 3";
        assert !deque.contains(2) : "Deque should not contain 2";
        assert !deque.contains(4) : "Deque should not contain 4";
    }

    static void testRemoveIfNoMatchingElements() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        Predicate<String> isNumber = s -> s.matches("\\d+");
        boolean result = deque.removeIf(isNumber);
        assert !result : "Expected removeIf to return false when no elements are removed";
        assert deque.size() == 2 : "Size should remain 2";
        assert deque.contains("a") : "Deque should contain 'a'";
        assert deque.contains("b") : "Deque should contain 'b'";
    }

    static void testRemoveIfAllElementsMatch() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(2);
        deque.add(4);
        deque.add(6);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        boolean result = deque.removeIf(isEven);
        assert result : "Expected removeIf to return true when all elements are removed";
        assert deque.isEmpty() : "Deque should be empty after removal";
    }

    static void testRemoveIfEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        Predicate<Integer> anyPredicate = x -> true;
        boolean result = deque.removeIf(anyPredicate);
        assert !result : "Expected removeIf to return false on empty deque";
        assert deque.isEmpty() : "Deque should remain empty";
    }
}
