/*
 * @test
 * @summary Verify that the unmodifiable collection view's retainAll always throws UnsupportedOperationException and leaves the backing collection unchanged
 * @run main CollectionsWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRetainAllWithNonEmptyCollectionThrows();
        testRetainAllWithNullThrows();
        testRetainAllWithEmptyCollectionThrows();
    }

    static void testRetainAllWithNonEmptyCollectionThrows() {
        List<String> backing = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collection<String> view = Collections.unmodifiableCollection(backing);
        expectUnsupported(view, Arrays.asList("a"));
        if (backing.size() != 3 || !backing.contains("b") || !backing.contains("c")) {
            throw new RuntimeException("backing collection was modified: " + backing);
        }
    }

    static void testRetainAllWithNullThrows() {
        List<String> backing = new ArrayList<>(Arrays.asList("x", "y"));
        Collection<String> view = Collections.unmodifiableCollection(backing);
        expectUnsupported(view, null);
        if (backing.size() != 2) {
            throw new RuntimeException("backing collection was modified: " + backing);
        }
    }

    static void testRetainAllWithEmptyCollectionThrows() {
        List<String> backing = new ArrayList<>(Arrays.asList("p"));
        Collection<String> view = Collections.unmodifiableCollection(backing);
        expectUnsupported(view, new ArrayList<String>());
        if (backing.size() != 1 || !backing.contains("p")) {
            throw new RuntimeException("backing collection was modified: " + backing);
        }
    }

    static void expectUnsupported(Collection<String> view, Collection<?> arg) {
        try {
            view.retainAll(arg);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException from retainAll");
    }
}
