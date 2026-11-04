/*
 * @test
 * @summary Verify that clear() on an unmodifiable collection view throws UnsupportedOperationException and leaves the backing data unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testClearThrowsAndPreservesElements();
        testClearThrowsOnEmptyView();
    }

    private static void testClearThrowsAndPreservesElements() {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");
        Collection<String> view = Collections.unmodifiableCollection(backing);
        try {
            view.clear();
            throw new RuntimeException("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (view.size() != 2 || !view.contains("a") || !view.contains("b")) {
            throw new RuntimeException("elements changed after failed clear(): " + view);
        }
    }

    private static void testClearThrowsOnEmptyView() {
        Collection<String> view = Collections.unmodifiableCollection(new ArrayList<String>());
        try {
            view.clear();
            throw new RuntimeException("expected UnsupportedOperationException on empty view");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (!view.isEmpty()) {
            throw new RuntimeException("empty view modified by clear()");
        }
    }
}
