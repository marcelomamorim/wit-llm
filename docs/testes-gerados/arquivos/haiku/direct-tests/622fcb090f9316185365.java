/*
 * @test
 * @summary Verifies that add(E) on unmodifiable collection wrappers throws UnsupportedOperationException and leaves the backing state unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionsWitupTest {

    interface Action {
        void run();
    }

    static void expectUnsupported(String name, Action action) {
        try {
            action.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError(name + ": expected UnsupportedOperationException");
    }

    static void testUnmodifiableCollectionAddThrows() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        Collection<String> view = Collections.unmodifiableCollection(backing);
        expectUnsupported("unmodifiableCollection.add", () -> view.add("b"));
        if (view.size() != 1 || !view.contains("a") || view.contains("b")) {
            throw new AssertionError("unmodifiableCollection changed after failed add");
        }
    }

    static void testUnmodifiableListAddThrows() {
        List<Integer> backing = new ArrayList<>();
        backing.add(1);
        List<Integer> view = Collections.unmodifiableList(backing);
        expectUnsupported("unmodifiableList.add", () -> view.add(2));
        if (!view.equals(backing) || view.size() != 1 || view.get(0) != 1) {
            throw new AssertionError("unmodifiableList changed after failed add");
        }
    }

    static void testUnmodifiableSetAddThrows() {
        Set<String> backing = new HashSet<>();
        backing.add("x");
        Set<String> view = Collections.unmodifiableSet(backing);
        expectUnsupported("unmodifiableSet.add", () -> view.add("y"));
        if (view.size() != 1 || !view.contains("x") || view.contains("y")) {
            throw new AssertionError("unmodifiableSet changed after failed add");
        }
    }

    static void testAddOfDuplicateStillThrows() {
        Set<String> backing = new HashSet<>();
        backing.add("x");
        Set<String> view = Collections.unmodifiableSet(backing);
        expectUnsupported("unmodifiableSet.add duplicate", () -> view.add("x"));
        if (view.size() != 1) {
            throw new AssertionError("unexpected size after duplicate add");
        }
    }

    static void testEmptyListAddThrows() {
        List<String> empty = Collections.emptyList();
        expectUnsupported("emptyList.add", () -> empty.add("z"));
        if (!empty.isEmpty()) {
            throw new AssertionError("emptyList changed after failed add");
        }
    }

    public static void main(String[] args) {
        testUnmodifiableCollectionAddThrows();
        testUnmodifiableListAddThrows();
        testUnmodifiableSetAddThrows();
        testAddOfDuplicateStillThrows();
        testEmptyListAddThrows();
    }
}
