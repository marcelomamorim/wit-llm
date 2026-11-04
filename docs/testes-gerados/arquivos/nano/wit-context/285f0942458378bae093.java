/*
 * @test
 * @summary Test for Vector.retainAll(Collection<?>) method
 * @run main VectorWitupTest
 */

import java.util.Vector;
import java.util.Collection;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNull();
        testRetainAllWithEmptyCollection();
        testRetainAllWithNonEmptyCollection();
    }

    private static void testRetainAllWithNull() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        boolean exceptionThrown = false;
        try {
            vector.retainAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to retainAll");
        }
    }

    private static void testRetainAllWithEmptyCollection() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        Collection<String> empty = new java.util.ArrayList<>();
        boolean changed = vector.retainAll(empty);
        if (changed != true) {
            throw new AssertionError("Expected retainAll to return true when collection is empty and vector is non-empty");
        }
        if (vector.size() != 0) {
            throw new AssertionError("Expected vector to be empty after retainAll with empty collection");
        }
    }

    private static void testRetainAllWithNonEmptyCollection() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        Collection<String> c = new java.util.ArrayList<>();
        c.add("b");
        c.add("c");
        boolean changed = vector.retainAll(c);
        if (changed != true) {
            throw new AssertionError("Expected retainAll to return true when vector is modified");
        }
        if (vector.size() != 2 || !vector.contains("b") || !vector.contains("c")) {
            throw new AssertionError("Vector contents incorrect after retainAll");
        }
    }
}
