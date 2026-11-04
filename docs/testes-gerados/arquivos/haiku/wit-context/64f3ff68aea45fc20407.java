/*
 * @test
 * @summary Verify that removeIf on an unmodifiable collection view throws UnsupportedOperationException and leaves the backing data unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfThrowsAndPreservesElements();
        testRemoveIfThrowsWithEmptyCollection();
        testRemoveIfThrowsWhenPredicateWouldMatchAll();
    }

    private static void testRemoveIfThrowsAndPreservesElements() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");
        backing.add("c");
        Collection<String> view = Collections.unmodifiableCollection(backing);
        boolean thrown = false;
        try {
            view.removeIf(s -> s.equals("a"));
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from removeIf");
        }
        if (backing.size() != 3 || !backing.contains("a")) {
            throw new AssertionError("Backing collection was modified: " + backing);
        }
    }

    private static void testRemoveIfThrowsWithEmptyCollection() {
        Collection<String> view =
                Collections.unmodifiableCollection(new ArrayList<String>());
        boolean thrown = false;
        try {
            view.removeIf(s -> true);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException for empty view");
        }
    }

    private static void testRemoveIfThrowsWhenPredicateWouldMatchAll() {
        List<Integer> backing = new ArrayList<>();
        backing.add(1);
        backing.add(2);
        Collection<Integer> view = Collections.unmodifiableCollection(backing);
        boolean thrown = false;
        try {
            view.removeIf(i -> true);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException when predicate matches all");
        }
        if (backing.size() != 2) {
            throw new AssertionError("Backing collection changed size: " + backing.size());
        }
    }
}
