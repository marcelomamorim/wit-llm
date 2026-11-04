/*
 * @test
 * @summary Test removeAll method in Collections class
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNonEmptyCollection();
        testRemoveAllWithNull();
    }

    static void testRemoveAllWithEmptyCollection() {
        Collection<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> toRemove = new ArrayList<>();
        boolean changed = Collections.removeAll(list, toRemove);
        assert !changed : "Expected no change when removing empty collection";
        assert list.equals(Arrays.asList("a", "b", "c")) : "List should remain unchanged";
    }

    static void testRemoveAllWithNonEmptyCollection() {
        Collection<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> toRemove = new ArrayList<>(Arrays.asList("b", "c"));
        boolean changed = Collections.removeAll(list, toRemove);
        assert changed : "Expected list to change";
        assert list.equals(Arrays.asList("a")) : "List should contain only 'a'";
    }

    static void testRemoveAllWithNull() {
        Collection<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        try {
            Collections.removeAll(list, null);
            assert false : "Expected NullPointerException when argument is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
