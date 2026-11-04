/*
 * @test
 * @summary Test Vector.nextElement() for normal and exceptional behavior
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.NoSuchElementException;

public class VectorWitupTest {
    public static void main(String[] args) {
        testNextElementNormal();
        testNextElementException();
    }

    private static void testNextElementNormal() {
        Vector<String> v = new Vector<>();
        v.add("first");
        v.add("second");

        java.util.Enumeration<String> e = v.elements();

        // first element
        String first = e.nextElement();
        if (!"first".equals(first)) {
            throw new AssertionError("Expected 'first', got: " + first);
        }

        // second element
        String second = e.nextElement();
        if (!"second".equals(second)) {
            throw new AssertionError("Expected 'second', got: " + second);
        }
    }

    private static void testNextElementException() {
        Vector<String> v = new Vector<>();
        java.util.Enumeration<String> e = v.elements();

        boolean thrown = false;
        try {
            e.nextElement();
        } catch (NoSuchElementException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling nextElement() on empty Vector");
        }
    }
}
