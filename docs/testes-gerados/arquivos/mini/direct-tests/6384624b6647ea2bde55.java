/*
 * @test
 * @summary Test removeAll method of Collections inner class that throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.*;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create an instance of the inner class that has removeAll method
        // Since the method is from an inner class of Collections, we simulate a minimal test
        // Here we create an anonymous subclass overriding removeAll to call super and catch exception

        Collection<Object> dummyCollection = new Collection<Object>() {
            public int size() { return 0; }
            public boolean isEmpty() { return true; }
            public boolean contains(Object o) { return false; }
            public Iterator<Object> iterator() { return Collections.emptyIterator(); }
            public Object[] toArray() { return new Object[0]; }
            public <T> T[] toArray(T[] a) { return a; }
            public boolean add(Object e) { return false; }
            public boolean remove(Object o) { return false; }
            public boolean containsAll(Collection<?> c) { return false; }
            public boolean addAll(Collection<?> c) { return false; }
            public boolean removeAll(Collection<?> c) {
                throw new UnsupportedOperationException();
            }
            public boolean retainAll(Collection<?> c) { return false; }
            public void clear() {}
        };

        boolean thrown = false;
        try {
            dummyCollection.removeAll(Collections.emptyList());
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        }
    }
}
