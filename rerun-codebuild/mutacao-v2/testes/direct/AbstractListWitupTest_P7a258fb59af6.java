/*
 * @test
 * @summary Test AbstractList.remove(int) throws UnsupportedOperationException
 * @run main AbstractListWitupTest_P7a258fb59af6
 */

import java.util.AbstractList;

public class AbstractListWitupTest_P7a258fb59af6 {
    public static void main(String[] args) {
        AbstractList<Object> list = new AbstractList<Object>() {
            public Object get(int index) {
                throw new IndexOutOfBoundsException();
            }
            public int size() {
                return 0;
            }
        };

        boolean thrown = false;
        try {
            list.remove(0);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        }
    }
}
