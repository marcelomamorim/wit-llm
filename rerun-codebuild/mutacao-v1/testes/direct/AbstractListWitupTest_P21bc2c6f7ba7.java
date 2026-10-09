/* @test    @summary Test AbstractList.set method throws UnsupportedOperationException in default implementation    @run main AbstractListWitupTest_P21bc2c6f7ba7 */

import java.util.AbstractList;

public class AbstractListWitupTest_P21bc2c6f7ba7 {
    public static void main(String[] args) {
        AbstractList<Object> list = new AbstractList<Object>() {
            @Override
            public Object get(int index) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }
        };
        try {
            list.set(0, new Object());
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
