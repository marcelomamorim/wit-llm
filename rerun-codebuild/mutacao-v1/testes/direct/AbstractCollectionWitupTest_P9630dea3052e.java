/* @test    @summary Test add method of AbstractCollection throws UnsupportedOperationException in default implementation    @run main AbstractCollectionWitupTest_P9630dea3052e */

import java.util.AbstractCollection;

public class AbstractCollectionWitupTest_P9630dea3052e {
    public static void main(String[] args) {
        AbstractCollection<Object> collection = new AbstractCollection<Object>() {
            @Override
            public java.util.Iterator<Object> iterator() {
                return new java.util.Iterator<Object>() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public Object next() {
                        throw new java.util.NoSuchElementException();
                    }
                };
            }

            @Override
            public int size() {
                return 0;
            }
        };
        try {
            boolean result = collection.add(new Object());
            throw new AssertionError("Expected UnsupportedOperationException, but add returned: " + result);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
