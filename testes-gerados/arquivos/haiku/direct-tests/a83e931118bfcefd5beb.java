/*
 * @test
 * @summary Verifies that remove(int) on an unmodifiable list view from Collections throws UnsupportedOperationException and leaves the list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> base = new ArrayList<>();
        base.add("a");
        base.add("b");
        List<String> view = Collections.unmodifiableList(base);

        boolean thrown = false;
        try {
            view.remove(0);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("remove(0) should throw UnsupportedOperationException");
        }
        if (view.size() != 2 || !"a".equals(view.get(0)) || !"b".equals(view.get(1))) {
            throw new AssertionError("list changed after failed remove(0)");
        }

        thrown = false;
        try {
            view.remove(5);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("remove(5) should throw UnsupportedOperationException");
        }

        base.add("c");
        if (view.size() != 3 || !"c".equals(view.get(2))) {
            throw new AssertionError("backing list changes should remain visible through the view");
        }
    }
}
