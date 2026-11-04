/*
 * @test
 * @summary Test that clear() method in AbstractImmutableCollection throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.AbstractCollection;
import java.util.function.Predicate;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableCollection<E> extends ImmutableCollections.AbstractImmutableCollection<E> {
        @Override
        public java.util.Iterator<E> iterator() {
            return new java.util.Iterator<E>() {
                public boolean hasNext() { return false; }
                public E next() { throw new java.util.NoSuchElementException(); }
            };
        }

        @Override
        public int size() {
            return 0;
        }
    }

    public static void main(String[] args) {
        TestImmutableCollection<Object> coll = new TestImmutableCollection<>();
        boolean threw = false;
        try {
            coll.clear();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("clear() did not throw UnsupportedOperationException");
        }
    }
}
