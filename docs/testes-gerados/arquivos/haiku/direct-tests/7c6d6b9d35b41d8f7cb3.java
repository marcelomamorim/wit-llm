/*
 * @test
 * @summary Verify that the unmodifiable list view rejects addAll(int, Collection) with UnsupportedOperationException and leaves the backing list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b"));
        List<String> view = Collections.unmodifiableList(base);

        expectUnsupported(view, Arrays.asList("c"));
        expectUnsupported(view, new ArrayList<String>());
        expectUnsupported(view, null);

        List<String> expected = Arrays.asList("a", "b");
        if (!base.equals(expected)) {
            throw new RuntimeException("backing list was modified: " + base);
        }
        if (!view.equals(expected)) {
            throw new RuntimeException("view changed: " + view);
        }
    }

    static void expectUnsupported(List<String> list, Collection<? extends String> c) {
        try {
            list.addAll(0, c);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException");
    }
}
