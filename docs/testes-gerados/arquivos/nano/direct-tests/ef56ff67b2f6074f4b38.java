/* @test    @summary Test lastElement() method of Vector for non-empty and empty cases    @run main VectorWitupTest */

import java.util.Vector;
import java.util.NoSuchElementException;

public class VectorWitupTest {

    public static void main(String[] args) {
        testLastElementWithElements();
        testLastElementEmpty();
    }

    private static void testLastElementWithElements() {
        Vector<String> vector = new Vector<>();
        vector.add("first");
        vector.add("second");
        String last = vector.lastElement();
        if (!"second".equals(last)) {
            throw new AssertionError("Expected 'second', but got: " + last);
        }
    }

    private static void testLastElementEmpty() {
        Vector<String> vector = new Vector<>();
        boolean exceptionThrown = false;
        try {
            vector.lastElement();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException to be thrown for empty vector");
        }
    }
}
