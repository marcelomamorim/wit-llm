/*
 * @test
 * @summary Test ImmutableCollections.clear() throws UnsupportedOperationException
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
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
