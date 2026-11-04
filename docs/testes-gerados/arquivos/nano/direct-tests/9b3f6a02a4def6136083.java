/* @test    @summary Test removeIf method of LinkedBlockingDeque for correctness and regressions    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoMatch();
        testRemoveIfAllMatch();
        testRemoveIfEmptyDeque();
    }

    static void testRemoveIfRemovesMatchingElements() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        deque.add(4);
        deque.add(5);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        boolean result = deque.removeIf(isEven);
        assert result : "Expected removeIf to return true when elements are removed";
        assert deque.size() == 3 : "Expected size 3 after removal";
        assert deque.contains(1) : "Deque should contain 1";
        assert deque.contains(3) : "Deque should contain 3";
        assert deque.contains(5) : "Deque should contain 5";
        assert !deque.contains(2) : "Deque should not contain 2";
        assert !deque.contains(4) : "Deque should not contain 4";
    }

    static void testRemoveIfNoMatch() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        Predicate<String> isDigit = s -> s.matches("\\d+");
        boolean result = deque.removeIf(isDigit);
        assert !result : "Expected removeIf to return false when no elements are removed";
        assert deque.size() == 3 : "Size should remain 3";
        assert deque.contains("a") : "Deque should contain 'a'";
        assert deque.contains("b") : "Deque should contain 'b'";
        assert deque.contains("c") : "Deque should contain 'c'";
    }

    static void testRemoveIfAllMatch() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(2);
        deque.add(4);
        deque.add(6);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        boolean result = deque.removeIf(isEven);
        assert result : "Expected removeIf to return true when all elements are removed";
        assert deque.isEmpty() : "Deque should be empty after removal";
    }

    static void testRemoveIfEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        Predicate<Integer> anyPredicate = x -> true;
        boolean result = deque.removeIf(anyPredicate);
        assert !result : "Expected removeIf to return false on empty deque";
        assert deque.isEmpty() : "Deque should remain empty";
    }
}
