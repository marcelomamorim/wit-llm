/*
 * @test
 * @summary Verifies that removeIf on a Collections unmodifiable collection view always throws UnsupportedOperationException and leaves the backing collection unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfThrowsForMatchingPredicate();
        testRemoveIfThrowsForNonMatchingPredicate();
        testRemoveIfThrowsOnEmptyCollection();
        testRemoveIfThrowsWithNullPredicate();
        testBackingCollectionUnchanged();
    }

    static void expectUnsupported(Collection<String> view, Predicate<? super String> filter) {
        try {
            view.removeIf(filter);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("Expected UnsupportedOperationException");
    }

    static void testRemoveIfThrowsForMatchingPredicate() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");
        Collection<String> view = Collections.unmodifiableCollection(backing);
        expectUnsupported(view, s -> true);
    }

    static void testRemoveIfThrowsForNonMatchingPredicate() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        Collection<String> view = Collections.unmodifiableCollection(backing);
        expectUnsupported(view, s -> false);
    }

    static void testRemoveIfThrowsOnEmptyCollection() {
        Collection<String> view = Collections.unmodifiableCollection(new ArrayList<String>());
        expectUnsupported(view, s -> true);
    }

    static void testRemoveIfThrowsWithNullPredicate() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        Collection<String> view = Collections.unmodifiableCollection(backing);
        expectUnsupported(view, null);
    }

    static void testBackingCollectionUnchanged() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");
        Collection<String> view = Collections.unmodifiableCollection(backing);
        expectUnsupported(view, s -> true);
        if (backing.size() != 2 || !backing.contains("a") || !backing.contains("b")) {
            throw new AssertionError("Backing collection was modified");
        }
        if (view.size() != 2) {
            throw new AssertionError("View size changed");
        }
    }
}
