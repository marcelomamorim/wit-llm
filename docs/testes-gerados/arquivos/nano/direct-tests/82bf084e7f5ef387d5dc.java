/* @test    @summary Test the clear() method of FixedList to ensure it throws UnsupportedOperationException.    @run main FixedListWitupTest */

import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {

    public static void main(String[] args) {
        testClearThrowsException();
    }

    public static void testClearThrowsException() {
        FixedList<Object> list = new FixedList<Object>(5);
        boolean exceptionThrown = false;
        try {
            list.clear();
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
