/*
 * @test
 * @summary Test ArrayList.removeAll(Collection<?>) for null argument and normal behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveAllNullArgument();
        testRemoveAllNormalBehavior();
    }

    private static void testRemoveAllNullArgument() {
        ArrayList<String> list = new ArrayList<>();
        try {
            list.removeAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveAllNormalBehavior() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
        Collection<String> toRemove = Arrays.asList("b", "d", "x"); // 'x' not in list
        boolean changed = list.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("removeAll should return true when elements are removed");
        }
        if (list.size() != 3) {
            throw new RuntimeException("List size after removeAll is incorrect: " + list.size());
        }
        if (list.contains("b") || list.contains("d")) {
            throw new RuntimeException("List still contains elements that should have been removed");
        }
        if (!list.contains("a") || !list.contains("c") || !list.contains("e")) {
            throw new RuntimeException("List lost elements that should remain");
        }

        // Test removeAll with empty collection - should not change list
        changed = list.removeAll(Collections.emptyList());
        if (changed) {
            throw new RuntimeException("removeAll with empty collection should return false");
        }
    }
}
