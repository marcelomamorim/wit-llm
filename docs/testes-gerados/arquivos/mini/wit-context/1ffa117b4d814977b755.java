/*
 * @test
 * @summary Test ArrayList.get(int) for valid and invalid indices
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

        // Test valid indices
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
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testGetInvalidIndexTooLarge() {
        ArrayList<String> list = new ArrayList<>();
        list.add("element");

        try {
            list.get(1); // index == size
            throw new AssertionError("Expected IndexOutOfBoundsException for index 1");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }

        try {
            list.get(100); // index > size
            throw new AssertionError("Expected IndexOutOfBoundsException for index 100");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
