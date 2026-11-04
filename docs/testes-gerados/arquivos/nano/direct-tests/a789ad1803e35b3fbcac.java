/*
 * @test
 * @summary Test that FixedList.add throws UnsupportedOperationException
 * @run main FixedListAddTest
 */

import com.sun.java.util.jar.pack.FixedList;

public class FixedListAddTest {

    public static void main(String[] args) {
        testAddThrowsException();
    }

    public static void testAddThrowsException() {
        FixedList<Object> list = new FixedList<Object>(3);
        boolean exceptionThrown = false;
        try {
            list.add(new Object());
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
