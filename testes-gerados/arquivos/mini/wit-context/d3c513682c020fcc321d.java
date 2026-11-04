/*
 * @test
 * @summary Test Vector.remove(int) for correct behavior and exception
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveInvalidIndex();
    }

    private static void testRemoveValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("zero");
        v.add("one");
        v.add("two");

        // Remove element at index 1, expect "one"
        String removed = v.remove(1);
        if (!"one".equals(removed)) {
            throw new RuntimeException("Expected removed element to be 'one', got: " + removed);
        }

        // After removal, size should be 2
        if (v.size() != 2) {
            throw new RuntimeException("Expected size 2 after removal, got: " + v.size());
        }

        // Remaining elements should be "zero" and "two"
        if (!"zero".equals(v.get(0)) || !"two".equals(v.get(1))) {
            throw new RuntimeException("Vector elements after removal are incorrect");
        }
    }

    private static void testRemoveInvalidIndex() {
        Vector<String> v = new Vector<>();
        v.add("only");

        // Attempt to remove at index equal to size (1), expect ArrayIndexOutOfBoundsException
        try {
            v.remove(1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }

        // Attempt to remove at negative index, expect ArrayIndexOutOfBoundsException
        try {
            v.remove(-1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
    }
}
