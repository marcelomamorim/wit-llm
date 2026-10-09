/*
 * @test
 * @summary Test AbstractList.set(int, E) throws UnsupportedOperationException
 * @run main AbstractListWitupTest_Pdff4cf62044c
 */

import java.util.AbstractList;

public class AbstractListWitupTest_Pdff4cf62044c {
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
            list.set(0, null);
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
