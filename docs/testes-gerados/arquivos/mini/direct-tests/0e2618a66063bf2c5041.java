/*
 * @test
 * @summary Test java.util.Collections removeAll(Collection<?>) method
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveAllBasic();
        testRemoveAllEmptyCollection();
        testRemoveAllNoMatch();
        testRemoveAllAllMatch();
    }

    private static void testRemoveAllBasic() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        Collection<String> toRemove = Arrays.asList("b", "d");
        boolean changed = list.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("Expected removeAll to return true when elements are removed");
        }
        if (list.size() != 2 || list.contains("b") || list.contains("d")) {
            throw new RuntimeException("removeAll did not remove the expected elements");
        }
    }

    private static void testRemoveAllEmptyCollection() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> toRemove = Collections.emptyList();
        boolean changed = list.removeAll(toRemove);
        if (changed) {
            throw new RuntimeException("Expected removeAll to return false when removing empty collection");
        }
        if (list.size() != 2) {
            throw new RuntimeException("List should remain unchanged when removing empty collection");
        }
    }

    private static void testRemoveAllNoMatch() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> toRemove = Arrays.asList("x", "y");
        boolean changed = list.removeAll(toRemove);
        if (changed) {
            throw new RuntimeException("Expected removeAll to return false when no elements match");
        }
        if (list.size() != 2) {
            throw new RuntimeException("List should remain unchanged when no elements match");
        }
    }

    private static void testRemoveAllAllMatch() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> toRemove = Arrays.asList("a", "b");
        boolean changed = list.removeAll(toRemove);
        if (!changed) {
            throw new RuntimeException("Expected removeAll to return true when all elements are removed");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("List should be empty after removing all elements");
        }
    }
}
