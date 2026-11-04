/*
 * @test
 * @summary Verifies that set on an unmodifiable list view throws UnsupportedOperationException and leaves the backing list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> backing = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> view = Collections.unmodifiableList(backing);

        expectUnsupported(view, 0, "x");
        expectUnsupported(view, 2, null);
        expectUnsupported(view, 5, "y");
        expectUnsupported(view, -1, "z");

        if (!backing.equals(Arrays.asList("a", "b", "c"))) {
            throw new RuntimeException("backing list was modified: " + backing);
        }
        if (!view.equals(Arrays.asList("a", "b", "c"))) {
            throw new RuntimeException("view changed: " + view);
        }
    }

    static void expectUnsupported(List<String> list, int index, String element) {
        try {
            list.set(index, element);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for index " + index);
    }
}
