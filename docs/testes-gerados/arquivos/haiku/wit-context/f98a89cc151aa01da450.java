/*
 * @test
 * @summary Verify that addAll on an unmodifiable collection view always throws UnsupportedOperationException and leaves the backing collection unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> backing = new ArrayList<>();
        backing.add("x");
        Collection<String> view = Collections.unmodifiableCollection(backing);

        expectUnsupported(view, Arrays.asList("a"));
        expectUnsupported(view, Collections.<String>emptyList());
        expectUnsupported(view, Arrays.asList("x"));

        if (backing.size() != 1 || !backing.contains("x")) {
            throw new RuntimeException("backing collection was modified");
        }
        if (view.size() != 1 || !view.contains("x")) {
            throw new RuntimeException("unmodifiable view state changed");
        }
    }

    static void expectUnsupported(Collection<String> target, Collection<String> arg) {
        try {
            target.addAll(arg);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException");
    }
}
