/*
 * @test
 * @summary Witup-derived tests for ArrayDeque.removeIf behavior and null filter handling
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testRemovesMatchingElementsPreservingOrder();
        testNoMatchReturnsFalse();
        testAllMatchEmptiesDeque();
        testEmptyDequeReturnsFalse();
        testRemoveIfAfterHeadWraparound();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFilterThrowsNPE() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        boolean thrown = false;
        try {
            deque.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) must throw NullPointerException");
        check(deque.toString().equals("[1, 2]"), "deque must be unchanged after NPE");
    }

    static void testRemovesMatchingElementsPreservingOrder() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 1; i <= 5; i++) {
            deque.add(i);
        }
        boolean changed = deque.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf must return true when elements are removed");
        check(deque.toString().equals("[1, 3, 5]"),
              "unexpected contents after removeIf: " + deque);
    }

    static void testNoMatchReturnsFalse() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(3);
        boolean changed = deque.removeIf(x -> x % 2 == 0);
        check(!changed, "removeIf must return false when no element matches");
        check(deque.toString().equals("[1, 3]"),
              "deque must be unchanged when nothing matches: " + deque);
    }

    static void testAllMatchEmptiesDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(2);
        deque.add(4);
        deque.add(6);
        boolean changed = deque.removeIf(x -> true);
        check(changed, "removeIf must return true when all elements match");
        check(deque.isEmpty(), "deque must be empty when all elements match");
        check(deque.size() == 0, "size must be 0 after removing all elements");
    }

    static void testEmptyDequeReturnsFalse() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        boolean changed = deque.removeIf(x -> true);
        check(!changed, "removeIf on empty deque must return false");
        check(deque.isEmpty(), "empty deque must remain empty");
    }

    static void testRemoveIfAfterHeadWraparound() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(4);
        deque.addLast(1);
        deque.addLast(2);
        deque.addFirst(0);
        deque.addFirst(-1);
        deque.addLast(3);
        boolean changed = deque.removeIf(x -> x < 0 || x == 2);
        check(changed, "removeIf must return true for wrapped deque");
        check(deque.toString().equals("[0, 1, 3]"),
              "unexpected contents after wraparound removeIf: " + deque);
    }
}
