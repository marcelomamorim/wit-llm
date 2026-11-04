/*
 * @test
 * @summary Test FixedList.get(int) method behavior
 * @run main FixedListWitupTest
 */

import java.util.List;
import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {
    public static void main(String[] args) {
        FixedList<String> list = new FixedList<>(3);

        // Initially all elements are null
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                throw new AssertionError("Expected null at index " + i);
            }
        }

        // Set elements and verify get returns the set value
        list.set(0, "zero");
        list.set(1, "one");
        list.set(2, "two");

        if (!"zero".equals(list.get(0))) {
            throw new AssertionError("Expected 'zero' at index 0");
        }
        if (!"one".equals(list.get(1))) {
            throw new AssertionError("Expected 'one' at index 1");
        }
        if (!"two".equals(list.get(2))) {
            throw new AssertionError("Expected 'two' at index 2");
        }

        // Test get with invalid index (negative) - expect IndexOutOfBoundsException
        try {
            list.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
        }

        // Test get with invalid index (>= size) - expect IndexOutOfBoundsException
        try {
            list.get(list.size());
            throw new AssertionError("Expected IndexOutOfBoundsException for index size");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
