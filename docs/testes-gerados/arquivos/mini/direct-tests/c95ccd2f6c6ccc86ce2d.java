/*
 * @test
 * @summary Test AbstractCollection.add(E) throws UnsupportedOperationException
 * @run main AbstractCollectionWitupTest
 */

import java.util.AbstractCollection;

public class AbstractCollectionWitupTest {
    public static void main(String[] args) {
        AbstractCollection<Object> col = new AbstractCollection<Object>() {
            public java.util.Iterator<Object> iterator() {
                return new java.util.Iterator<Object>() {
                    public boolean hasNext() { return false; }
                    public Object next() { throw new java.util.NoSuchElementException(); }
                    public void remove() { throw new UnsupportedOperationException(); }
                };
            }
            public int size() { return 0; }
        };

        try {
            col.add(new Object());
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
