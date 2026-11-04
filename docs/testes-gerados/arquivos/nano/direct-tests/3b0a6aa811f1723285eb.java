/* @test    @summary Test removeAll method of ArrayList for various collection inputs    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithNullCollection();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithMatchingElements();
        testRemoveAllWithNonMatchingElements();
    }

    static void testRemoveAllWithNullCollection() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        try {
            list.removeAll(null);
        } catch (NullPointerException e) {
            // Expected exception
            return;
        }
        throw new AssertionError("Expected NullPointerException when removing with null collection");
    }

    static void testRemoveAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> empty = Arrays.asList();
        boolean changed = list.removeAll(empty);
        if (changed) {
            throw new AssertionError("removeAll with empty collection should not change list");
        }
        if (list.size() != 3) {
            throw new AssertionError("List should remain unchanged when removing empty collection");
        }
    }

    static void testRemoveAllWithMatchingElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "a"));
        Collection<String> toRemove = Arrays.asList("a", "b");
        boolean changed = list.removeAll(toRemove);
        if (!changed) {
            throw new AssertionError("removeAll should return true when list is modified");
        }
        if (list.contains("a") || list.contains("b")) {
            throw new AssertionError("List should not contain removed elements");
        }
        if (list.size() != 2) {
            throw new AssertionError("List size should be 2 after removal");
        }
    }

    static void testRemoveAllWithNonMatchingElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> toRemove = Arrays.asList("x", "y");
        boolean changed = list.removeAll(toRemove);
        if (changed) {
            throw new AssertionError("removeAll should return false when list is not modified");
        }
        if (list.size() != 3) {
            throw new AssertionError("List should remain unchanged when removing non-matching elements");
        }
    }
}
