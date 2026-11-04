/*
 * @test
 * @summary Verifies that add on an unmodifiable collection view throws UnsupportedOperationException and leaves the view unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> backing = new ArrayList<>();
        backing.add("a");
        Collection<String> view = Collections.unmodifiableCollection(backing);
        boolean thrown = false;
        try {
            view.add("b");
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("add should throw UnsupportedOperationException");
        }
        if (view.size() != 1 || !view.contains("a") || view.contains("b")) {
            throw new AssertionError("view changed after rejected add");
        }
        if (backing.size() != 1) {
            throw new AssertionError("backing collection changed after rejected add");
        }
    }
}
