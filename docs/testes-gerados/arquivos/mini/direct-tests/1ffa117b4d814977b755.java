/*
 * @test
 * @summary Test java.util.ArrayList.get(int) method for valid and invalid indices
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testGetValidIndex();
        testGetInvalidIndexNegative();
        testGetInvalidIndexTooLarge();
    }

    private static void testGetValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("zero");
        list.add("one");
        list.add("two");

        // Test get for valid indices
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

    private static void testGetInvalidIndexNegative() {
        ArrayList<String> list = new ArrayList<>();
        list.add("element");

        try {
            list.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testGetInvalidIndexTooLarge() {
        ArrayList<String> list = new ArrayList<>();
        list.add("element");

        try {
            list.get(1); // index == size
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        try {
            list.get(100); // index > size
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
