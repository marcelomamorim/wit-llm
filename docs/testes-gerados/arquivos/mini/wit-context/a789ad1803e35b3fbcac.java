/*
 * @test
 * @summary Test FixedList.add(E) throws UnsupportedOperationException as expected
 * @run main FixedListWitupTest
 */

import java.util.List;
import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {
    public static void main(String[] args) {
        FixedList<String> fl = new FixedList<String>(1);
        try {
            fl.add("test");
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
