/*
 * @test
 * @summary Checks ConcurrentLinkedDeque iterator remove() behavior: ISE without next, removal of last returned element, and double remove.
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutNextThrowsAndKeepsContents();
        testRemoveRemovesLastReturnedElement();
        testRemoveTwiceThrows();
        testRemoveAfterSecondNextRemovesSecondElement();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveWithoutNextThrowsAndKeepsContents() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        Iterator<String> it = d.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(d.size() == 1, "size must be unchanged after failed remove");
        check(d.contains("a"), "element must still be present");
    }

    static void testRemoveRemovesLastReturnedElement() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        d.add("c");
        Iterator<String> it = d.iterator();
        String first = it.next();
        check("a".equals(first), "first next() must return a");
        it.remove();
        check(!d.contains("a"), "removed element must not be present");
        check(d.size() == 2, "size must be 2 after remove");
        check("b".equals(d.peekFirst()), "b must be the new first element");
        check("c".equals(d.peekLast()), "c must be the last element");
    }

    static void testRemoveTwiceThrows() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        Iterator<String> it = d.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second consecutive remove() must throw IllegalStateException");
        check(d.isEmpty(), "deque must remain empty after failed second remove");
    }

    static void testRemoveAfterSecondNextRemovesSecondElement() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        d.add("c");
        Iterator<String> it = d.iterator();
        it.next();
        String second = it.next();
        check("b".equals(second), "second next() must return b");
        it.remove();
        check(!d.contains("b"), "b must be removed");
        check(d.contains("a") && d.contains("c"), "a and c must remain");
        check(d.size() == 2, "size must be 2");
        String third = it.next();
        check("c".equals(third), "next() after remove must return c");
        it.remove();
        check(!d.contains("c"), "c must be removed");
        check(d.size() == 1 && "a".equals(d.peekFirst()), "only a must remain");
    }
}
