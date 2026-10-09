/* @test    @summary Test AbstractList.set method throws UnsupportedOperationException by default    @run main AbstractListWitupTest_Pe0b2bd6895ac */

import java.util.AbstractList;

public class AbstractListWitupTest_Pe0b2bd6895ac {

    public static void main(String[] args) {
        testSetMethodThrows();
    }

    public static void testSetMethodThrows() {
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
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: set() method throws UnsupportedOperationException as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
