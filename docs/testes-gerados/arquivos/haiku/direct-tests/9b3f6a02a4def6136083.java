/*
 * @test
 * @summary Tests LinkedBlockingDeque.removeIf observable behavior: null filter, empty deque, no match, partial and full removal, order and capacity
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testEmptyDequeReturnsFalse();
        testNoMatchReturnsFalseAndKeepsElements();
        testSomeMatchRemovesAndPreservesOrder();
        testAllMatchEmptiesDeque();
        testCapacityUpdatedAfterRemoval();
    }

    static void testNullFilterThrowsNPE() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.add(1);
        try {
            d.removeIf(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(toList(d).equals(Arrays.asList(1)), "deque must be unchanged after NPE");
    }

    static void testEmptyDequeReturnsFalse() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        check(!d.removeIf(x -> true), "empty deque removeIf must return false");
        check(d.isEmpty(), "deque must remain empty");
    }

    static void testNoMatchReturnsFalseAndKeepsElements() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.add(1);
        d.add(3);
        d.add(5);
        Predicate<Integer> even = x -> x % 2 == 0;
        check(!d.removeIf(even), "removeIf with no match must return false");
        check(toList(d).equals(Arrays.asList(1, 3, 5)), "elements must be unchanged");
    }

    static void testSomeMatchRemovesAndPreservesOrder() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        for (int i = 1; i <= 6; i++) {
            d.addLast(i);
        }
        Predicate<Integer> even = x -> x % 2 == 0;
        check(d.removeIf(even), "removeIf with matches must return true");
        check(toList(d).equals(Arrays.asList(1, 3, 5)),
              "remaining elements must keep relative order: " + toList(d));
        check(d.peekFirst() == 1 && d.peekLast() == 5, "first/last must be updated");
    }

    static void testAllMatchEmptiesDeque() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.add(2);
        d.add(4);
        check(d.removeIf(x -> true), "removing all must return true");
        check(d.isEmpty(), "deque must be empty after removing all");
        check(d.size() == 0, "size must be 0");
    }

    static void testCapacityUpdatedAfterRemoval() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(3);
        d.add(1);
        d.add(2);
        d.add(3);
        check(d.remainingCapacity() == 0, "deque should be full");
        check(d.removeIf(x -> x == 2), "removeIf must remove element 2");
        check(d.remainingCapacity() == 1, "remaining capacity must be 1 after removal");
        check(d.offerLast(4), "offerLast must succeed after removal");
        check(toList(d).equals(Arrays.asList(1, 3, 4)), "unexpected contents: " + toList(d));
    }

    static List<Integer> toList(LinkedBlockingDeque<Integer> d) {
        return new ArrayList<>(d);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
