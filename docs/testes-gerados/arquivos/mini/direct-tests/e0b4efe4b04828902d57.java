/*
 * @test
 * @summary Test LinkedList.get(int) method for valid and invalid indices
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testGetValidIndex();
        testGetInvalidIndexNegative();
        testGetInvalidIndexTooLarge();
    }

    private static void testGetValidIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("zero");
        list.add("one");
        list.add("two");

        String first = list.get(0);
        if (!"zero".equals(first)) {
            throw new AssertionError("Expected 'zero' at index 0 but got: " + first);
        }

        String second = list.get(1);
        if (!"one".equals(second)) {
            throw new AssertionError("Expected 'one' at index 1 but got: " + second);
        }

        String third = list.get(2);
        if (!"two".equals(third)) {
            throw new AssertionError("Expected 'two' at index 2 but got: " + third);
        }
    }

    private static void testGetInvalidIndexNegative() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        try {
            list.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testGetInvalidIndexTooLarge() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        try {
            list.get(1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index >= size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
