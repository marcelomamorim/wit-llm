/*
 * @test
 * @summary Verifies that clear() on unmodifiable collection views throws UnsupportedOperationException and leaves the backing data unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testUnmodifiableListClearThrows() {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b"));
        List<String> view = Collections.unmodifiableList(base);
        boolean thrown = false;
        try {
            view.clear();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "clear() on unmodifiable list must throw UnsupportedOperationException");
        check(base.size() == 2, "backing list must be unchanged after failed clear()");
        check(base.get(0).equals("a") && base.get(1).equals("b"), "backing list content must be unchanged");
    }

    static void testUnmodifiableCollectionClearThrows() {
        Collection<String> base = new ArrayList<>(Arrays.asList("x", "y", "z"));
        Collection<String> view = Collections.unmodifiableCollection(base);
        boolean thrown = false;
        try {
            view.clear();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "clear() on unmodifiable collection must throw UnsupportedOperationException");
        check(base.size() == 3, "backing collection must keep its size after failed clear()");
    }

    static void testUnmodifiableSetClearThrows() {
        Set<String> base = new HashSet<>(Arrays.asList("p", "q"));
        Set<String> view = Collections.unmodifiableSet(base);
        boolean thrown = false;
        try {
            view.clear();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "clear() on unmodifiable set must throw UnsupportedOperationException");
        check(base.size() == 2, "backing set must keep its size after failed clear()");
    }

    static void testEmptyUnmodifiableListClearStillThrows() {
        List<String> view = Collections.unmodifiableList(new ArrayList<String>());
        boolean thrown = false;
        try {
            view.clear();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "clear() on empty unmodifiable list must still throw UnsupportedOperationException");
    }

    public static void main(String[] args) {
        testUnmodifiableListClearThrows();
        testUnmodifiableCollectionClearThrows();
        testUnmodifiableSetClearThrows();
        testEmptyUnmodifiableListClearStillThrows();
    }
}
