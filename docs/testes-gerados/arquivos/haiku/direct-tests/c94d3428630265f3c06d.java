/*
 * @test
 * @summary Tests ArrayDeque.removeIf behavior: matching removal, order preservation, no-match, empty deque, null filter and wrapped buffer
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testRemovesMatchingElementsAndKeepsOrder();
        testNoMatchReturnsFalse();
        testEmptyDequeReturnsFalse();
        testRemoveAllMatchingEmptiesDeque();
        testNullFilterThrowsNullPointerException();
        testWrappedBufferRemoval();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<Integer> contents(ArrayDeque<Integer> deque) {
        return new ArrayList<>(deque);
    }

    static void testRemovesMatchingElementsAndKeepsOrder() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        boolean changed = deque.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf should return true when elements are removed");
        check(contents(deque).equals(Arrays.asList(1, 3, 5)),
              "unexpected contents after removing evens: " + contents(deque));
    }

    static void testNoMatchReturnsFalse() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(Arrays.asList(1, 3, 5));
        boolean changed = deque.removeIf(x -> x % 2 == 0);
        check(!changed, "removeIf should return false when nothing matches");
        check(contents(deque).equals(Arrays.asList(1, 3, 5)),
              "deque should be unchanged when nothing matches");
    }

    static void testEmptyDequeReturnsFalse() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        boolean changed = deque.removeIf(x -> true);
        check(!changed, "removeIf on empty deque should return false");
        check(deque.isEmpty(), "empty deque should remain empty");
    }

    static void testRemoveAllMatchingEmptiesDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(Arrays.asList(7, 8, 9));
        boolean changed = deque.removeIf(x -> true);
        check(changed, "removeIf should return true when all elements are removed");
        check(deque.isEmpty(), "deque should be empty after removing all elements");
        check(deque.size() == 0, "size should be 0 after removing all elements");
    }

    static void testNullFilterThrowsNullPointerException() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(Arrays.asList(1, 2, 3));
        boolean thrown = false;
        try {
            deque.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) should throw NullPointerException");
        check(contents(deque).equals(Arrays.asList(1, 2, 3)),
              "deque should be unchanged after null filter");
    }

    static void testWrappedBufferRemoval() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i <= 20; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i <= 10; i++) {
            deque.pollFirst();
        }
        for (int i = 21; i <= 30; i++) {
            deque.addLast(i);
        }
        boolean changed = deque.removeIf(x -> x % 2 != 0);
        check(changed, "removeIf should report removal in wrapped buffer");

        List<Integer> expected = new ArrayList<>();
        for (int v = 11; v <= 30; v++) {
            if (v % 2 == 0) {
                expected.add(v);
            }
        }
        check(contents(deque).equals(expected),
              "unexpected contents after removal in wrapped buffer: " + contents(deque));
    }
}
