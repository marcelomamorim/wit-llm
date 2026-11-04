/*
 * @test
 * @summary Tests for LinkedBlockingDeque methods, focusing on removeIf behavior.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfWithPredicate();
    }

    private static void testRemoveIfNullPredicate() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.removeIf(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null predicate");
        }
    }

    private static void testRemoveIfWithPredicate() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Predicate<Integer> predicate = (Integer i) -> i % 2 == 1; // remove odd numbers
        boolean result = deque.removeIf(predicate);
        if (!result) {
            throw new AssertionError("removeIf should return true when elements are removed");
        }
        if (deque.contains(1) || deque.contains(3)) {
            throw new AssertionError("Deque should not contain removed elements");
        }
        if (!deque.contains(2)) {
            throw new AssertionError("Deque should still contain 2");
        }
    }
}
