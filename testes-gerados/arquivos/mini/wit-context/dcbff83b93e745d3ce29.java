/*
 * @test
 * @summary Test LinkedList.ListItr.set(E) for expected exceptions and normal behavior
 * @run main java.util.LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;
import java.lang.IllegalStateException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testSetThrowsIllegalStateException();
        testSetThrowsConcurrentModificationException();
        testSetReplacesElement();
    }

    private static void testSetThrowsIllegalStateException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        // lastReturned == null before next() or previous()
        try {
            it.set("b");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testSetThrowsConcurrentModificationException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        it.next(); // lastReturned != null now
        // Modify list structurally outside iterator
        list.add("b");
        try {
            it.set("c");
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    private static void testSetReplacesElement() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        String val = it.next();
        it.set("b");
        if (!"a".equals(val)) {
            throw new RuntimeException("Iterator next() returned unexpected value");
        }
        if (!"b".equals(list.get(0))) {
            throw new RuntimeException("set() did not replace element correctly");
        }
    }
}
