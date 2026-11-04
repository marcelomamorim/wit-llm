/*
 * @test
 * @summary Test java.util.Vector.retainAll(Collection<?>) method behavior
 * @run main java.util.VectorWitupTest
 */

import java.util.Vector;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class VectorWitupTest {
    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllWithEmptyCollection();
        testRetainAllWithPartialOverlap();
        testRetainAllWithFullOverlap();
        testRetainAllWithNoOverlap();
    }

    private static void testRetainAllWithNullCollection() {
        Vector<String> v = new Vector<>();
        try {
            v.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRetainAllWithEmptyCollection() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        boolean changed = v.retainAll(new ArrayList<String>());
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when removing elements");
        }
        if (!v.isEmpty()) {
            throw new RuntimeException("Expected vector to be empty after retainAll with empty collection");
        }
    }

    private static void testRetainAllWithPartialOverlap() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        Collection<String> c = Arrays.asList("b", "c", "d");
        boolean changed = v.retainAll(c);
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when removing elements");
        }
        if (v.size() != 2 || !v.contains("b") || !v.contains("c")) {
            throw new RuntimeException("Expected vector to contain only elements present in collection");
        }
    }

    private static void testRetainAllWithFullOverlap() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        Collection<String> c = Arrays.asList("a", "b", "c");
        boolean changed = v.retainAll(c);
        if (changed) {
            throw new RuntimeException("Expected retainAll to return false when no elements removed");
        }
        if (v.size() != 2 || !v.contains("a") || !v.contains("b")) {
            throw new RuntimeException("Expected vector to remain unchanged");
        }
    }

    private static void testRetainAllWithNoOverlap() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        Collection<String> c = Arrays.asList("x", "y");
        boolean changed = v.retainAll(c);
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when all elements removed");
        }
        if (!v.isEmpty()) {
            throw new RuntimeException("Expected vector to be empty after retainAll with no overlap");
        }
    }
}
