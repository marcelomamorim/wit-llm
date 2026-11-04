/*
 * @test
 * @summary Test ImmutableCollections.remove(Object) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        ImmutableCollections.AbstractImmutableCollection<Object> coll = new ImmutableCollections.AbstractImmutableCollection<Object>() {
            public java.util.Iterator<Object> iterator() {
                return java.util.Collections.emptyIterator();
            }
            public int size() {
                return 0;
            }
        };

        boolean uoeThrown = false;
        try {
            coll.remove(new Object());
        } catch (UnsupportedOperationException e) {
            uoeThrown = true;
        }

        if (!uoeThrown) {
            throw new AssertionError("Expected UnsupportedOperationException from remove(Object)");
        }
    }
}
