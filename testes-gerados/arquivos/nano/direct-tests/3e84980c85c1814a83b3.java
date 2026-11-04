/* @test    @summary Test nextElement() method of Vector class    @run main VectorWitupTest */

import java.util.Vector;
import java.util.NoSuchElementException;

public class VectorWitupTest {

    public static void main(String[] args) {
        testNextElementWithElements();
        testNextElementWithoutElements();
    }

    static void testNextElementWithElements() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        try {
            // Call nextElement() multiple times to retrieve all elements
            String first = vector.nextElement();
            String second = vector.nextElement();
            String third = vector.nextElement();
            assert "a".equals(first) : "Expected 'a', got " + first;
            assert "b".equals(second) : "Expected 'b', got " + second;
            assert "c".equals(third) : "Expected 'c', got " + third;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testNextElementWithoutElements() {
        Vector<String> vector = new Vector<>();
        boolean exceptionThrown = false;
        try {
            vector.nextElement();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NoSuchElementException to be thrown when no elements are present";
    }
}
