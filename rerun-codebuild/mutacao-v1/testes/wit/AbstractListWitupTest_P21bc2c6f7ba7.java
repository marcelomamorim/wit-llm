/* @test    @summary Test for AbstractList.set method to verify UnsupportedOperationException is thrown as expected    @run main AbstractListWitupTest_P21bc2c6f7ba7 */

import java.util.AbstractList;

public class AbstractListWitupTest_P21bc2c6f7ba7 {
    public static void main(String[] args) {
        testSetMethodThrowsException();
    }

    public static void testSetMethodThrowsException() {
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
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes as exception is expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
