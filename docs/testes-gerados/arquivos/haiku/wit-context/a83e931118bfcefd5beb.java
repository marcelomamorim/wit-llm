/*
 * @test
 * @summary Verify that remove(int) on an unmodifiable list view throws UnsupportedOperationException and leaves the backing list unchanged
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

        expectUnsupported(() -> view.remove(0));
        expectUnsupported(() -> view.remove(1));
        expectUnsupported(() -> view.remove(10));
        expectUnsupported(() -> view.remove(-1));

        if (view.size() != 3) {
            throw new RuntimeException("view size changed: " + view.size());
        }
        if (!backing.equals(Arrays.asList("a", "b", "c"))) {
            throw new RuntimeException("backing list changed: " + backing);
        }
    }

    private static void expectUnsupported(Runnable action) {
        try {
            action.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("UnsupportedOperationException was not thrown");
    }
}
