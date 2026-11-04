/*
 * @test
 * @summary Test Vector.add(int, E) for valid and invalid indices
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testAddValidIndex();
        testAddInvalidIndex();
    }

    private static void testAddValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("zero");
        v.add("one");
        // Add at index 1, valid
        v.add(1, "inserted");
        if (!"inserted".equals(v.get(1))) {
            throw new AssertionError("Element not inserted at index 1");
        }
        if (v.size() != 3) {
            throw new AssertionError("Vector size should be 3 after insertion");
        }
    }

    private static void testAddInvalidIndex() {
        Vector<String> v = new Vector<>();
        v.add("only");
        // According to WIT, add(index, element) throws ArrayIndexOutOfBoundsException if index > elementCount
        // Current elementCount is 1, so index 2 is invalid
        try {
            v.add(2, "fail");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 2");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
        // Also test negative index
        try {
            v.add(-1, "fail");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
