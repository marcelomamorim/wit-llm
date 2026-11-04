/*
 * @test
 * @summary Verify that pollFirst on an unmodifiable NavigableSet view throws UnsupportedOperationException and leaves the view unchanged
 * @run main CollectionsWitupTest
 */
import java.util.Collections;
import java.util.NavigableSet;
import java.util.TreeSet;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        NavigableSet<String> base = new TreeSet<>();
        base.add("a");
        base.add("b");
        NavigableSet<String> view = Collections.unmodifiableNavigableSet(base);
        try {
            view.pollFirst();
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }
        if (view.size() != 2) {
            throw new AssertionError("size changed: " + view.size());
        }
        if (!"a".equals(view.first())) {
            throw new AssertionError("first element changed: " + view.first());
        }

        NavigableSet<String> empty = Collections.unmodifiableNavigableSet(new TreeSet<String>());
        try {
            empty.pollFirst();
            throw new AssertionError("expected UnsupportedOperationException on empty view");
        } catch (UnsupportedOperationException expected) {
        }
        if (!empty.isEmpty()) {
            throw new AssertionError("empty view changed");
        }
    }
}
