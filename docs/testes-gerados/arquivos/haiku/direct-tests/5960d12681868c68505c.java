/*
 * @test
 * @summary Verify that the iterator returned by unmodifiable collection wrappers rejects remove() with UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectUnsupportedRemove(Iterator<?> it, String context) {
        try {
            it.remove();
            throw new RuntimeException("remove() did not throw UnsupportedOperationException: " + context);
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testUnmodifiableCollectionRemoveBeforeNext();
        testUnmodifiableCollectionRemoveAfterNext();
        testUnmodifiableListRemoveAfterNextLeavesContentUnchanged();
    }

    static void testUnmodifiableCollectionRemoveBeforeNext() {
        Collection<String> c = Collections.unmodifiableCollection(
                new ArrayList<>(Arrays.asList("a", "b")));
        Iterator<String> it = c.iterator();
        expectUnsupportedRemove(it, "before next()");
    }

    static void testUnmodifiableCollectionRemoveAfterNext() {
        Collection<String> c = Collections.unmodifiableCollection(
                new ArrayList<>(Arrays.asList("a", "b")));
        Iterator<String> it = c.iterator();
        check("a".equals(it.next()), "unexpected first element");
        expectUnsupportedRemove(it, "after next()");
        check(c.size() == 2, "collection size changed after failed remove");
    }

    static void testUnmodifiableListRemoveAfterNextLeavesContentUnchanged() {
        List<String> backing = new ArrayList<>(Arrays.asList("x", "y", "z"));
        List<String> view = Collections.unmodifiableList(backing);
        Iterator<String> it = view.iterator();
        check("x".equals(it.next()), "unexpected first element");
        expectUnsupportedRemove(it, "unmodifiable list iterator");
        check(backing.equals(Arrays.asList("x", "y", "z")), "backing list was modified");
    }
}
