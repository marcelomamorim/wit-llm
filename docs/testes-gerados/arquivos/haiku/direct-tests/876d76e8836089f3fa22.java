/*
 * @test
 * @summary Verifies that the unmodifiable list view returned by Collections.unmodifiableList rejects add(int, E) with UnsupportedOperationException and leaves the backing list unchanged.
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> base = new ArrayList<>();
        base.add("a");
        List<String> view = Collections.unmodifiableList(base);

        expectUOE(view, 0, "x");
        expectUOE(view, 1, "y");
        expectUOE(view, 0, null);
        expectUOE(view, 5, "z");

        if (base.size() != 1 || !"a".equals(base.get(0))) {
            throw new RuntimeException("backing list was modified: " + base);
        }
        if (view.size() != 1 || !"a".equals(view.get(0))) {
            throw new RuntimeException("view was modified: " + view);
        }
    }

    static void expectUOE(List<String> list, int index, String element) {
        try {
            list.add(index, element);
        } catch (UnsupportedOperationException ex) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for index " + index);
    }
}
