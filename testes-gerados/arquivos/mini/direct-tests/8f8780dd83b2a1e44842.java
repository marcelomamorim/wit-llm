/*
 * @test
 * @summary Test java.util.ArrayList.retainAll(Collection<?>) method behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRetainAllWithEmptyCollection();
        testRetainAllWithNoCommonElements();
        testRetainAllWithSomeCommonElements();
        testRetainAllWithAllCommonElements();
        testRetainAllWithNullCollection();
    }

    private static void testRetainAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        boolean changed = list.retainAll(new ArrayList<>());
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when removing all elements");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("Expected list to be empty after retainAll with empty collection");
        }
    }

    private static void testRetainAllWithNoCommonElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> c = Arrays.asList("x", "y", "z");
        boolean changed = list.retainAll(c);
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when removing all elements");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("Expected list to be empty after retainAll with no common elements");
        }
    }

    private static void testRetainAllWithSomeCommonElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> c = Arrays.asList("b", "c", "x");
        boolean changed = list.retainAll(c);
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when removing some elements");
        }
        if (list.size() != 2 || !list.contains("b") || !list.contains("c")) {
            throw new RuntimeException("Expected list to contain only elements present in the collection after retainAll");
        }
    }

    private static void testRetainAllWithAllCommonElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> c = Arrays.asList("a", "b", "c", "d");
        boolean changed = list.retainAll(c);
        if (changed) {
            throw new RuntimeException("Expected retainAll to return false when no elements are removed");
        }
        if (list.size() != 3 || !list.containsAll(Arrays.asList("a", "b", "c"))) {
            throw new RuntimeException("Expected list to remain unchanged after retainAll with all elements present");
        }
    }

    private static void testRetainAllWithNullCollection() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        try {
            list.retainAll(null);
            throw new RuntimeException("Expected NullPointerException when passing null to retainAll");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
