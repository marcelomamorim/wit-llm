/*
 * @test
 * @summary Verify LinkedBlockingDeque iterator remove() semantics
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutNextThrows();
        testRemoveAfterNextRemovesElement();
        testRemoveTwiceThrows();
        testRemoveMiddleElement();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testRemoveWithoutNextThrows() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
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

    static void testRemoveAfterNextRemovesElement() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.add("a");
        d.add("b");
        d.add("c");
        Iterator<String> it = d.iterator();
        check("a".equals(it.next()), "first next() must return a");
        it.remove();
        check(d.size() == 2, "size must decrease by one");
        check("b".equals(d.peekFirst()), "b must be the new head");
        check(!d.contains("a"), "a must have been removed");
    }

    static void testRemoveTwiceThrows() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.add("a");
        d.add("b");
        Iterator<String> it = d.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without next() must throw IllegalStateException");
        check(d.size() == 1 && "b".equals(d.peekFirst()), "deque must still contain only b");
    }

    static void testRemoveMiddleElement() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.add("a");
        d.add("b");
        d.add("c");
        Iterator<String> it = d.iterator();
        it.next();
        it.next();
        it.remove();
        check(d.toString().equals("[a, c]"), "removing middle element must yield [a, c] but was " + d);
    }
}
