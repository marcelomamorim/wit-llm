/*
 * @test
 * @summary Behavioral tests for ArrayList spliterator forEachRemaining (public API path)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsAllElementsInOrder();
        testVisitsOnlyRemainingAfterTryAdvance();
        testEmptyListNoCalls();
        testStructuralModificationDuringTraversalThrowsCME();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Spliterator<Integer> sp = list.spliterator();
        try {
            sp.forEachRemaining(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testVisitsAllElementsInOrder() {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        List<Integer> seen = new ArrayList<>();
        list.spliterator().forEachRemaining(e -> seen.add(e));
        check(seen.size() == 3, "expected 3 elements, got " + seen.size());
        check(seen.get(0) == 10, "element 0 mismatch");
        check(seen.get(1) == 20, "element 1 mismatch");
        check(seen.get(2) == 30, "element 2 mismatch");
    }

    static void testVisitsOnlyRemainingAfterTryAdvance() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Spliterator<Integer> sp = list.spliterator();
        List<Integer> first = new ArrayList<>();
        check(sp.tryAdvance(e -> first.add(e)), "tryAdvance should succeed");
        check(first.size() == 1 && first.get(0) == 1, "tryAdvance consumed wrong element");
        List<Integer> rest = new ArrayList<>();
        sp.forEachRemaining(e -> rest.add(e));
        check(rest.size() == 2, "expected 2 remaining, got " + rest.size());
        check(rest.get(0) == 2 && rest.get(1) == 3, "remaining elements mismatch");
    }

    static void testEmptyListNoCalls() {
        List<Integer> list = new ArrayList<>();
        int[] count = new int[1];
        list.spliterator().forEachRemaining(e -> count[0]++);
        check(count[0] == 0, "action should not be invoked for empty list");
    }

    static void testStructuralModificationDuringTraversalThrowsCME() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Spliterator<Integer> sp = list.spliterator();
        try {
            sp.forEachRemaining(e -> {
                if (e == 1) {
                    list.add(4);
                }
            });
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
