/*
 * @test
 * @summary Test java.util.ArrayList SubList next() method behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testNextNormal();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        List<String> subList = list.subList(1, 3); // contains "b", "c"
        ListIterator<String> it = subList.listIterator();

        // next() should return "b" then "c"
        String first = it.next();
        if (!"b".equals(first)) {
            throw new Error("Expected 'b' but got: " + first);
        }

        String second = it.next();
        if (!"c".equals(second)) {
            throw new Error("Expected 'c' but got: " + second);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");

        List<String> subList = list.subList(0, 1); // contains "x"
        ListIterator<String> it = subList.listIterator();

        it.next(); // consume the only element

        try {
            it.next(); // should throw NoSuchElementException
            throw new Error("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");

        List<String> subList = list.subList(0, 2); // contains "x", "y"
        ListIterator<String> it = subList.listIterator();

        // Modify the root list structurally to cause concurrent modification
        list.add("z");

        try {
            it.next(); // should throw ConcurrentModificationException
            throw new Error("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
