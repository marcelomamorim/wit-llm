/* @test    @summary Test for AbstractCollection.add method to observe exception behavior    @run main AbstractCollectionWitupTest_P9630dea3052e */

import java.util.AbstractCollection;

public class AbstractCollectionWitupTest_P9630dea3052e {

    public static void main(String[] args) {
        testAddThrowsUnsupportedOperationException();
    }

    private static void testAddThrowsUnsupportedOperationException() {
        AbstractCollection<Object> collection = new AbstractCollection<Object>() {
            @Override
            public java.util.Iterator<Object> iterator() {
                return new java.util.Iterator<Object>() {
                    public boolean hasNext() { return false; }
                    public Object next() { throw new java.util.NoSuchElementException(); }
                };
            }
            @Override
            public int size() { return 0; }
        };
        try {
            collection.add(new Object());
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
