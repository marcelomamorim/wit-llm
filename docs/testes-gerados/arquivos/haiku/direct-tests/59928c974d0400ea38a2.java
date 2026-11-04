/*
 * @test
 * @summary Verifies LinkedBlockingDeque.forEach null-action handling, empty deque behavior, and first-to-last iteration order
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testEmptyDequeDoesNotInvokeAction();
        testVisitsElementsFirstToLast();
        testVisitsElementsAfterAddFirst();
        testConstructedFromCollectionPreservesOrder();
        testActionDoesNotRemoveElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNullPointerException() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        try {
            deque.forEach(null);
            throw new AssertionError("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyDequeDoesNotInvokeAction() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        List<Integer> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.isEmpty(), "action must not be invoked on empty deque");
        check(deque.isEmpty(), "deque must remain empty");
    }

    static void testVisitsElementsFirstToLast() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        List<Integer> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.equals(Arrays.asList(1, 2, 3)),
              "expected [1, 2, 3] but was " + seen);
    }

    static void testVisitsElementsAfterAddFirst() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(2);
        deque.addFirst(1);
        deque.addLast(3);
        List<Integer> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.equals(Arrays.asList(1, 2, 3)),
              "expected [1, 2, 3] after addFirst/addLast but was " + seen);
    }

    static void testConstructedFromCollectionPreservesOrder() {
        LinkedBlockingDeque<String> deque =
            new LinkedBlockingDeque<>(Arrays.asList("a", "b", "c"));
        List<String> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "expected [a, b, c] but was " + seen);
    }

    static void testActionDoesNotRemoveElements() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(10);
        deque.add(20);
        deque.forEach(x -> { });
        check(deque.size() == 2, "forEach must not change size");
        check(deque.peekFirst() == 10, "first element must remain 10");
        check(deque.peekLast() == 20, "last element must remain 20");
    }
}
