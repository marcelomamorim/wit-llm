/*
 * @test
 * @summary Verifies that removeAll on an unmodifiable collection view throws UnsupportedOperationException and leaves the backing collection unchanged
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
        expectUnsupported(view, new ArrayList<String>());
        expectUnsupported(view, null);

        if (backing.size() != 3 || !backing.contains("a") || !backing.contains("b") || !backing.contains("c")) {
            throw new AssertionError("backing collection was modified: " + backing);
        }
        if (view.size() != 3) {
            throw new AssertionError("view size changed: " + view.size());
        }
    }

    static void expectUnsupported(Collection<String> view, Collection<?> arg) {
        try {
            view.removeAll(arg);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("UnsupportedOperationException not thrown for removeAll(" + arg + ")");
    }
}
