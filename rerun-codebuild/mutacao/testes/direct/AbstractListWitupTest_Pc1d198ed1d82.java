/* @test    @summary Test for AbstractList.add(int, E) method to ensure it throws UnsupportedOperationException by default.    @run main AbstractListWitupTest_Pc1d198ed1d82 */

import java.util.AbstractList;

public class AbstractListWitupTest_Pc1d198ed1d82 {
    public static void main(String[] args) {
        testAddMethodThrows();
    }

    public static void testAddMethodThrows() {
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
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
