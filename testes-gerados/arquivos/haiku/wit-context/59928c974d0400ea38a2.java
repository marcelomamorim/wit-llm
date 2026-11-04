/*
 * @test
 * @summary Witup-derived checks for LinkedBlockingDeque.forEach
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testEmptyDequeDoesNotInvokeAction();
        testVisitsElementsInFirstToLastOrder();
        testCollectionConstructorElementsAreVisited();
        testBoundedDequeElementsAreVisited();
    }

    static void testNullActionThrowsNullPointerException() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        try {
            deque.forEach(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyDequeDoesNotInvokeAction() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        List<String> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.isEmpty(), "action invoked on empty deque: " + seen);
    }

    static void testVisitsElementsInFirstToLastOrder() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(0);
        List<Integer> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.equals(Arrays.asList(0, 1, 2, 3)),
              "unexpected visit order: " + seen);
    }

    static void testCollectionConstructorElementsAreVisited() {
        LinkedBlockingDeque<String> deque =
            new LinkedBlockingDeque<>(Arrays.asList("x", "y", "z"));
        List<String> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.equals(Arrays.asList("x", "y", "z")),
              "unexpected elements: " + seen);
    }

    static void testBoundedDequeElementsAreVisited() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(10);
        deque.add(7);
        deque.add(8);
        List<Integer> seen = new ArrayList<>();
        deque.forEach(seen::add);
        check(seen.equals(Arrays.asList(7, 8)),
              "unexpected elements on bounded deque: " + seen);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
