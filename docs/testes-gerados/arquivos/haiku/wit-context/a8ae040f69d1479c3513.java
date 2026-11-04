/*
 * @test
 * @summary Verify that the iterator returned by Collections.unmodifiableCollection rejects remove() with UnsupportedOperationException and leaves the backing collection unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> backing = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> view = Collections.unmodifiableCollection(backing);

        Iterator<String> afterNext = view.iterator();
        if (!afterNext.hasNext()) {
            throw new RuntimeException("expected at least one element");
        }
        afterNext.next();
        checkRemoveUnsupported(afterNext);

        Iterator<String> beforeNext = view.iterator();
        checkRemoveUnsupported(beforeNext);

        if (backing.size() != 2 || view.size() != 2) {
            throw new RuntimeException("collection was modified by remove()");
        }
    }

    private static void checkRemoveUnsupported(Iterator<String> it) {
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException from remove()");
    }
}
