/* @test    @summary Test for AbstractList.set method to verify UnsupportedOperationException behavior    @run main AbstractListWitupTest_Pe0b2bd6895ac */

import java.util.AbstractList;

public class AbstractListWitupTest_Pe0b2bd6895ac {

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
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            return;
        }
        // Fail the test if exception not thrown
        throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
    }
}
