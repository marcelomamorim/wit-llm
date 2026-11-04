/* @test    @summary Test add method of FixedList to ensure it always throws UnsupportedOperationException    @run main FixedListWitupTest */

import com.sun.java.util.jar.pack.FixedList;
import java.util.List;

public class FixedListWitupTest {

    public static void main(String[] args) {
        testAddMethodThrowsException();
    }

    public static void testAddMethodThrowsException() {
        FixedList<Object> fixedList = new FixedList<Object>(5);
        try {
            fixedList.add(0, new Object());
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}
