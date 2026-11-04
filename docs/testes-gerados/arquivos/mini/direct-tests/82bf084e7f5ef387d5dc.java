/*
 * @test
 * @summary Test FixedList.clear() throws UnsupportedOperationException
 * @run main FixedListWitupTest
 */

import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {
    public static void main(String[] args) {
        FixedList<Object> fl = new FixedList<>(3);
        boolean threw = false;
        try {
            fl.clear();
        } catch (UnsupportedOperationException e) {
            threw = true;
            if (!"operation not permitted".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        }
    }
}
