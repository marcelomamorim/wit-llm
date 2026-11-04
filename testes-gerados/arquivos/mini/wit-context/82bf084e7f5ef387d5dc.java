/*
 * @test
 * @summary Test FixedList.clear() throws UnsupportedOperationException
 * @run main FixedListWitupTest
 */

import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {
    public static void main(String[] args) {
        FixedList<Object> fl = new FixedList<Object>(1);
        try {
            fl.clear();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
