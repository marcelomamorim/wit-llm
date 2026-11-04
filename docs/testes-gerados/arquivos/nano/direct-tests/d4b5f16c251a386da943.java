/* @test    @summary Test retainAll method of FixedList to ensure it throws UnsupportedOperationException.    @run main FixedListWitupTest */

import java.util.Arrays;
import java.util.Collection;
import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {

    public static void main(String[] args) {
        testRetainAllThrowsException();
    }

    public static void testRetainAllThrowsException() {
        FixedList<String> list = new FixedList<String>(3);
        list = new FixedList<String>(3);
        // Access internal list via reflection or assume constructor initializes with nulls
        // Since constructor initializes with nulls, set some values for test
        list.set(0, "a");
        list.set(1, "b");
        list.set(2, "c");
        Collection<String> c = Arrays.asList("a", "b");
        try {
            list.retainAll(c);
            // If no exception, fail the test
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
