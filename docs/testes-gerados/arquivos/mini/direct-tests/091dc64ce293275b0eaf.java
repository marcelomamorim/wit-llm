/*
 * @test
 * @summary Test that clear() method in AbstractImmutableCollection throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.AbstractCollection;
import java.util.function.Predicate;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableCollection<E> extends java.util.ImmutableCollections.AbstractImmutableCollection<E> {
        @Override
        public java.util.Iterator<E> iterator() {
            return new java.util.Iterator<E>() {
                public boolean hasNext() { return false; }
                public E next() { throw new java.util.NoSuchElementException(); }
            };
        }
        @Override
        public int size() { return 0; }
    }

    public static void main(String[] args) {
        TestImmutableCollection<Object> coll = new TestImmutableCollection<>();
        // Test that clear() throws UnsupportedOperationException
        try {
            coll.clear();
            throw new RuntimeException("clear() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Also test other mutating methods throw UOE
        try {
            coll.add(new Object());
            throw new RuntimeException("add() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {}

        try {
            coll.addAll(java.util.Collections.emptyList());
            throw new RuntimeException("addAll() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {}

        try {
            coll.remove(new Object());
            throw new RuntimeException("remove() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {}

        try {
            coll.removeAll(java.util.Collections.emptyList());
            throw new RuntimeException("removeAll() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {}

        try {
            coll.removeIf(new Predicate<Object>() {
                public boolean test(Object o) { return true; }
            });
            throw new RuntimeException("removeIf() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {}

        try {
            coll.retainAll(java.util.Collections.emptyList());
            throw new RuntimeException("retainAll() did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {}
    }
}
