/*
 * @test
 * @summary Test ArrayList.retainAll(Collection<?>) for null argument and basic retain behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRetainAllNullCollection();
        testRetainAllBasicBehavior();
    }

    private static void testRetainAllNullCollection() {
        ArrayList<String> list = new ArrayList<>();
        try {
            list.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRetainAllBasicBehavior() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        Collection<String> toRetain = Arrays.asList("b", "c", "x");

        boolean changed = list.retainAll(toRetain);

        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when list is modified");
        }

        if (list.size() != 2 || !list.contains("b") || !list.contains("c")) {
            throw new RuntimeException("List contents incorrect after retainAll: " + list);
        }

        // Calling retainAll with a collection that contains all elements should not change the list
        changed = list.retainAll(toRetain);
        if (changed) {
            throw new RuntimeException("Expected retainAll to return false when list is not modified");
        }

        // Calling retainAll with empty collection should clear the list
        changed = list.retainAll(Collections.<String>emptyList());
        if (!changed) {
            throw new RuntimeException("Expected retainAll to return true when list is cleared");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("Expected list to be empty after retainAll with empty collection");
        }
    }
}
