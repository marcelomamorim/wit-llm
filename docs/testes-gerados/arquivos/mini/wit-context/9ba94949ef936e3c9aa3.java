/*
 * @test
 * @summary Test Collections.UnmodifiableCollection.clear() throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testUnmodifiableCollectionClearThrows();
    }

    private static void testUnmodifiableCollectionClearThrows() {
        Collection<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        Collection<String> unmodifiable = Collections.unmodifiableCollection(list);
        boolean threw = false;
        try {
            unmodifiable.clear();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException from unmodifiable.clear()");
        }
    }
}
