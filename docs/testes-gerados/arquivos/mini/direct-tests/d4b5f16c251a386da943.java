/*
 * @test
 * @summary Test FixedList.retainAll throws UnsupportedOperationException
 * @run main FixedListWitupTest
 */

import java.util.Collection;
import java.util.Collections;
import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {
    public static void main(String[] args) {
        FixedList<Object> fl = new FixedList<>(1);
        try {
            fl.retainAll(Collections.singleton(new Object()));
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
