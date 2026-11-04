/*
 * @test
 * @summary Test FixedList.add(E) throws UnsupportedOperationException
 * @run main FixedListWitupTest
 */

import java.util.List;
import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {
    public static void main(String[] args) {
        FixedList<String> fl = new FixedList<>(1);
        try {
            fl.add("test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"operation not permitted".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
