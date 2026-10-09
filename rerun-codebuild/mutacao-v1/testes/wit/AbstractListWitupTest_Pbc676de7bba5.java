/* @test    @summary Test remove method of AbstractList for UnsupportedOperationException    @run main AbstractListWitupTest_Pbc676de7bba5 */

import java.util.AbstractList;

public class AbstractListWitupTest_Pbc676de7bba5 {
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
            list.remove(0);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
