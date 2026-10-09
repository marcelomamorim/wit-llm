/* @test    @summary Test for AbstractCollection.add method to verify UnsupportedOperationException is thrown as expected    @run main AbstractCollectionWitupTest_Pefe055246f35 */

import java.util.AbstractCollection;

public class AbstractCollectionWitupTest_Pefe055246f35 {

    public static void main(String[] args) {
        testAddThrowsException();
    }

    public static void testAddThrowsException() {
        AbstractCollection<Object> collection = new AbstractCollection<Object>() {
            @Override
            public java.util.Iterator<Object> iterator() {
                return new java.util.Iterator<Object>() {
                    public boolean hasNext() { return false; }
                    public Object next() { return null; }
                    public void remove() { }
                };
            }
            @Override
            public int size() { return 0; }
        };
        try {
            collection.add(new Object());
            // Fail if no exception
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
