/*
 * @test
 * @summary Test AbstractList.add(int, E) throws UnsupportedOperationException
 * @run main AbstractListWitupTest_P47bfd9278a49
 */

import java.util.AbstractList;

public class AbstractListWitupTest_P47bfd9278a49 {
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
            list.add(0, new Object());
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
