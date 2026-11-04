/*
 * @test
 * @summary Verifies Iterator.remove() of ConcurrentLinkedDeque throws IllegalStateException when no element was returned by next(), and removes the last returned element otherwise.
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalState();
        testRemoveAfterNextRemovesElement();
        testSecondRemoveWithoutNextThrowsIllegalState();
        System.out.println("PASS");
    }

    static void testRemoveBeforeNextThrowsIllegalState() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        deque.add(2);
        Iterator<Integer> it = deque.iterator();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(deque.size() == 2, "deque must be unchanged");
    }

    static void testRemoveAfterNextRemovesElement() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Iterator<Integer> it = deque.iterator();
        Integer first = it.next();
        check(first.intValue() == 1, "first element must be 1");
        it.remove();
        check(deque.size() == 2, "size must be 2 after remove");
        check(!deque.contains(1), "removed element must not be present");
        check(deque.peekFirst().intValue() == 2, "head must be 2");
    }

    static void testSecondRemoveWithoutNextThrowsIllegalState() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(10);
        deque.add(20);
        Iterator<Integer> it = deque.iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new RuntimeException("expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(deque.size() == 1, "only one element must remain");
        check(deque.peekFirst().intValue() == 20, "remaining element must be 20");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
