/*
 * @test
 * @summary Verify that pollFirst on an unmodifiable navigable set view throws UnsupportedOperationException and leaves the backing set unchanged
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

        boolean thrown = false;
        try {
            view.pollFirst();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("pollFirst did not throw UnsupportedOperationException");
        }
        if (base.size() != 2 || !"a".equals(base.first())) {
            throw new RuntimeException("backing set was modified by pollFirst");
        }
        if (view.size() != 2) {
            throw new RuntimeException("view size changed after failed pollFirst");
        }
    }
}
