/* @test    @summary Test AbstractList.remove(int) throws UnsupportedOperationException    @run main AbstractListWitupTest_Pbc676de7bba5 */

import java.util.AbstractList;

public class AbstractListWitupTest_Pbc676de7bba5 {
    public static void main(String[] args) {
        class DummyList extends AbstractList<Object> {
            @Override
            public Object get(int index) {
                return null;
            }

            @Override
            public int size() {
                return 1;
            }
        }
        
        AbstractList<Object> list = new DummyList();
        try {
            list.remove(0);
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
