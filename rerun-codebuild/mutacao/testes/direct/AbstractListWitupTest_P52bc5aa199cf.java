/* @test    @summary Test AbstractList.remove method throws UnsupportedOperationException in default implementation    @run main AbstractListWitupTest_P52bc5aa199cf */

import java.util.AbstractList;

public class AbstractListWitupTest_P52bc5aa199cf {
    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
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
            list.remove(0);
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: remove() throws UnsupportedOperationException as expected.");
            return;
        }
        System.out.println("FAIL: remove() did not throw UnsupportedOperationException.");
    }
}
