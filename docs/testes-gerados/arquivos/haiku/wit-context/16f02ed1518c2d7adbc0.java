/*
 * @test
 * @summary Verifies LinkedBlockingDeque iterator remove() IllegalStateException and removal behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalStateException();
        testRemoveAfterNextRemovesElement();
        testSecondRemoveWithoutNextThrowsIllegalStateException();
        testRemoveAfterElementAlreadyRemovedIsNoOp();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveBeforeNextThrowsIllegalStateException() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>();
        deque.add("a");
        Iterator<String> it = deque.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(deque.size() == 1, "deque must be unchanged after failed remove()");
    }

    static void testRemoveAfterNextRemovesElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>();
        deque.add("a");
        deque.add("b");
        Iterator<String> it = deque.iterator();
        String first = it.next();
        check("a".equals(first), "first element must be a");
        it.remove();
        check(deque.size() == 1, "size must be 1 after iterator remove()");
        check(!deque.contains("a"), "removed element must not be present");
        check(deque.contains("b"), "other element must remain");
    }

    static void testSecondRemoveWithoutNextThrowsIllegalStateException() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>();
        deque.add("a");
        deque.add("b");
        Iterator<String> it = deque.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without next() must throw IllegalStateException");
        check(deque.size() == 1, "deque must not lose further elements");
        check(deque.contains("b"), "remaining element must still be present");
    }

    static void testRemoveAfterElementAlreadyRemovedIsNoOp() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>();
        deque.add("a");
        deque.add("b");
        Iterator<String> it = deque.iterator();
        it.next();
        check(deque.remove("a"), "direct remove of a must succeed");
        it.remove();
        check(deque.size() == 1, "size must remain 1 when element was already removed");
        check(deque.contains("b"), "remaining element must still be present");
    }
}
