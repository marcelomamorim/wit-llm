/*
 * @test
 * @summary Behavioral tests for ArrayDeque.removeAll(Collection) using only public API
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNullPointerException();
        testRemovesMatchingElements();
        testNoMatchReturnsFalseAndKeepsContents();
        testEmptyDequeReturnsFalse();
        testEmptyCollectionReturnsFalse();
        testRemovesAllOccurrencesOfDuplicates();
        testRemovingEverythingEmptiesDeque();
    }

    static void testNullArgumentThrowsNullPointerException() {
        ArrayDeque<Integer> d = new ArrayDeque<>(Arrays.asList(1, 2, 3));
        boolean thrown = false;
        try {
            d.removeAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeAll(null) must throw NullPointerException");
        check(contents(d).equals(Arrays.asList(1, 2, 3)),
              "deque must be unchanged after removeAll(null) failure");
    }

    static void testRemovesMatchingElements() {
        ArrayDeque<Integer> d = new ArrayDeque<>(Arrays.asList(1, 2, 3, 4));
        boolean changed = d.removeAll(Arrays.asList(2, 4));
        check(changed, "removeAll must return true when elements were removed");
        check(contents(d).equals(Arrays.asList(1, 3)),
              "expected [1, 3] after removing 2 and 4, got " + contents(d));
    }

    static void testNoMatchReturnsFalseAndKeepsContents() {
        ArrayDeque<Integer> d = new ArrayDeque<>(Arrays.asList(1, 2, 3));
        boolean changed = d.removeAll(Arrays.asList(7, 8));
        check(!changed, "removeAll must return false when nothing matches");
        check(contents(d).equals(Arrays.asList(1, 2, 3)),
              "contents must be unchanged when nothing matches");
    }

    static void testEmptyDequeReturnsFalse() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        boolean changed = d.removeAll(Arrays.asList(1, 2));
        check(!changed, "removeAll on empty deque must return false");
        check(d.isEmpty(), "empty deque must remain empty");
    }

    static void testEmptyCollectionReturnsFalse() {
        ArrayDeque<Integer> d = new ArrayDeque<>(Arrays.asList(1, 2, 3));
        boolean changed = d.removeAll(Collections.<Integer>emptyList());
        check(!changed, "removeAll with empty collection must return false");
        check(contents(d).equals(Arrays.asList(1, 2, 3)),
              "contents must be unchanged when removing empty collection");
    }

    static void testRemovesAllOccurrencesOfDuplicates() {
        ArrayDeque<Integer> d = new ArrayDeque<>(Arrays.asList(1, 2, 1, 3, 1));
        boolean changed = d.removeAll(Arrays.asList(1));
        check(changed, "removeAll must return true when duplicates are removed");
        check(contents(d).equals(Arrays.asList(2, 3)),
              "expected [2, 3] after removing all 1s, got " + contents(d));
    }

    static void testRemovingEverythingEmptiesDeque() {
        ArrayDeque<Integer> d = new ArrayDeque<>(Arrays.asList(5, 6, 7));
        boolean changed = d.removeAll(Arrays.asList(5, 6, 7, 8));
        check(changed, "removeAll must return true when all elements are removed");
        check(d.isEmpty(), "deque must be empty after removing all elements");
        check(d.size() == 0, "size must be 0 after removing all elements");
    }

    static List<Integer> contents(ArrayDeque<Integer> d) {
        return new ArrayList<>(d);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
