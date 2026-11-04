/*
 * @test
 * @summary Verifies that remove(Object) on an unmodifiable collection view always throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Collection<String> base = new ArrayList<>();
        base.add("a");
        Collection<String> view = Collections.unmodifiableCollection(base);

        expectUnsupported(view, "a");
        expectUnsupported(view, "missing");
        expectUnsupported(view, null);

        if (base.size() != 1 || !base.contains("a")) {
            throw new RuntimeException("Backing collection was modified through unmodifiable view");
        }
    }

    static void expectUnsupported(Collection<String> c, Object o) {
        try {
            c.remove(o);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException for remove(" + o + ")");
    }
}
