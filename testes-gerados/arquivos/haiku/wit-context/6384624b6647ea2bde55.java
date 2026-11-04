/*
 * @test
 * @summary Verify that removeAll on an unmodifiable collection view throws UnsupportedOperationException and leaves the backing collection unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> backing = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> view = Collections.unmodifiableCollection(backing);

        expectUnsupported(view, Arrays.asList("a"));
        expectUnsupported(view, Arrays.asList("x", "y"));
        expectUnsupported(view, new ArrayList<String>());
        expectUnsupported(view, null);

        if (!backing.equals(Arrays.asList("a", "b", "c"))) {
            throw new RuntimeException("backing collection was modified");
        }
        if (view.size() != 3) {
            throw new RuntimeException("unmodifiable view size changed");
        }
    }

    private static void expectUnsupported(Collection<String> view, Collection<?> arg) {
        try {
            view.removeAll(arg);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException");
    }
}
