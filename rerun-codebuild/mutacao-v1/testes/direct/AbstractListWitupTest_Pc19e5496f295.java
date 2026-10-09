/* @test    @summary Test AbstractList.add(int, E) throws UnsupportedOperationException    @run main AbstractListWitupTest_Pc19e5496f295 */

import java.util.AbstractList;

public class AbstractListWitupTest_Pc19e5496f295 {
    public static void main(String[] args) {
        class DummyList extends AbstractList<Object> {
            @Override
            public Object get(int index) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }
        }
        
        AbstractList<Object> list = new DummyList();
        try {
            list.add(0, new Object());
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
