/*
 * @test
 * @summary Witup-derived behavioral tests for LinkedBlockingDeque.removeIf
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testEmptyDequeReturnsFalse();
        testRemovesMatchingElementsPreservingOrder();
        testNoMatchLeavesDequeUnchanged();
        testBoundedDequeFreesCapacityAfterRemoval();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFilterThrowsNPE() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        try {
            deque.removeIf(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        check(deque.size() == 1, "deque changed after rejected null filter");
    }

    static void testEmptyDequeReturnsFalse() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        check(!deque.removeIf(x -> true), "removeIf on empty deque should return false");
        check(deque.isEmpty(), "empty deque should remain empty");
    }

    static void testRemovesMatchingElementsPreservingOrder() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        for (int i = 1; i <= 6; i++) {
            deque.add(i);
        }
        check(deque.removeIf(x -> x % 2 == 0), "removeIf should report removal");
        check(deque.size() == 3, "expected 3 remaining elements");
        check(deque.pollFirst() == 1, "expected first element 1");
        check(deque.pollFirst() == 3, "expected second element 3");
        check(deque.pollFirst() == 5, "expected third element 5");
        check(deque.isEmpty(), "deque should be empty");
    }

    static void testNoMatchLeavesDequeUnchanged() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(3);
        check(!deque.removeIf(x -> x > 10), "removeIf without matches should return false");
        check(deque.size() == 2, "size should be unchanged");
        check(deque.pollFirst() == 1, "first element should be unchanged");
        check(deque.pollFirst() == 3, "second element should be unchanged");
    }

    static void testBoundedDequeFreesCapacityAfterRemoval() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(3);
        deque.add(1);
        deque.add(2);
        deque.add(3);
        check(deque.removeIf(x -> x == 2), "removeIf should remove element 2");
        deque.add(4);
        check(deque.size() == 3, "expected size 3 after refilling capacity");
    }
}
