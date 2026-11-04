/*
 * @test
 * @summary Test that ImmutableCollections.AbstractImmutableCollection.addAll throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.Collections;
import java.util.AbstractCollection;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        AbstractImmutableCollection<String> coll = new AbstractImmutableCollection<String>() {
            public java.util.Iterator<String> iterator() {
                return Collections.emptyIterator();
            }
            public int size() {
                return 0;
            }
        };

        boolean thrown = false;
        try {
            coll.addAll(Collections.singleton("test"));
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from addAll");
        }
    }
}
