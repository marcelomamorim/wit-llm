/*
 * @test
 * @summary Test LinkedList.add(int, E) for index bounds and normal insertion
 * @run main java.util.LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testAddAtValidIndices();
        testAddAtInvalidIndices();
    }

    private static void testAddAtValidIndices() {
        LinkedList<String> list = new LinkedList<>();
        // Add at index 0 in empty list (should append)
        list.add(0, "first");
        if (!"first".equals(list.get(0))) {
            throw new AssertionError("Element not added at index 0");
        }

        // Add at index 1 (end) in list with one element
        list.add(1, "second");
        if (!"second".equals(list.get(1))) {
            throw new AssertionError("Element not added at index 1");
        }

        // Add at index 1 (middle) in list with two elements
        list.add(1, "middle");
        if (!"middle".equals(list.get(1))) {
            throw new AssertionError("Element not added at index 1 (middle)");
        }

        // Verify list order
        String[] expected = {"first", "middle", "second"};
        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(list.get(i))) {
                throw new AssertionError("List order incorrect at index " + i);
            }
        }
    }

    private static void testAddAtInvalidIndices() {
        LinkedList<String> list = new LinkedList<>();
        list.add(0, "zero"); // valid

        // Test negative index
        try {
            list.add(-1, "neg");
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test index greater than size
        try {
            list.add(2, "tooFar");
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
