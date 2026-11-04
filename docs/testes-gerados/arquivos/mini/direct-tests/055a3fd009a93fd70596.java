/*
 * @test
 * @summary Test that add(E) method in AbstractImmutableCollection throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.AbstractCollection;
import java.util.function.Predicate;
import java.util.Iterator;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableCollection<E> extends ImmutableCollections.AbstractImmutableCollection<E> {
        // Provide minimal implementation to allow instantiation
        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                public boolean hasNext() { return false; }
                public E next() { return null; }
            };
        }

        @Override
        public int size() {
            return 0;
        }
    }

    public static void main(String[] args) {
        TestImmutableCollection<String> coll = new TestImmutableCollection<>();

        // Test that add throws UnsupportedOperationException
        try {
            coll.add("test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }

        // Also test other mutating methods throw UOE
        try {
            coll.addAll(null);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown for addAll");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.clear();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown for clear");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.remove("test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown for remove");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.removeAll(null);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown for removeAll");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.removeIf(null);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown for removeIf");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            coll.retainAll(null);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown for retainAll");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
