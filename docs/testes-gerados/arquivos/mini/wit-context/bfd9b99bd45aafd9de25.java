/*
 * @test
 * @summary Test ImmutableCollections.AbstractImmutableCollection.addAll throws UnsupportedOperationException
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
                public void remove() { throw new UnsupportedOperationException(); }
            };
        }

        @Override
        public int size() {
            return 0;
        }
    }

    public static void main(String[] args) {
        TestImmutableCollection<String> coll = new TestImmutableCollection<>();

        // addAll must throw UnsupportedOperationException
        try {
            coll.addAll(java.util.Arrays.asList("a", "b"));
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Also test other mutating methods throw UOE
        try {
            coll.add("x");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.clear();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.remove("x");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.removeAll(java.util.Arrays.asList("x"));
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.removeIf(new Predicate<String>() {
                public boolean test(String s) { return true; }
            });
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.retainAll(java.util.Arrays.asList("x"));
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
