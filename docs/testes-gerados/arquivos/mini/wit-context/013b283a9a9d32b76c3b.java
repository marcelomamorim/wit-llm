/*
 * @test
 * @summary Test LinkedList.set(int, E) for valid and invalid indices
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testSetValidIndex();
        testSetInvalidIndex();
    }

    private static void testSetValidIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // Replace element at index 1
        String old = list.set(1, "x");
        if (!"b".equals(old)) {
            throw new AssertionError("Expected old value 'b', got: " + old);
        }
        if (!"x".equals(list.get(1))) {
            throw new AssertionError("Expected new value 'x' at index 1, got: " + list.get(1));
        }

        // Replace element at index 0
        old = list.set(0, "y");
        if (!"a".equals(old)) {
            throw new AssertionError("Expected old value 'a', got: " + old);
        }
        if (!"y".equals(list.get(0))) {
            throw new AssertionError("Expected new value 'y' at index 0, got: " + list.get(0));
        }

        // Replace element at last index
        old = list.set(list.size() - 1, "z");
        if (!"c".equals(old)) {
            throw new AssertionError("Expected old value 'c', got: " + old);
        }
        if (!"z".equals(list.get(list.size() - 1))) {
            throw new AssertionError("Expected new value 'z' at last index, got: " + list.get(list.size() - 1));
        }
    }

    private static void testSetInvalidIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");

        // index < 0
        try {
            list.set(-1, "x");
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // index == size
        try {
            list.set(list.size(), "x");
            throw new AssertionError("Expected IndexOutOfBoundsException for index == size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // index > size
        try {
            list.set(list.size() + 1, "x");
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
