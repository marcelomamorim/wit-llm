/*
 * @test
 * @summary removeFirst must throw NoSuchElementException on an empty deque and return the head otherwise
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testEmptyDequeThrows(new LinkedBlockingDeque<Integer>());
        testEmptyBoundedDequeThrows(new LinkedBlockingDeque<Integer>(4));
        testReturnsHeadAndShrinks();
        testEmptyAfterDrainThrows();
        testBoundedReturnsElement();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyDequeThrows(LinkedBlockingDeque<Integer> deque) {
        try {
            deque.removeFirst();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
        check(deque.size() == 0, "deque must remain empty");
    }

    static void testEmptyBoundedDequeThrows(LinkedBlockingDeque<Integer> deque) {
        try {
            deque.removeFirst();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testReturnsHeadAndShrinks() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<Integer>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addFirst(0);
        int first = deque.removeFirst();
        check(first == 0, "removeFirst must return head 0 but was " + first);
        int second = deque.removeFirst();
        check(second == 1, "removeFirst must return 1 but was " + second);
        check(deque.size() == 1, "size must be 1 but was " + deque.size());
        int third = deque.removeFirst();
        check(third == 2, "removeFirst must return 2 but was " + third);
    }

    static void testEmptyAfterDrainThrows() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<Integer>();
        deque.addLast(7);
        check(deque.removeFirst() == 7, "removeFirst must return 7");
        try {
            deque.removeFirst();
            throw new RuntimeException("expected NoSuchElementException after drain");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testBoundedReturnsElement() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<Integer>(4);
        deque.addFirst(42);
        int value = deque.removeFirst();
        check(value == 42, "removeFirst must return 42 but was " + value);
        check(deque.size() == 0, "deque must be empty after removal");
    }
}
