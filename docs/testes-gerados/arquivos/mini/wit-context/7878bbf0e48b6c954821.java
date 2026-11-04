/*
 * @test
 * @summary Test ArrayList.SubList.Anonymous next() method for expected exceptions and normal behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ListIterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testNextNormal();
        testNextNoSuchElementException();
        testNextConcurrentModificationException();
    }

    private static void testNextNormal() {
        ArrayList<String> root = new ArrayList<>();
        root.add("a");
        root.add("b");
        root.add("c");

        List<String> subList = root.subList(0, 3);
        ListIterator<String> it = subList.listIterator();

        // next() should return elements in order
        String first = it.next();
        if (!"a".equals(first)) {
            throw new AssertionError("Expected 'a' but got " + first);
        }
        String second = it.next();
        if (!"b".equals(second)) {
            throw new AssertionError("Expected 'b' but got " + second);
        }
    }

    private static void testNextNoSuchElementException() {
        ArrayList<String> root = new ArrayList<>();
        root.add("x");

        List<String> subList = root.subList(0, 1);
        ListIterator<String> it = subList.listIterator();

        it.next(); // consume the only element

        try {
            it.next(); // should throw NoSuchElementException
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    private static void testNextConcurrentModificationException() {
        ArrayList<String> root = new ArrayList<>();
        root.add("x");
        root.add("y");

        List<String> subList = root.subList(0, 2);
        ListIterator<String> it = subList.listIterator();

        // Modify root list structurally to cause modCount mismatch
        root.add("z");

        try {
            it.next(); // should throw ConcurrentModificationException
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
