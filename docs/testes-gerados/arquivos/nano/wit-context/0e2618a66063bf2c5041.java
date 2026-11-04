/*
 * @test
 * @summary Test for Collections.removeAll(Collection c) method.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithNull();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNonEmptyCollection();
    }

    static void testRemoveAllWithNull() {
        Collection<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        try {
            boolean result = Collections.removeAll(list, null);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Expected NullPointerException for null argument, but got result: " + result);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testRemoveAllWithEmptyCollection() {
        Collection<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> empty = new ArrayList<>();
        boolean result = Collections.removeAll(list, empty);
        assert result == false : "removeAll with empty collection should return false";
        assert list.equals(Arrays.asList("a", "b", "c")) : "List should remain unchanged";
    }

    static void testRemoveAllWithNonEmptyCollection() {
        Collection<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> toRemove = new ArrayList<>(Arrays.asList("b", "c"));
        boolean result = Collections.removeAll(list, toRemove);
        assert result == true : "removeAll should return true when elements are removed";
        assert list.equals(Arrays.asList("a")) : "List should contain only 'a' after removal";
    }
}
