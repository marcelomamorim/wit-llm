/*
 * @test
 * @summary Test AbstractMap anonymous inner class remove() method throws UnsupportedOperationException
 * @run main AbstractMapWitupTest
 */

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.NoSuchElementException;

public class AbstractMapWitupTest {

    public static void main(String[] args) {
        // Create a minimal AbstractMap subclass with a fixed entrySet
        AbstractMap<String, String> map = new AbstractMap<String, String>() {
            @Override
            public Set<Entry<String, String>> entrySet() {
                return new Set<Entry<String, String>>() {
                    public int size() { return 1; }
                    public boolean isEmpty() { return false; }
                    public boolean contains(Object o) { return false; }
                    public Iterator<Entry<String, String>> iterator() {
                        return new Iterator<Entry<String, String>>() {
                            private boolean hasNext = true;
                            public boolean hasNext() { return hasNext; }
                            public Entry<String, String> next() {
                                if (!hasNext) throw new NoSuchElementException();
                                hasNext = false;
                                return new Entry<String, String>() {
                                    public String getKey() { return "key"; }
                                    public String getValue() { return "value"; }
                                    public String setValue(String value) {
                                        throw new UnsupportedOperationException();
                                    }
                                };
                            }
                            public void remove() {
                                // This remove method is expected to throw UnsupportedOperationException
                                throw new UnsupportedOperationException();
                            }
                        };
                    }
                    public Object[] toArray() { throw new UnsupportedOperationException(); }
                    public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
                    public boolean add(Entry<String, String> e) { throw new UnsupportedOperationException(); }
                    public boolean remove(Object o) { throw new UnsupportedOperationException(); }
                    public boolean containsAll(java.util.Collection<?> c) { return false; }
                    public boolean addAll(java.util.Collection<? extends Entry<String, String>> c) { throw new UnsupportedOperationException(); }
                    public boolean retainAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
                    public boolean removeAll(java.util.Collection<?> c) { throw new UnsupportedOperationException(); }
                    public void clear() { throw new UnsupportedOperationException(); }
                };
            }
        };

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        Map.Entry<String, String> entry = it.next();
        if (!"key".equals(entry.getKey()) || !"value".equals(entry.getValue())) {
            throw new AssertionError("Entry key or value mismatch");
        }

        boolean thrown = false;
        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from remove()");
        }
    }
}
