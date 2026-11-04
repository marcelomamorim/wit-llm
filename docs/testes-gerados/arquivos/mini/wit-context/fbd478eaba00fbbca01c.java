/*
 * @test
 * @summary Test FixedList.addAll(int, Collection) throws UnsupportedOperationException
 * @run main FixedListWitupTest
 */

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {
    public static void main(String[] args) {
        FixedList<Object> fl = new FixedList<Object>(1);
        Collection<Object> c = new ArrayList<Object>();
        c.add(new Object());
        try {
            fl.addAll(0, c);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
