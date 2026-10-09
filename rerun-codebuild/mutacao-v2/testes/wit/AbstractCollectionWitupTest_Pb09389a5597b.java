/*
 * @test
 * @summary Test that AbstractCollection.add(E) throws UnsupportedOperationException
 * @run main AbstractCollectionWitupTest_Pb09389a5597b
 */

import java.util.AbstractCollection;

public class AbstractCollectionWitupTest_Pb09389a5597b {

    static class TestCollection extends AbstractCollection<Object> {
        @Override
        public java.util.Iterator<Object> iterator() {
            return new java.util.Iterator<Object>() {
                public boolean hasNext() { return false; }
                public Object next() { throw new java.util.NoSuchElementException(); }
                public void remove() { throw new UnsupportedOperationException(); }
            };
        }

        @Override
        public int size() {
            return 0;
        }
    }

    public static void main(String[] args) {
        AbstractCollection<Object> c = new TestCollection();
        boolean thrown = false;
        try {
            c.add(new Object());
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException from add method");
        }
    }
}
