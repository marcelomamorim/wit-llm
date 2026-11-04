/*
 * @test
 * @summary Tests ConcurrentLinkedDeque.removeIf observable behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemovesMatchingElements();
        testNoMatchReturnsFalse();
        testRemovesAllElements();
        testEmptyDequeReturnsFalse();
        testNullFilterThrowsAndKeepsContent();
        testConstructorCollectionThenRemoveIf();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testRemovesMatchingElements() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        boolean changed = deque.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf should return true when elements are removed");
        List<Integer> expected = Arrays.asList(1, 3, 5);
        check(expected.equals(new ArrayList<>(deque)), "only odd elements should remain in order");
    }

    static void testNoMatchReturnsFalse() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>(Arrays.asList(1, 3, 5));
        boolean changed = deque.removeIf(x -> x % 2 == 0);
        check(!changed, "removeIf should return false when no element matches");
        List<Integer> expected = Arrays.asList(1, 3, 5);
        check(expected.equals(new ArrayList<>(deque)), "deque content should be unchanged when nothing matches");
    }

    static void testRemovesAllElements() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>(Arrays.asList(2, 4, 6));
        boolean changed = deque.removeIf(x -> true);
        check(changed, "removeIf should return true when all elements are removed");
        check(deque.isEmpty(), "deque should be empty after removing all elements");
    }

    static void testEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        boolean changed = deque.removeIf(x -> true);
        check(!changed, "removeIf on empty deque should return false");
        check(deque.isEmpty(), "empty deque should remain empty");
    }

    static void testNullFilterThrowsAndKeepsContent() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>(Arrays.asList(1, 2, 3));
        boolean thrown = false;
        try {
            deque.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf with null filter should throw NullPointerException");
        List<Integer> expected = Arrays.asList(1, 2, 3);
        check(expected.equals(new ArrayList<>(deque)), "deque content should be preserved after null filter");
    }

    static void testConstructorCollectionThenRemoveIf() {
        List<Integer> source = Arrays.asList(10, 11, 12, 13);
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>(source);
        boolean changed = deque.removeIf(x -> x >= 12);
        check(changed, "removeIf should remove elements from deque built from collection");
        List<Integer> expected = Arrays.asList(10, 11);
        check(expected.equals(new ArrayList<>(deque)), "remaining elements should be 10 and 11");
    }
}
