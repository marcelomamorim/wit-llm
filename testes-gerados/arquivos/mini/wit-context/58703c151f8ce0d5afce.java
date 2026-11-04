/*
 * @test
 * @summary Test that ImmutableCollections.AbstractImmutableCollection.clear() throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        ImmutableCollections.AbstractImmutableCollection<?> coll = new ImmutableCollections.AbstractImmutableCollection<Object>() {
            public java.util.Iterator<Object> iterator() {
                return java.util.Collections.emptyIterator();
            }
            public int size() {
                return 0;
            }
        };

        try {
            coll.clear();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
