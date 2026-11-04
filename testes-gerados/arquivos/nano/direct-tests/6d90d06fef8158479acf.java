/*
 * @test
 * @summary Test for Collections.retainAll to verify it retains only specified elements.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRetainAllWithExistingElements();
        testRetainAllWithEmptyCollection();
        testRetainAllWithNoMatchingElements();
    }

    static void testRetainAllWithExistingElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        Collection<String> retainCollection = Arrays.asList("b", "d", "x");
        boolean changed = Collections.retainAll(list, retainCollection);
        assert changed : "Expected list to be changed";
        assert list.size() == 2 : "Expected list size 2";
        assert list.contains("b") : "List should contain 'b'";
        assert list.contains("d") : "List should contain 'd'";
        assert !list.contains("a") : "List should not contain 'a'";
        assert !list.contains("c") : "List should not contain 'c'";
    }

    static void testRetainAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> empty = Collections.emptyList();
        boolean changed = Collections.retainAll(list, empty);
        assert changed : "Expected list to be changed when retaining empty collection";
        assert list.isEmpty() : "List should be empty after retainAll with empty collection";
    }

    static void testRetainAllWithNoMatchingElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> retainCollection = Arrays.asList("x", "y", "z");
        boolean changed = Collections.retainAll(list, retainCollection);
        assert changed : "Expected list to be changed";
        assert list.isEmpty() : "List should be empty after retainAll with no matching elements";
    }
}
