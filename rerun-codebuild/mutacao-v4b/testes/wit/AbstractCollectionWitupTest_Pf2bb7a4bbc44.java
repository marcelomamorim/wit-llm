/* @test
   @summary Testa o método add de AbstractCollection para comportamento de exceção UnsupportedOperationException.
   @run main AbstractCollectionWitupTest_Pf2bb7a4bbc44
 */
import java.util.*;
public class AbstractCollectionWitupTest_Pf2bb7a4bbc44 {
    public static void main(String[] args) {
        Collection<Object> coll = new Collection<Object>() {
            @Override
            public Iterator<Object> iterator() { return Collections.emptyIterator(); }
            @Override
            public int size() { return 0; }
            @Override
            public boolean add(Object e) {
                try {
                    return new AbstractCollection<Object>() {
                        @Override
                        public Iterator<Object> iterator() { return Collections.emptyIterator(); }
                        @Override
                        public int size() { return 0; }
                        @Override
                        public boolean add(Object e) { return false; }
                    }.add(e);
                } catch (UnsupportedOperationException e) {
                    System.out.println("add() correctly throws UnsupportedOperationException");
                    return true;
                }
            }
            @Override
            public boolean remove(Object o) { return false; }
            @Override
            public boolean contains(Object o) { return false; }
            @Override
            public void clear() {}
            @Override
            public boolean isEmpty() { return true; }
            @Override
            public Object[] toArray() { return new Object[0]; }
            @Override
            public <T> T[] toArray(T[] a) { return a; }
        };
        boolean exceptionThrown = false;
        try {
            coll.add(new Object());
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
            System.out.println("add() threw UnsupportedOperationException as expected");
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException to be thrown by add()");
        }
    }
}
