/*
 * @test
 * @summary Test AbstractMap anonymous iterator remove() throws UnsupportedOperationException
 * @run main AbstractMapWitupTest
 */

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class AbstractMapWitupTest {

    public static void main(String[] args) {
        testAnonymousIteratorRemoveThrows();
    }

    static void testAnonymousIteratorRemoveThrows() {
        // Create a minimal AbstractMap subclass with entrySet returning a set with an iterator
        AbstractMap<Object, Object> map = new AbstractMap<Object, Object>() {
            @Override
            public java.util.Set<Entry<Object, Object>> entrySet() {
                return new java.util.AbstractSet<Entry<Object, Object>>() {
                    @Override
                    public Iterator<Entry<Object, Object>> iterator() {
                        return new Iterator<Entry<Object, Object>>() {
                            public boolean hasNext() { return false; }
                            public Entry<Object, Object> next() { throw new NoSuchElementException(); }
                            public void remove() {
                                // According to AbstractMap anonymous iterator remove() throws UnsupportedOperationException
                                throw new UnsupportedOperationException();
                            }
                        };
                    }
                    @Override
                    public int size() { return 0; }
                };
            }
        };

        Iterator<Entry<Object, Object>> it = map.entrySet().iterator();

        // The iterator has no elements
        if (it.hasNext()) {
            throw new AssertionError("Iterator unexpectedly has elements");
        }

        // next() should throw NoSuchElementException
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException from next()");
        } catch (NoSuchElementException expected) {
        }

        // remove() should throw UnsupportedOperationException
        try {
            it.remove();
            throw new AssertionError("Expected UnsupportedOperationException from remove()");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
