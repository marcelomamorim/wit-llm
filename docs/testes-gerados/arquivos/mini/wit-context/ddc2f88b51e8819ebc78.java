/*
 * @test
 * @summary Test java.util.ArrayList.add(int, E) for valid and invalid indices
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testAddValidIndex();
        testAddInvalidIndexNegative();
        testAddInvalidIndexTooLarge();
    }

    private static void testAddValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "first");
        if (!"first".equals(list.get(0))) {
            throw new AssertionError("Element not added at index 0");
        }
        list.add(1, "second");
        if (!"second".equals(list.get(1))) {
            throw new AssertionError("Element not added at index 1");
        }
        list.add(1, "middle");
        if (!"middle".equals(list.get(1))) {
            throw new AssertionError("Element not inserted at index 1");
        }
        if (list.size() != 3) {
            throw new AssertionError("List size incorrect after adds");
        }
    }

    private static void testAddInvalidIndexNegative() {
        ArrayList<String> list = new ArrayList<>();
        try {
            list.add(-1, "invalid");
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testAddInvalidIndexTooLarge() {
        ArrayList<String> list = new ArrayList<>();
        list.add("only");
        try {
            list.add(2, "invalid"); // index > size (size=1)
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
