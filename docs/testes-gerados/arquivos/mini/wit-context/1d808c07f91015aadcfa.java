/*
 * @test
 * @summary Test fail-fast behavior and exception paths of java.util.ArrayList.ListItr.set(E)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.IllegalStateException;
import java.util.ListIterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSetThrowsIllegalStateException();
        testSetThrowsConcurrentModificationException();
        testSetUpdatesElement();
    }

    private static void testSetThrowsIllegalStateException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        // lastRet < 0 means set() called before next() or previous()
        try {
            it.set("x");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testSetThrowsConcurrentModificationException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        it.next(); // lastRet set to 0
        // Modify list structurally outside iterator
        list.add("b");
        try {
            it.set("x");
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    private static void testSetUpdatesElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        String first = it.next();
        if (!"a".equals(first)) {
            throw new RuntimeException("Unexpected element returned by next()");
        }
        it.set("x");
        if (!"x".equals(list.get(0))) {
            throw new RuntimeException("set() did not update element correctly");
        }
    }
}
