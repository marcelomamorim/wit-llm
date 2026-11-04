/*
 * @test
 * @summary Test FixedList.add(int, E) throws UnsupportedOperationException
 * @run main FixedListWitupTest
 */

import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {
    public static void main(String[] args) {
        FixedList<String> fl = new FixedList<String>(1);
        try {
            fl.add(0, "test");
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
