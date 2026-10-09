/* @test    @summary Test for AbstractCollection.add method to observe UnsupportedOperationException behavior    @run main AbstractCollectionWitupTest_P12127783bf68 */

import java.util.AbstractCollection;

public class AbstractCollectionWitupTest_P12127783bf68 {

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
            System.out.println("Test failed: No exception thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException thrown as expected");
        }
    }
}
