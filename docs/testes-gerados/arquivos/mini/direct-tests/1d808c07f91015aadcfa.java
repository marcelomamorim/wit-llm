/*
 * @test
 * @summary Test java.util.ArrayList$ListItr.set(E) method behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testSetWithoutNextThrowsIllegalStateException();
        testSetAfterNextUpdatesElement();
        testSetAfterStructuralModificationThrowsConcurrentModificationException();
    }

    private static void testSetWithoutNextThrowsIllegalStateException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        try {
            it.set("b");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testSetAfterNextUpdatesElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        String next = it.next();
        if (!"a".equals(next)) {
            throw new RuntimeException("Unexpected next element: " + next);
        }
        it.set("b");
        if (!"b".equals(list.get(0))) {
            throw new RuntimeException("Element not updated by set");
        }
    }

    private static void testSetAfterStructuralModificationThrowsConcurrentModificationException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        it.next();
        // Structural modification outside iterator
        list.add("c");
        try {
            it.set("b");
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
