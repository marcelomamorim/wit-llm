/* @test    @summary Test for AbstractList#set method throwing UnsupportedOperationException    @run main AbstractListWitupTest_P912b1ce39070 */

import java.util.AbstractList;

public class AbstractListWitupTest_P912b1ce39070 {

    public static void main(String[] args) {
        testSetThrowsException();
    }

    public static void testSetThrowsException() {
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
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: set() threw UnsupportedOperationException as expected.");
        }
    }
}
