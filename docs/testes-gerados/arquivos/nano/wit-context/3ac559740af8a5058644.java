/* @test    @summary Test for AbstractList.add(int, E) method to verify UnsupportedOperationException is thrown.    @run main AbstractListWitupTest */

import java.util.AbstractList;

public class AbstractListWitupTest {

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
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: UnsupportedOperationException was thrown as expected.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }
}
