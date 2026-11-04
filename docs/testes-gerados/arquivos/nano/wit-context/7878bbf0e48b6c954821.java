/*
 * @test
 * @summary Test for ArrayList.next() method, focusing on normal behavior and ConcurrentModificationException.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNextNormal();
        testNextConcurrentModification();
    }

    static void testNextNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        ArrayList<String>.ListIterator iterator = list.listIterator();
        try {
            while (iterator.hasNext()) {
                String element = iterator.next();
                System.out.println("Next element: " + element);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal next(): " + e);
        }
    }

    static void testNextConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        ArrayList<String>.ListIterator iterator = list.listIterator();
        list.add("new"); // modify list after iterator creation
        try {
            iterator.next();
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        } catch (ConcurrentModificationException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
