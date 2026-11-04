/*
 * @test
 * @summary Test java.util.Vector.lastElement() for normal and exceptional cases
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.NoSuchElementException;

public class VectorWitupTest {
    public static void main(String[] args) {
        testLastElementEmptyVector();
        testLastElementNonEmptyVector();
    }

    private static void testLastElementEmptyVector() {
        Vector<Object> v = new Vector<>();
        try {
            v.lastElement();
            throw new AssertionError("Expected NoSuchElementException for empty vector");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }

    private static void testLastElementNonEmptyVector() {
        Vector<String> v = new Vector<>();
        v.add("first");
        v.add("second");
        String last = v.lastElement();
        if (!"second".equals(last)) {
            throw new AssertionError("Expected lastElement to return 'second', got: " + last);
        }
    }
}
