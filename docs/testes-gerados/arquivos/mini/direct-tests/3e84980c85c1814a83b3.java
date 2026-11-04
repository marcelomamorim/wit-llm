/*
 * @test
 * @summary Test Vector.nextElement() method behavior
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class VectorWitupTest {
    public static void main(String[] args) {
        testNextElementReturnsElementsInOrder();
        testNextElementThrowsWhenNoMoreElements();
    }

    private static void testNextElementReturnsElementsInOrder() {
        Vector<String> v = new Vector<>();
        v.add("first");
        v.add("second");
        Enumeration<String> e = v.elements();

        String first = e.nextElement();
        if (!"first".equals(first)) {
            throw new AssertionError("Expected 'first', got: " + first);
        }

        String second = e.nextElement();
        if (!"second".equals(second)) {
            throw new AssertionError("Expected 'second', got: " + second);
        }
    }

    private static void testNextElementThrowsWhenNoMoreElements() {
        Vector<String> v = new Vector<>();
        Enumeration<String> e = v.elements();

        boolean thrown = false;
        try {
            e.nextElement();
        } catch (NoSuchElementException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling nextElement() on empty Enumeration");
        }
    }
}
