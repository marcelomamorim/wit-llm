/*
 * @test
 * @summary Test java.util.Vector.lastElement() method behavior
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.NoSuchElementException;

public class VectorWitupTest {
    public static void main(String[] args) {
        testLastElementReturnsLastAdded();
        testLastElementThrowsOnEmptyVector();
    }

    private static void testLastElementReturnsLastAdded() {
        Vector<String> v = new Vector<>();
        v.add("first");
        v.add("second");
        v.add("third");
        String last = v.lastElement();
        if (!"third".equals(last)) {
            throw new AssertionError("Expected last element to be 'third' but was '" + last + "'");
        }
    }

    private static void testLastElementThrowsOnEmptyVector() {
        Vector<Object> v = new Vector<>();
        try {
            v.lastElement();
            throw new AssertionError("Expected NoSuchElementException when calling lastElement on empty Vector");
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
