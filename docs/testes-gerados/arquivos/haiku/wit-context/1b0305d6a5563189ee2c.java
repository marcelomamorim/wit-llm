/*
 * @test
 * @summary Verifies that removeIf on an unmodifiable collection view throws UnsupportedOperationException and leaves the backing collection unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveIfAlwaysTrueThrows();
        testRemoveIfAlwaysFalseThrows();
        testRemoveIfOnEmptyViewThrows();
    }

    static void testRemoveIfAlwaysTrueThrows() {
        List<String> backing = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> view = Collections.unmodifiableCollection(backing);
        boolean thrown = false;
        try {
            view.removeIf(s -> true);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException");
        }
        if (backing.size() != 3 || !backing.equals(Arrays.asList("a", "b", "c"))) {
            throw new RuntimeException("backing collection was modified");
        }
    }

    static void testRemoveIfAlwaysFalseThrows() {
        List<String> backing = new ArrayList<>(Arrays.asList("x", "y"));
        Collection<String> view = Collections.unmodifiableCollection(backing);
        boolean thrown = false;
        try {
            view.removeIf(s -> false);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException");
        }
        if (!backing.equals(Arrays.asList("x", "y"))) {
            throw new RuntimeException("backing collection was modified");
        }
    }

    static void testRemoveIfOnEmptyViewThrows() {
        List<String> backing = new ArrayList<>();
        Collection<String> view = Collections.unmodifiableCollection(backing);
        boolean thrown = false;
        try {
            view.removeIf(s -> true);
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException on empty view");
        }
    }
}
