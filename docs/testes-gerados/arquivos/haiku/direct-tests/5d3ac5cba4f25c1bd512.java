/*
 * @test
 * @summary Verifies that replaceAll on an unmodifiable list view from Collections throws UnsupportedOperationException and leaves the backing list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNonEmptyViewThrowsAndKeepsContent();
        testEmptyViewThrows();
    }

    private static void testNonEmptyViewThrowsAndKeepsContent() {
        List<String> base = new ArrayList<>();
        base.add("a");
        base.add("b");
        List<String> view = Collections.unmodifiableList(base);
        UnaryOperator<String> op = s -> s + "!";

        boolean thrown = false;
        try {
            view.replaceAll(op);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException");
        }
        if (base.size() != 2 || !"a".equals(base.get(0)) || !"b".equals(base.get(1))) {
            throw new RuntimeException("Backing list was modified: " + base);
        }
        if (view.size() != 2 || !"a".equals(view.get(0)) || !"b".equals(view.get(1))) {
            throw new RuntimeException("View content changed: " + view);
        }
    }

    private static void testEmptyViewThrows() {
        List<String> base = new ArrayList<>();
        List<String> view = Collections.unmodifiableList(base);
        UnaryOperator<String> op = s -> s.toUpperCase();

        boolean thrown = false;
        try {
            view.replaceAll(op);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException on empty view");
        }
        if (!base.isEmpty()) {
            throw new RuntimeException("Backing list should remain empty");
        }
    }
}
