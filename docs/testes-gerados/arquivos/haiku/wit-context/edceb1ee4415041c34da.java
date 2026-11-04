/*
 * @test
 * @summary Witup-derived tests for LinkedBlockingDeque.getLast
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testEmptyThrowsNoSuchElement();
        testReturnsLastElement();
        testAfterRemoveLast();
        testBoundedCapacity();
        testFromCollection();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyThrowsNoSuchElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.getLast();
            check(false, "getLast on empty deque must throw NoSuchElementException");
        } catch (NoSuchElementException expected) {
            check(deque.isEmpty(), "deque must remain empty");
        }
    }

    static void testReturnsLastElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        check("b".equals(deque.getLast()), "getLast must return last added element");
        check(deque.size() == 2, "getLast must not remove elements");
        check("a".equals(deque.getFirst()), "first element must be unchanged");
    }

    static void testAfterRemoveLast() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("x");
        deque.addLast("y");
        deque.removeLast();
        check("x".equals(deque.getLast()), "getLast must reflect removal of last element");
        deque.removeLast();
        try {
            deque.getLast();
            check(false, "getLast after draining must throw NoSuchElementException");
        } catch (NoSuchElementException expected) {
            check(deque.isEmpty(), "deque must be empty after draining");
        }
    }

    static void testBoundedCapacity() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(1);
        check(deque.offerLast(Integer.valueOf(7)), "offerLast must succeed within capacity");
        check(Integer.valueOf(7).equals(deque.getLast()), "getLast must return element in bounded deque");
        check(!deque.offerLast(Integer.valueOf(8)), "offerLast must fail beyond capacity");
        check(Integer.valueOf(7).equals(deque.getLast()), "getLast must be unchanged after rejected offer");
    }

    static void testFromCollection() {
        LinkedBlockingDeque<String> deque =
            new LinkedBlockingDeque<>(Arrays.asList("p", "q", "r"));
        check("r".equals(deque.getLast()), "getLast must return last element of source collection");
        check(deque.size() == 3, "size must match source collection");
    }
}
