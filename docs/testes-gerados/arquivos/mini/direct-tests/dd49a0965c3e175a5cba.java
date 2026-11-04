/*
 * @test
 * @summary Test that ImmutableCollections.AbstractImmutableCollection.set(E) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass instance to test set(E) method
        ImmutableCollections.AbstractImmutableCollection<Object> coll = new ImmutableCollections.AbstractImmutableCollection<Object>() {
            @Override
            public java.util.Iterator<Object> iterator() {
                return java.util.Collections.emptyIterator();
            }
            @Override
            public int size() {
                return 0;
            }
            // Expose set method for testing
            public void set(Object e) {
                throw ImmutableCollections.uoe();
            }
        };

        // Test that set throws UnsupportedOperationException
        try {
            coll.set(new Object());
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
