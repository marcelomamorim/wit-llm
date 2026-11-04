/*
 * @test
 * @summary Verifies LinkedBlockingDeque.removeFirst returns head elements in FIFO order, throws NoSuchElementException when empty, and works with a bounded capacity
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveFirstOrder();
        testRemoveFirstOnEmptyThrows();
        testRemoveFirstAfterDrainThrows();
        testRemoveFirstWithBoundedCapacity();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testRemoveFirstOrder() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addFirst("z");
        check("z".equals(deque.removeFirst()), "first removeFirst should return z");
        check("a".equals(deque.removeFirst()), "second removeFirst should return a");
        check("b".equals(deque.removeFirst()), "third removeFirst should return b");
        check(deque.isEmpty(), "deque should be empty after removing all elements");
    }

    static void testRemoveFirstOnEmptyThrows() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        try {
            deque.removeFirst();
            throw new AssertionError("expected NoSuchElementException on empty deque");
        } catch (NoSuchElementException expected) {
            // expected
        }
        check(deque.isEmpty(), "deque should remain empty after failed removeFirst");
    }

    static void testRemoveFirstAfterDrainThrows() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        check(deque.removeFirst() == 1, "removeFirst should return 1");
        try {
            deque.removeFirst();
            throw new AssertionError("expected NoSuchElementException after draining");
        } catch (NoSuchElementException expected) {
            // expected
        }
        check(deque.size() == 0, "size should be 0 after drain");
    }

    static void testRemoveFirstWithBoundedCapacity() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(1);
        deque.addLast(1);
        check(deque.removeFirst() == 1, "removeFirst should return 1 from bounded deque");
        deque.addLast(2);
        check(deque.removeFirst() == 2, "removeFirst should return 2 after reinsertion");
        check(deque.isEmpty(), "bounded deque should be empty");
    }
}
