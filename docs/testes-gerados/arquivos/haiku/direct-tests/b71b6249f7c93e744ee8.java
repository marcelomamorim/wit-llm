/*
 * @test
 * @summary Verifies public Iterator.remove() behavior of ArrayDeque, including IllegalStateException when no element was returned by next().
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrows();
        testRemoveAfterNextRemovesReturnedElement();
        testSecondRemoveWithoutNextThrows();
        testIteratorRemoveKeepsRemainingOrder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testRemoveBeforeNextThrows() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.add("a");
        Iterator<String> it = d.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(d.size() == 1, "deque must be unchanged after failed remove()");
    }

    static void testRemoveAfterNextRemovesReturnedElement() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.add("a");
        d.add("b");
        d.add("c");
        Iterator<String> it = d.iterator();
        check("a".equals(it.next()), "next() should return first element");
        it.remove();
        check(d.size() == 2, "size should decrease by one");
        check(!d.contains("a"), "removed element must no longer be present");
        check("b".equals(d.peekFirst()), "head should now be b");
    }

    static void testSecondRemoveWithoutNextThrows() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.add("x");
        d.add("y");
        Iterator<String> it = d.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without intervening next() must throw IllegalStateException");
        check(d.size() == 1, "only one removal should have occurred");
    }

    static void testIteratorRemoveKeepsRemainingOrder() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.add("a");
        d.add("b");
        d.add("c");
        Iterator<String> it = d.iterator();
        while (it.hasNext()) {
            if ("b".equals(it.next())) {
                it.remove();
            }
        }
        check(d.size() == 2, "two elements should remain");
        check("a".equals(d.pollFirst()), "first remaining element should be a");
        check("c".equals(d.pollFirst()), "second remaining element should be c");
        check(d.isEmpty(), "deque should be empty");
    }
}
