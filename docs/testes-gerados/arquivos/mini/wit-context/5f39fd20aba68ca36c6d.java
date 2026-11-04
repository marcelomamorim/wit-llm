/*
 * @test
 * @summary Test java.util.ArrayList.ListItr.add(E) for ConcurrentModificationException on modCount mismatch
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testAddThrowsConcurrentModificationException();
        testAddNormalBehavior();
    }

    private static void testAddThrowsConcurrentModificationException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        it.next(); // move cursor to 1

        // Modify list structurally outside iterator to cause modCount mismatch
        list.add("b");

        boolean threw = false;
        try {
            it.add("c"); // should throw ConcurrentModificationException
        } catch (ConcurrentModificationException ex) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        }
    }

    private static void testAddNormalBehavior() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        ListIterator<String> it = list.listIterator();

        // Add element via iterator without external modification
        it.add("y");

        // After add, cursor should move forward and list should contain both elements
        if (!list.get(0).equals("y") || !list.get(1).equals("x")) {
            throw new AssertionError("List contents incorrect after iterator add");
        }

        // Iterator cursor should be at index 1 now
        if (it.nextIndex() != 1) {
            throw new AssertionError("Iterator cursor not updated correctly after add");
        }
    }
}
