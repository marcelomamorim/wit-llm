/*
 * @test
 * @summary Verifies ConcurrentLinkedDeque.retainAll retains only elements contained in the given collection, reports changes correctly, and rejects null collections.
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRetainsOnlyContainedElements();
        testReturnsFalseWhenNothingRemoved();
        testRemovesAllWhenNoneContained();
        testEmptyDequeReturnsFalse();
        testNullCollectionThrowsNpe();
    }

    static void testRetainsOnlyContainedElements() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>(Arrays.asList(1, 2, 3, 4, 5));
        boolean changed = d.retainAll(Arrays.asList(2, 4, 6));
        if (!changed) {
            throw new RuntimeException("retainAll should report modification");
        }
        List<Integer> actual = new ArrayList<>(d);
        if (!actual.equals(Arrays.asList(2, 4))) {
            throw new RuntimeException("Unexpected contents: " + actual);
        }
    }

    static void testReturnsFalseWhenNothingRemoved() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>(Arrays.asList(1, 2, 3));
        boolean changed = d.retainAll(Arrays.asList(1, 2, 3, 4));
        if (changed) {
            throw new RuntimeException("retainAll should return false when no element is removed");
        }
        List<Integer> actual = new ArrayList<>(d);
        if (!actual.equals(Arrays.asList(1, 2, 3))) {
            throw new RuntimeException("Unexpected contents: " + actual);
        }
    }

    static void testRemovesAllWhenNoneContained() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>(Arrays.asList(1, 2, 3));
        boolean changed = d.retainAll(Collections.<Integer>emptyList());
        if (!changed) {
            throw new RuntimeException("retainAll should report modification");
        }
        if (!d.isEmpty()) {
            throw new RuntimeException("Deque should be empty, was: " + d);
        }
    }

    static void testEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>();
        boolean changed = d.retainAll(Arrays.asList(1, 2));
        if (changed) {
            throw new RuntimeException("retainAll on empty deque should return false");
        }
        if (!d.isEmpty()) {
            throw new RuntimeException("Deque should remain empty");
        }
    }

    static void testNullCollectionThrowsNpe() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>(Arrays.asList(1, 2));
        try {
            d.retainAll(null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        List<Integer> actual = new ArrayList<>(d);
        if (!actual.equals(Arrays.asList(1, 2))) {
            throw new RuntimeException("Deque modified after failed call: " + actual);
        }
    }
}
