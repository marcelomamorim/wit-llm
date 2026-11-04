/*
 * @test
 * @summary Test for Vector.nextElement() method.
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testNextElementWithEmptyVector();
        testNextElementWithPopulatedVector();
    }

    private static void testNextElementWithEmptyVector() {
        Vector<String> vector = new Vector<>();
        boolean exceptionThrown = false;
        try {
            vector.nextElement();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException for empty vector");
        }
    }

    private static void testNextElementWithPopulatedVector() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        // Call nextElement() multiple times to verify correct behavior
        String first = vector.nextElement();
        String second = vector.nextElement();
        String third = vector.nextElement();
        if (!"a".equals(first) || !"b".equals(second) || !"c".equals(third)) {
            throw new AssertionError("nextElement() did not return expected elements");
        }
        // After consuming all elements, next call should throw exception
        boolean exceptionThrown = false;
        try {
            vector.nextElement();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException after consuming all elements");
        }
    }
}
