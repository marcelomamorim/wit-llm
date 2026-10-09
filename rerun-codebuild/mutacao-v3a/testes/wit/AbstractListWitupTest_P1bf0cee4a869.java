/* @test    @summary Test add() method of AbstractList for UnsupportedOperationException    @run main AbstractListWitupTest_P1bf0cee4a869 */

import java.util.AbstractList;

public class AbstractListWitupTest_P1bf0cee4a869 {

    public static void main(String[] args) {
        testAddThrowsUnsupportedOperationException();
    }

    public static void testAddThrowsUnsupportedOperationException() {
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
            list.add(0, new Object());
            System.err.println("FAIL: Expected UnsupportedOperationException not thrown");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
