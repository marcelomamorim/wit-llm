/*
 * @test
 * @summary Test that ImmutableCollections.AbstractImmutableCollection.add throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collection;
import java.util.AbstractCollection;
import java.util.function.Predicate;
import java.util.Iterator;

public class ImmutableCollectionsWitupTest {

    static class TestImmutableCollection<E> extends ImmutableCollections.AbstractImmutableCollection<E> {
        private final E element;
        TestImmutableCollection(E element) {
            this.element = element;
        }
        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                boolean hasNext = true;
                public boolean hasNext() { return hasNext; }
                public E next() {
                    if (hasNext) {
                        hasNext = false;
                        return element;
                    } else {
                        throw new java.util.NoSuchElementException();
                    }
                }
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        @Override
        public int size() {
            return 1;
        }
    }

    public static void main(String[] args) {
        TestImmutableCollection<String> coll = new TestImmutableCollection<>("test");

        // Test add(E e) throws UnsupportedOperationException
        try {
            coll.add("fail");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by add");
        } catch (UnsupportedOperationException expected) {
        }

        // Test addAll(Collection<? extends E> c) throws UnsupportedOperationException
        try {
            coll.addAll(java.util.Collections.singleton("fail"));
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by addAll");
        } catch (UnsupportedOperationException expected) {
        }

        // Test clear() throws UnsupportedOperationException
        try {
            coll.clear();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by clear");
        } catch (UnsupportedOperationException expected) {
        }

        // Test remove(Object o) throws UnsupportedOperationException
        try {
            coll.remove("test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by remove");
        } catch (UnsupportedOperationException expected) {
        }

        // Test removeAll(Collection<?> c) throws UnsupportedOperationException
        try {
            coll.removeAll(java.util.Collections.singleton("test"));
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by removeAll");
        } catch (UnsupportedOperationException expected) {
        }

        // Test removeIf(Predicate<? super E> filter) throws UnsupportedOperationException
        try {
            coll.removeIf(new Predicate<String>() {
                public boolean test(String s) { return true; }
            });
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by removeIf");
        } catch (UnsupportedOperationException expected) {
        }

        // Test retainAll(Collection<?> c) throws UnsupportedOperationException
        try {
            coll.retainAll(java.util.Collections.singleton("test"));
            throw new RuntimeException("Expected UnsupportedOperationException not thrown by retainAll");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
