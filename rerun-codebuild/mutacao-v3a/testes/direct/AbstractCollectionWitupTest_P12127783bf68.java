/* @test    @summary Test adding an element to AbstractCollection, expecting UnsupportedOperationException    @run main AbstractCollectionWitupTest_P12127783bf68 */

import java.util.AbstractCollection;

public class AbstractCollectionWitupTest_P12127783bf68 {
    public static void main(String[] args) {
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
        boolean exceptionThrown = false;
        try {
            collection.add(new Object());
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
