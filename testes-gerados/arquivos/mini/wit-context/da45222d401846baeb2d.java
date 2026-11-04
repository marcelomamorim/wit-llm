/*
 * @test
 * @summary Test LinkedList.add(E) for normal and concurrent modification behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ConcurrentModificationException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testAddNormal();
        testAddConcurrentModificationException();
    }

    private static void testAddNormal() {
        LinkedList<String> list = new LinkedList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        if (list.size() != 3) {
            throw new AssertionError("Expected size 3 but got " + list.size());
        }
        if (!"one".equals(list.get(0))) {
            throw new AssertionError("Expected first element 'one' but got " + list.get(0));
        }
        if (!"two".equals(list.get(1))) {
            throw new AssertionError("Expected second element 'two' but got " + list.get(1));
        }
        if (!"three".equals(list.get(2))) {
            throw new AssertionError("Expected third element 'three' but got " + list.get(2));
        }
    }

    private static void testAddConcurrentModificationException() {
        final LinkedList<String> list = new LinkedList<>();
        list.add("initial");

        // Create an iterator and modify the list structurally to cause ConcurrentModificationException
        java.util.ListIterator<String> it = list.listIterator();
        it.next(); // move iterator forward

        // Modify list directly to cause modCount != expectedModCount
        list.add("newElement");

        boolean cmeThrown = false;
        try {
            it.add("shouldFail");
        } catch (ConcurrentModificationException e) {
            cmeThrown = true;
        }

        if (!cmeThrown) {
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        }
    }
}
