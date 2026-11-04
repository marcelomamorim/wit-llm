/*
 * @test
 * @summary WITUP-derived tests for ArrayDeque.removeAll null handling and observable removal behavior
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNullPointerException();
        testRemovesOnlyElementsContainedInArgument();
        testReturnsFalseWhenNoElementMatches();
        testEmptyArgumentLeavesDequeUnchanged();
        testEmptyDequeReturnsFalse();
    }

    static void testNullArgumentThrowsNullPointerException() {
        ArrayDeque<Integer> d = new ArrayDeque<>(Arrays.asList(1, 2, 3));
        try {
            d.removeAll(null);
        } catch (NullPointerException e) {
            check(d.size() == 3, "deque must be unchanged after NPE");
            check(d.contains(Integer.valueOf(1)), "element 1 must remain");
            return;
        }
        throw new RuntimeException("expected NullPointerException for null argument");
    }

    static void testRemovesOnlyElementsContainedInArgument() {
        ArrayDeque<Integer> d = new ArrayDeque<>(Arrays.asList(1, 2, 3, 2, 4));
        boolean changed = d.removeAll(Arrays.asList(2, 4));
        check(changed, "removeAll must return true when elements are removed");
        check(d.size() == 2, "expected 2 remaining elements but was " + d.size());
        check(d.contains(Integer.valueOf(1)), "1 must remain");
        check(d.contains(Integer.valueOf(3)), "3 must remain");
        check(!d.contains(Integer.valueOf(2)), "2 must be removed");
        check(!d.contains(Integer.valueOf(4)), "4 must be removed");
    }

    static void testReturnsFalseWhenNoElementMatches() {
        ArrayDeque<Integer> d = new ArrayDeque<>(Arrays.asList(1, 2, 3));
        boolean changed = d.removeAll(Arrays.asList(7, 8));
        check(!changed, "removeAll must return false when nothing matches");
        check(d.size() == 3, "deque size must stay 3");
    }

    static void testEmptyArgumentLeavesDequeUnchanged() {
        ArrayDeque<Integer> d = new ArrayDeque<>(Arrays.asList(1, 2, 3));
        boolean changed = d.removeAll(Collections.emptyList());
        check(!changed, "removeAll with empty collection must return false");
        check(d.size() == 3, "deque size must stay 3");
    }

    static void testEmptyDequeReturnsFalse() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        boolean changed = d.removeAll(Arrays.asList(1, 2));
        check(!changed, "removeAll on empty deque must return false");
        check(d.isEmpty(), "deque must remain empty");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
