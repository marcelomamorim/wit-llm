/*
 * @test
 * @summary Verifies that addAll on a collection returned by Collections.unmodifiableCollection throws UnsupportedOperationException and leaves the backing collection unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testAddAllNonEmptyThrows();
        testAddAllEmptyArgumentThrows();
        testAddAllLeavesBackingCollectionUnchanged();
    }

    static void testAddAllNonEmptyThrows() {
        List<String> backing = new ArrayList<>();
        Collection<String> view = Collections.unmodifiableCollection(backing);
        boolean thrown = false;
        try {
            view.addAll(Arrays.asList("a", "b"));
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("addAll(non-empty) did not throw UnsupportedOperationException");
        }
    }

    static void testAddAllEmptyArgumentThrows() {
        List<String> backing = new ArrayList<>();
        Collection<String> view = Collections.unmodifiableCollection(backing);
        boolean thrown = false;
        try {
            view.addAll(Collections.<String>emptyList());
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("addAll(empty) did not throw UnsupportedOperationException");
        }
    }

    static void testAddAllLeavesBackingCollectionUnchanged() {
        List<String> backing = new ArrayList<>();
        backing.add("x");
        Collection<String> view = Collections.unmodifiableCollection(backing);
        try {
            view.addAll(Arrays.asList("y"));
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (backing.size() != 1 || !backing.contains("x") || backing.contains("y")) {
            throw new AssertionError("backing collection was modified by addAll: " + backing);
        }
        if (view.size() != 1) {
            throw new AssertionError("view size changed after failed addAll: " + view.size());
        }
    }
}
