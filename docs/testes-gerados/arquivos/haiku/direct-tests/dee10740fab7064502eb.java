/*
 * @test
 * @summary Black-box tests for CopyOnWriteArrayList.removeIf(Predicate)
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class CopyOnWriteArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemovesMatchingElements() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        boolean changed = list.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf should return true when elements are removed");
        check(list.size() == 3, "expected 3 elements after removal, got " + list.size());
        check(list.equals(Arrays.asList(1, 3, 5)), "unexpected remaining elements: " + list);
    }

    static void testNoMatchReturnsFalse() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[] {"a", "b"});
        boolean changed = list.removeIf(s -> s.equals("z"));
        check(!changed, "removeIf should return false when nothing matches");
        check(list.equals(Arrays.asList("a", "b")), "list should be unchanged");
    }

    static void testRemoveAllMatching() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[] {"x", "y"});
        boolean changed = list.removeIf(s -> true);
        check(changed, "removeIf should return true when all elements match");
        check(list.isEmpty(), "list should be empty");
    }

    static void testEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        boolean changed = list.removeIf(s -> true);
        check(!changed, "removeIf on empty list should return false");
        check(list.isEmpty(), "empty list should remain empty");
    }

    static void testNullPredicateThrowsNPE() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[] {"a"});
        boolean thrown = false;
        try {
            list.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) should throw NullPointerException");
        check(list.size() == 1 && list.get(0).equals("a"),
              "list should be unchanged after NPE");
    }

    static void testNullElementsAreHandled() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add(null);
        list.add("a");
        list.add(null);
        boolean changed = list.removeIf(s -> s == null);
        check(changed, "removeIf should remove null elements");
        check(list.size() == 1, "expected 1 element, got " + list.size());
        check("a".equals(list.get(0)), "remaining element should be a");
    }

    static void testPredicateAppliedToAllElements() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3, 4));
        int[] calls = new int[1];
        list.removeIf(x -> { calls[0]++; return x == 1; });
        check(calls[0] == 4, "predicate should be applied to every element, calls=" + calls[0]);
        check(list.equals(Arrays.asList(2, 3, 4)), "unexpected result: " + list);
    }

    static void testRepeatedRemoveIfIsStable() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3));
        Predicate<Integer> big = x -> x > 1;
        check(list.removeIf(big), "first call should remove elements");
        check(!list.removeIf(big), "second call should find nothing to remove");
        check(list.equals(Arrays.asList(1)), "unexpected final state: " + list);
    }

    public static void main(String[] args) {
        testRemovesMatchingElements();
        testNoMatchReturnsFalse();
        testRemoveAllMatching();
        testEmptyList();
        testNullPredicateThrowsNPE();
        testNullElementsAreHandled();
        testPredicateAppliedToAllElements();
        testRepeatedRemoveIfIsStable();
    }
}
