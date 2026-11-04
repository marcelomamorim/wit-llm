/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableCollection.removeIf throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.function.Predicate;
import java.util.AbstractCollection;
import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of AbstractImmutableCollection to test removeIf
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

        Predicate<Object> alwaysTrue = new Predicate<Object>() {
            public boolean test(Object o) {
                return true;
            }
        };

        try {
            coll.removeIf(alwaysTrue);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
