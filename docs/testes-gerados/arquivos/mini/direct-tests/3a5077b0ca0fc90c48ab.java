/*
 * @test
 * @summary Test java.util.ArrayList.get(int) method behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testGetValidIndex();
        testGetInvalidIndex();
    }

    private static void testGetValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("zero");
        list.add("one");
        list.add("two");

        // Test get at valid indices
        if (!"zero".equals(list.get(0))) {
            throw new AssertionError("Expected 'zero' at index 0");
        }
        if (!"one".equals(list.get(1))) {
            throw new AssertionError("Expected 'one' at index 1");
        }
        if (!"two".equals(list.get(2))) {
            throw new AssertionError("Expected 'two' at index 2");
        }
    }

    private static void testGetInvalidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("only");

        // Test get with negative index
        try {
            list.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
        }

        // Test get with index equal to size
        try {
            list.get(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index == size");
        } catch (IndexOutOfBoundsException expected) {
        }

        // Test get with index greater than size
        try {
            list.get(100);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
