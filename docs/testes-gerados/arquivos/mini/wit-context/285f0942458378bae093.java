/*
 * @test
 * @summary Test java.util.Vector.retainAll(Collection<?>) method for null argument and basic retainAll behavior
 * @run main java.util.VectorWitupTest
 */

import java.util.Vector;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class VectorWitupTest {
    public static void main(String[] args) {
        testRetainAllNullArgument();
        testRetainAllBasicBehavior();
    }

    private static void testRetainAllNullArgument() {
        Vector<String> vector = new Vector<>();
        try {
            vector.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRetainAllBasicBehavior() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");

        Collection<String> retain = new ArrayList<>();
        retain.add("b");
        retain.add("c");

        boolean changed = vector.retainAll(retain);
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when elements are removed");
        }

        if (vector.size() != 2 || !vector.contains("b") || !vector.contains("c")) {
            throw new RuntimeException("Vector contents incorrect after retainAll");
        }

        // Calling retainAll with a collection that contains all elements should return false
        changed = vector.retainAll(retain);
        if (changed) {
            throw new RuntimeException("Expected retainAll to return false when no elements are removed");
        }
    }
}
