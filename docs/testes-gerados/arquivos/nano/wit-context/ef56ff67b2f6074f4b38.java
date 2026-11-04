/* @test    @summary Test for Vector.lastElement() method to verify behavior when vector is empty    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testLastElementEmptyVector();
        testLastElementNonEmptyVector();
    }

    private static void testLastElementEmptyVector() {
        Vector<Object> vector = new Vector<>();
        boolean exceptionThrown = false;
        try {
            vector.lastElement();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException when calling lastElement() on empty vector");
        }
    }

    private static void testLastElementNonEmptyVector() {
        Vector<String> vector = new Vector<>();
        vector.add("first");
        vector.add("second");
        String last = vector.lastElement();
        if (!"second".equals(last)) {
            throw new AssertionError("Expected last element to be 'second', but was: " + last);
        }
    }
}
