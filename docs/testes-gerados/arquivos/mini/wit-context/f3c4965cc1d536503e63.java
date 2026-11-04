/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableCollection.retainAll throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.Collections;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass instance of AbstractImmutableCollection to test retainAll
        ImmutableCollections.AbstractImmutableCollection<Object> coll = new ImmutableCollections.AbstractImmutableCollection<Object>() {
            @Override
            public java.util.Iterator<Object> iterator() {
                return Collections.emptyIterator();
            }
            @Override
            public int size() {
                return 0;
            }
        };

        boolean uoeThrown = false;
        try {
            coll.retainAll(Collections.singleton(new Object()));
        } catch (UnsupportedOperationException e) {
            uoeThrown = true;
        }

        if (!uoeThrown) {
            throw new AssertionError("Expected UnsupportedOperationException from retainAll");
        }
    }
}
