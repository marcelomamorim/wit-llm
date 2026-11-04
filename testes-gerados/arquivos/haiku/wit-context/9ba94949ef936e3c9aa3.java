/*
 * @test
 * @summary Verify that clear() on an unmodifiable collection view throws UnsupportedOperationException without changing the backing collection
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        ArrayList<String> backing = new ArrayList<>();
        backing.add("a");
        backing.add("b");
        Collection<String> view = Collections.unmodifiableCollection(backing);
        expectUnsupported(view);
        if (view.size() != 2 || !backing.contains("a") || !backing.contains("b")) {
            throw new RuntimeException("backing collection was modified by clear()");
        }
        Collection<String> emptyView = Collections.unmodifiableCollection(new ArrayList<String>());
        expectUnsupported(emptyView);
        if (!emptyView.isEmpty()) {
            throw new RuntimeException("empty view changed unexpectedly");
        }
    }

    private static void expectUnsupported(Collection<String> c) {
        try {
            c.clear();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException from clear()");
    }
}
