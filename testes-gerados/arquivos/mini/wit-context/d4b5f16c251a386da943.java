/*
 * @test
 * @summary Test FixedList.retainAll throws UnsupportedOperationException as expected
 * @run main FixedListWitupTest
 */

import java.util.Collection;
import java.util.Collections;
import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {
    public static void main(String[] args) {
        FixedList<Object> fl = new FixedList<Object>(1);
        boolean thrown = false;
        try {
            fl.retainAll(Collections.singleton(new Object()));
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown by retainAll");
        }
    }
}
