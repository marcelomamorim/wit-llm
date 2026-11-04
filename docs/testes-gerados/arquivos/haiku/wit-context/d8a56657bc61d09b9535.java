/*
 * @test
 * @summary Witup-derived tests for ConcurrentLinkedDeque.removeAll
 * @run main ConcurrentLinkedDequeWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testRemovesOnlyMatchingElements();
        testNoMatchReturnsFalse();
        testEmptyCollectionReturnsFalse();
        testEmptyDequeReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullArgumentThrowsNPE() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>(Arrays.asList(1, 2));
        try {
            d.removeAll(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        check(new ArrayList<>(d).equals(Arrays.asList(1, 2)), "deque modified after NPE");
    }

    static void testRemovesOnlyMatchingElements() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>(Arrays.asList(1, 2, 3, 4));
        check(d.removeAll(Arrays.asList(2, 4)), "removeAll should return true when elements removed");
        check(new ArrayList<>(d).equals(Arrays.asList(1, 3)), "unexpected contents after removeAll");
    }

    static void testNoMatchReturnsFalse() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>(Arrays.asList(1, 2, 3));
        check(!d.removeAll(Arrays.asList(7, 8)), "removeAll should return false when nothing matches");
        check(new ArrayList<>(d).equals(Arrays.asList(1, 2, 3)), "deque changed without matches");
    }

    static void testEmptyCollectionReturnsFalse() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>(Arrays.asList(1, 2));
        check(!d.removeAll(Collections.emptyList()), "removeAll with empty collection should return false");
        check(new ArrayList<>(d).equals(Arrays.asList(1, 2)), "deque changed by empty removeAll");
    }

    static void testEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>();
        check(!d.removeAll(Arrays.asList(1)), "removeAll on empty deque should return false");
        check(d.isEmpty(), "empty deque should remain empty");
    }
}
