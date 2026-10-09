/* @test    @summary Test for AbstractList.add(int, E) method to verify UnsupportedOperationException is thrown as expected    @run main AbstractListWitupTest_Pc19e5496f295 */

import java.util.AbstractList;

public class AbstractListWitupTest_Pc19e5496f295 {
    public static void main(String[] args) {
        testAddThrowsException();
    }

    public static void testAddThrowsException() {
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
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes, exception was thrown as expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
