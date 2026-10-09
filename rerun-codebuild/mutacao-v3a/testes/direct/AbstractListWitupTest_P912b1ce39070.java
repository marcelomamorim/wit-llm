/* @test    @summary Test AbstractList.set method throws UnsupportedOperationException in default implementation    @run main AbstractListWitupTest_P912b1ce39070 */

import java.util.AbstractList;

public class AbstractListWitupTest_P912b1ce39070 {

    public static void main(String[] args) {
        testSetMethodThrows();
    }

    public static void testSetMethodThrows() {
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
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("Expected UnsupportedOperationException not thrown.");
    }
}
