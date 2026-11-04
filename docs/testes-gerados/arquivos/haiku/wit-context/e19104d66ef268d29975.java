/*
 * @test
 * @summary Witup-derived check that the sub-list ListIterator remove() of CopyOnWriteArrayList throws UnsupportedOperationException and leaves the list unchanged
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ListIterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testRemoveWithoutNextThrowsUOE() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});
        List<String> sub = list.subList(0, 2);
        ListIterator<String> it = sub.listIterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "remove() without next() must throw UnsupportedOperationException");
        check(list.size() == 3, "list size must be unchanged after failed remove()");
    }

    static void testRemoveAfterNextThrowsUOE() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});
        List<String> sub = list.subList(0, 2);
        ListIterator<String> it = sub.listIterator();
        check(it.hasNext(), "sub-list iterator should have a next element");
        check("a".equals(it.next()), "first next() should return \"a\"");
        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "remove() after next() must throw UnsupportedOperationException");
        check(list.size() == 3, "list size must be unchanged after failed remove()");
        check("a".equals(list.get(0)), "element at index 0 must be unchanged");
    }

    public static void main(String[] args) {
        testRemoveWithoutNextThrowsUOE();
        testRemoveAfterNextThrowsUOE();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
