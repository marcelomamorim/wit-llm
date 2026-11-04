/*
 * @test
 * @summary Verify that the unmodifiable list view returned by Collections.unmodifiableList rejects sort with UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> backing = new ArrayList<>();
        backing.add("b");
        backing.add("a");
        backing.add("c");
        List<String> view = Collections.unmodifiableList(backing);

        expectUnsupported(view, Comparator.naturalOrder());
        expectUnsupported(view, Comparator.reverseOrder());
        expectUnsupported(view, null);

        if (!backing.equals(Arrays.asList("b", "a", "c"))) {
            throw new AssertionError("backing list was modified by rejected sort");
        }
        if (!view.equals(Arrays.asList("b", "a", "c"))) {
            throw new AssertionError("unmodifiable view changed after rejected sort");
        }
    }

    static void expectUnsupported(List<String> list, Comparator<? super String> c) {
        try {
            list.sort(c);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException");
    }
}
