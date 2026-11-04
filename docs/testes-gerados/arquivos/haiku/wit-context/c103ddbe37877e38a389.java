/*
 * @test
 * @summary Witup-derived behavioral tests for LinkedBlockingDeque.retainAll
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testRetainsOnlyElementsInCollection();
        testReturnsFalseWhenNothingRemoved();
        testEmptyCollectionClearsDeque();
        testEmptyDequeReturnsFalse();
    }

    static void testNullArgumentThrowsNPE() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(Arrays.asList(1, 2, 3));
        boolean thrown = false;
        try {
            d.retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "retainAll(null) must throw NullPointerException");
        check(new ArrayList<>(d).equals(Arrays.asList(1, 2, 3)),
              "deque must be unchanged after retainAll(null)");
    }

    static void testRetainsOnlyElementsInCollection() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(Arrays.asList(1, 2, 3, 4));
        boolean changed = d.retainAll(Arrays.asList(2, 4, 6));
        check(changed, "retainAll must return true when elements are removed");
        check(new ArrayList<>(d).equals(Arrays.asList(2, 4)),
              "only elements contained in the argument must remain, in order");
        check(d.size() == 2, "size must be 2");
    }

    static void testReturnsFalseWhenNothingRemoved() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(Arrays.asList(1, 2));
        boolean changed = d.retainAll(Arrays.asList(1, 2, 3));
        check(!changed, "retainAll must return false when no element is removed");
        check(new ArrayList<>(d).equals(Arrays.asList(1, 2)), "deque must be unchanged");
    }

    static void testEmptyCollectionClearsDeque() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(Arrays.asList(1, 2));
        boolean changed = d.retainAll(Collections.<Integer>emptyList());
        check(changed, "retainAll with empty collection must return true for non-empty deque");
        check(d.isEmpty(), "deque must be empty after retainAll with empty collection");
    }

    static void testEmptyDequeReturnsFalse() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        List<Integer> other = Arrays.asList(1, 2);
        boolean changed = d.retainAll(other);
        check(!changed, "retainAll on empty deque must return false");
        check(d.isEmpty(), "empty deque must remain empty");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
