/*
 * @test
 * @summary Test FixedList.addAll(int, Collection) throws UnsupportedOperationException
 * @run main FixedListWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {
    public static void main(String[] args) {
        FixedList<String> fl = new FixedList<>(3);
        Collection<String> c = new ArrayList<>();
        c.add("a");
        c.add("b");

        // Test that addAll(int, Collection) throws UnsupportedOperationException
        try {
            fl.addAll(0, c);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        // Also test that the list remains unchanged and size is as expected
        if (fl.size() != 3) {
            throw new RuntimeException("FixedList size changed unexpectedly");
        }
        for (int i = 0; i < fl.size(); i++) {
            if (fl.get(i) != null) {
                throw new RuntimeException("FixedList element at index " + i + " expected to be null");
            }
        }
    }
}
