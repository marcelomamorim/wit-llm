/*
 * @test
 * @summary Verifies that retainAll on an unmodifiable collection view throws UnsupportedOperationException and leaves the backing collection unchanged
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
        expectUnsupported(view, (Collection<?>) null);

        if (!backing.equals(Arrays.asList("a", "b", "c"))) {
            throw new RuntimeException("Backing collection was modified");
        }
    }

    private static void expectUnsupported(Collection<String> c, Collection<?> arg) {
        try {
            c.retainAll(arg);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException from retainAll");
    }
}
