/* @test    @summary Test add method of AbstractCollection to ensure UnsupportedOperationException is thrown.    @run main AbstractCollectionWitupTest_Pefe055246f35 */

import java.util.AbstractCollection;

public class AbstractCollectionWitupTest_Pefe055246f35 {

    public static void main(String[] args) {
        testAddThrowsException();
    }

    public static void testAddThrowsException() {
        AbstractCollection<Object> collection = new AbstractCollection<Object>() {
            @Override
            public java.util.Iterator<Object> iterator() {
                return java.util.Collections.emptyIterator();
            }

            @Override
            public int size() {
                return 0;
            }
        };
        try {
            collection.add(new Object());
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}
