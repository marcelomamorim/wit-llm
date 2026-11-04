/*
 * @test
 * @summary Verify that addAll(int, Collection) on unmodifiable lists throws UnsupportedOperationException and leaves the list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testAddAllIndexThrowsForNonEmptySource();
        testAddAllIndexThrowsForEmptySource();
        testAddAllIndexThrowsForLinkedListBackedView();
    }

    static void testAddAllIndexThrowsForNonEmptySource() {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b"));
        List<String> unmod = Collections.unmodifiableList(base);
        Collection<String> src = Arrays.asList("x", "y");
        expectUnsupported(() -> unmod.addAll(0, src));
        expectUnsupported(() -> unmod.addAll(unmod.size(), src));
        check(base.equals(Arrays.asList("a", "b")), "backing list must be unchanged");
    }

    static void testAddAllIndexThrowsForEmptySource() {
        List<String> base = new ArrayList<>(Arrays.asList("a"));
        List<String> unmod = Collections.unmodifiableList(base);
        Collection<String> empty = new ArrayList<>();
        expectUnsupported(() -> unmod.addAll(0, empty));
        check(base.equals(Arrays.asList("a")), "backing list must be unchanged for empty source");
    }

    static void testAddAllIndexThrowsForLinkedListBackedView() {
        List<String> base = new LinkedList<>(Arrays.asList("p"));
        List<String> unmod = Collections.unmodifiableList(base);
        Collection<String> src = Arrays.asList("q");
        expectUnsupported(() -> unmod.addAll(0, src));
        check(base.equals(Arrays.asList("p")), "linked backing list must be unchanged");
    }

    interface Action {
        void run();
    }

    static void expectUnsupported(Action action) {
        try {
            action.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
