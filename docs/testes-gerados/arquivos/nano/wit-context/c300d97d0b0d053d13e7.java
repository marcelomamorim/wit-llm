/*
 * @test
 * @summary Test for FixedList.get(int) to verify behavior with valid and invalid indices.
 * @run main FixedListWitupTest
 */

import com.sun.java.util.jar.pack.FixedList;
import java.util.List;

public class FixedListWitupTest {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToSize();
    }

    static void testGetWithValidIndex() {
        FixedList<String> list = new FixedList<>(3);
        list.flist.set(0, "a");
        list.flist.set(1, "b");
        list.flist.set(2, "c");
        String result = list.get(1);
        if (!"b".equals(result)) {
            throw new AssertionError("Expected 'b', got: " + result);
        }
    }

    static void testGetWithNegativeIndex() {
        FixedList<String> list = new FixedList<>(2);
        list.flist.set(0, "x");
        list.flist.set(1, "y");
        try {
            list.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testGetWithIndexEqualToSize() {
        FixedList<String> list = new FixedList<>(2);
        list.flist.set(0, "x");
        list.flist.set(1, "y");
        try {
            list.get(2);
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
