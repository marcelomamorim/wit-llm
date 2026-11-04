/*
 * @test
 * @summary Black-box tests for ConcurrentLinkedDeque.removeAll(Collection)
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveAllRemovesMatchingElements();
        testRemoveAllReturnsFalseWhenNoMatch();
        testRemoveAllWithEmptyCollectionLeavesDeque();
        testRemoveAllOnEmptyDequeReturnsFalse();
        testRemoveAllWithAllElementsEmptiesDeque();
        testRemoveAllNullThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveAllRemovesMatchingElements() {
        ConcurrentLinkedDeque<Integer> d =
            new ConcurrentLinkedDeque<>(Arrays.asList(1, 2, 3, 4, 5));
        boolean changed = d.removeAll(Arrays.asList(2, 4));
        check(changed, "removeAll should return true when elements are removed");
        check(new ArrayList<>(d).equals(Arrays.asList(1, 3, 5)),
              "remaining elements should be [1, 3, 5] but were " + new ArrayList<>(d));
        check(d.size() == 3, "size should be 3");
    }

    static void testRemoveAllReturnsFalseWhenNoMatch() {
        ConcurrentLinkedDeque<Integer> d =
            new ConcurrentLinkedDeque<>(Arrays.asList(1, 2, 3));
        boolean changed = d.removeAll(Arrays.asList(9, 10));
        check(!changed, "removeAll should return false when nothing matches");
        check(new ArrayList<>(d).equals(Arrays.asList(1, 2, 3)),
              "deque should be unchanged");
    }

    static void testRemoveAllWithEmptyCollectionLeavesDeque() {
        ConcurrentLinkedDeque<Integer> d =
            new ConcurrentLinkedDeque<>(Arrays.asList(1, 2, 3));
        boolean changed = d.removeAll(Collections.emptyList());
        check(!changed, "removeAll with empty collection should return false");
        check(d.size() == 3, "size should remain 3");
    }

    static void testRemoveAllOnEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>();
        boolean changed = d.removeAll(Arrays.asList(1, 2));
        check(!changed, "removeAll on empty deque should return false");
        check(d.isEmpty(), "deque should still be empty");
    }

    static void testRemoveAllWithAllElementsEmptiesDeque() {
        ConcurrentLinkedDeque<Integer> d =
            new ConcurrentLinkedDeque<>(Arrays.asList(7, 8, 9));
        List<Integer> all = new ArrayList<>(Arrays.asList(7, 8, 9));
        boolean changed = d.removeAll(all);
        check(changed, "removeAll should return true");
        check(d.isEmpty(), "deque should be empty after removing all elements");
    }

    static void testRemoveAllNullThrowsNullPointerException() {
        ConcurrentLinkedDeque<Integer> d =
            new ConcurrentLinkedDeque<>(Arrays.asList(1, 2));
        try {
            d.removeAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(c)
        }
        check(d.size() == 2, "deque should be unchanged after NPE");
    }
}
