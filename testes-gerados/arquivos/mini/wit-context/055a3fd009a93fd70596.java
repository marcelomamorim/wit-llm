/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableCollection.add(E) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass instance of AbstractImmutableCollection to test add(E)
        ImmutableCollections.AbstractImmutableCollection<Object> coll = new ImmutableCollections.AbstractImmutableCollection<Object>() {
            @Override
            public java.util.Iterator<Object> iterator() {
                return java.util.Collections.emptyIterator();
            }
            @Override
            public int size() {
                return 0;
            }
        };

        try {
            coll.add(new Object());
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
