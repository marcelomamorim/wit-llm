/*
 * @test
 * @summary Verify that remove(Object) on an unmodifiable collection view always throws UnsupportedOperationException and leaves the backing collection unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        Collection<String> view = Collections.unmodifiableCollection(backing);

        expectUnsupported(view, "a");
        expectUnsupported(view, "missing");

        if (view.size() != 1 || !view.contains("a")) {
            throw new RuntimeException("view was modified by remove");
        }
        if (backing.size() != 1 || !backing.contains("a")) {
            throw new RuntimeException("backing collection was modified through view");
        }

        Collection<String> empty = Collections.unmodifiableCollection(new ArrayList<String>());
        expectUnsupported(empty, "x");
        expectUnsupported(empty, null);
        if (!empty.isEmpty()) {
            throw new RuntimeException("empty view changed after remove");
        }
    }

    static void expectUnsupported(Collection<String> c, Object o) {
        try {
            c.remove(o);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("remove did not throw UnsupportedOperationException for " + o);
    }
}
