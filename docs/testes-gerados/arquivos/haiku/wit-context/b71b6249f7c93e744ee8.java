/*
 * @test
 * @summary Verify ArrayDeque iterator remove() throws IllegalStateException when no element has been returned by next()
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrows();
        testRemoveAfterNextRemovesElement();
        testSecondRemoveWithoutNextThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testRemoveBeforeNextThrows() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        Iterator<String> it = deque.iterator();
        try {
            it.remove();
            throw new AssertionError("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(deque.size() == 2, "deque must be unchanged after failed remove");
    }

    static void testRemoveAfterNextRemovesElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        Iterator<String> it = deque.iterator();
        check("a".equals(it.next()), "first next() must return a");
        it.remove();
        check(deque.size() == 1, "size must be 1 after remove");
        check("b".equals(deque.peekFirst()), "remaining element must be b");
    }

    static void testSecondRemoveWithoutNextThrows() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        Iterator<String> it = deque.iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new AssertionError("expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(deque.size() == 1, "second failed remove must not change deque");
    }
}
