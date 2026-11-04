/*
 * @test
 * @summary Verifies that iterator remove() on unmodifiable collection views throws UnsupportedOperationException and leaves the backing data unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testUnmodifiableCollectionIteratorRemove();
        testUnmodifiableListIteratorRemove();
        testUnmodifiableListIteratorRemoveFromStart();
    }

    static void testUnmodifiableCollectionIteratorRemove() {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> view = Collections.unmodifiableCollection(base);
        Iterator<String> it = view.iterator();
        it.next();
        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("remove() did not throw UnsupportedOperationException");
        }
        if (base.size() != 3 || !base.get(0).equals("a")) {
            throw new RuntimeException("backing collection was modified: " + base);
        }
    }

    static void testUnmodifiableListIteratorRemove() {
        List<String> base = new ArrayList<>(Arrays.asList("x", "y"));
        List<String> view = Collections.unmodifiableList(base);
        ListIterator<String> it = view.listIterator();
        it.next();
        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("ListIterator.remove() did not throw UnsupportedOperationException");
        }
        if (base.size() != 2) {
            throw new RuntimeException("backing list size changed: " + base.size());
        }
    }

    static void testUnmodifiableListIteratorRemoveFromStart() {
        List<Integer> base = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> view = Collections.unmodifiableList(base);
        Iterator<Integer> it = view.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        } catch (IllegalStateException e) {
            throw new RuntimeException("unexpected IllegalStateException instead of UnsupportedOperationException");
        }
        if (!thrown) {
            throw new RuntimeException("remove() before next() did not throw UnsupportedOperationException");
        }
        if (!base.equals(Arrays.asList(1, 2, 3))) {
            throw new RuntimeException("backing list changed: " + base);
        }
    }
}
