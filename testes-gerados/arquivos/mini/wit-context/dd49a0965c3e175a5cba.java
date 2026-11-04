/*
 * @test
 * @summary Test ImmutableCollections.set(E) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        try {
            // The set method always throws UnsupportedOperationException
            ImmutableCollections.ListItr<Object> itr = new ImmutableCollections.ListItr<Object>() {
                @Override
                public boolean hasNext() { return false; }
                @Override
                public Object next() { return null; }
                @Override
                public void set(Object e) {
                    // call the tested method
                    throw ImmutableCollections.uoe();
                }
                @Override
                public void add(Object e) { throw ImmutableCollections.uoe(); }
                @Override
                public void remove() { throw ImmutableCollections.uoe(); }
            };
            itr.set(new Object());
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
