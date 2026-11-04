/*
 * @test
 * @summary Verifies LinkedBlockingDeque.retainAll retains only elements contained in the given collection and rejects null
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRetainsOnlyContainedElements();
        testReturnsFalseWhenNothingRemoved();
        testEmptyCollementRemovesAll();
        testEmptyDequeReturnsFalse();
        testDuplicatesAreKept();
        testNullThrowsNullPointerException();
        testBoundedDequeRetainAll();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRetainsOnlyContainedElements() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        boolean changed = d.retainAll(Arrays.asList(2, 4, 6));
        check(changed, "retainAll should return true when elements are removed");
        check(new ArrayList<Integer>(d).equals(Arrays.asList(2, 4)),
              "deque should contain only 2 and 4 in order");
    }

    static void testReturnsFalseWhenNothingRemoved() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<Integer>(Arrays.asList(1, 2, 3));
        boolean changed = d.retainAll(Arrays.asList(1, 2, 3, 4));
        check(!changed, "retainAll should return false when no element is removed");
        check(new ArrayList<Integer>(d).equals(Arrays.asList(1, 2, 3)),
              "deque content should be unchanged");
    }

    static void testEmptyCollementRemovesAll() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<Integer>(Arrays.asList(1, 2, 3));
        boolean changed = d.retainAll(Collections.emptyList());
        check(changed, "retainAll with empty collection should return true");
        check(d.isEmpty(), "deque should be empty after retaining nothing");
    }

    static void testEmptyDequeReturnsFalse() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<Integer>();
        boolean changed = d.retainAll(Arrays.asList(1, 2));
        check(!changed, "retainAll on empty deque should return false");
        check(d.isEmpty(), "empty deque should remain empty");
    }

    static void testDuplicatesAreKept() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<Integer>(Arrays.asList(1, 1, 2, 3));
        boolean changed = d.retainAll(Arrays.asList(1));
        check(changed, "retainAll should return true when 2 and 3 are removed");
        check(new ArrayList<Integer>(d).equals(Arrays.asList(1, 1)),
              "both occurrences of 1 should be retained");
    }

    static void testNullThrowsNullPointerException() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<Integer>(Arrays.asList(1, 2, 3));
        boolean thrown = false;
        try {
            d.retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "retainAll(null) should throw NullPointerException");
        check(new ArrayList<Integer>(d).equals(Arrays.asList(1, 2, 3)),
              "deque should be unchanged after null argument");
    }

    static void testBoundedDequeRetainAll() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>(10);
        d.add("a");
        d.add("b");
        d.add("c");
        boolean changed = d.retainAll(Arrays.asList("b"));
        check(changed, "bounded deque retainAll should return true");
        check(new ArrayList<String>(d).equals(Arrays.asList("b")),
              "bounded deque should contain only b");
        check(d.remainingCapacity() == 9, "remaining capacity should reflect one element");
    }
}
