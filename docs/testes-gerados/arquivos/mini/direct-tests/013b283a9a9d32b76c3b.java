/*
 * @test
 * @summary Test LinkedList.set(int, E) method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // Test set at valid index 0
        String old0 = list.set(0, "x");
        if (!"a".equals(old0)) {
            throw new RuntimeException("Expected old value 'a' at index 0, got: " + old0);
        }
        if (!"x".equals(list.get(0))) {
            throw new RuntimeException("Expected new value 'x' at index 0, got: " + list.get(0));
        }

        // Test set at valid index 1
        String old1 = list.set(1, "y");
        if (!"b".equals(old1)) {
            throw new RuntimeException("Expected old value 'b' at index 1, got: " + old1);
        }
        if (!"y".equals(list.get(1))) {
            throw new RuntimeException("Expected new value 'y' at index 1, got: " + list.get(1));
        }

        // Test set at valid index 2
        String old2 = list.set(2, "z");
        if (!"c".equals(old2)) {
            throw new RuntimeException("Expected old value 'c' at index 2, got: " + old2);
        }
        if (!"z".equals(list.get(2))) {
            throw new RuntimeException("Expected new value 'z' at index 2, got: " + list.get(2));
        }

        // Test set with null element
        String oldNull = list.set(1, null);
        if (!"y".equals(oldNull)) {
            throw new RuntimeException("Expected old value 'y' at index 1, got: " + oldNull);
        }
        if (list.get(1) != null) {
            throw new RuntimeException("Expected null at index 1, got: " + list.get(1));
        }

        // Test set with index out of bounds (negative)
        try {
            list.set(-1, "fail");
            throw new RuntimeException("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // Test set with index out of bounds (equal to size)
        try {
            list.set(list.size(), "fail");
            throw new RuntimeException("Expected IndexOutOfBoundsException for index size");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
