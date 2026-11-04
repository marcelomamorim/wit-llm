/*
 * @test
 * @summary Test AbstractList.remove(int) throws UnsupportedOperationException
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;

public class AbstractListWitupTest {
    public static void main(String[] args) {
        AbstractList<Object> list = new AbstractList<Object>() {
            public Object get(int index) {
                throw new IndexOutOfBoundsException();
            }
            public int size() {
                return 0;
            }
        };

        try {
            list.remove(0);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
