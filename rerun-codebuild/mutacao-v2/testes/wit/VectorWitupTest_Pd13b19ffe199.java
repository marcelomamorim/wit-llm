/*
 * @test
 * @summary Test java.util.Vector.set(int, E) for correct behavior and exception
 * @run main VectorWitupTest_Pd13b19ffe199
 */

import java.util.Vector;

public class VectorWitupTest_Pd13b19ffe199 {
    public static void main(String[] args) {
        testSetValidIndex();
        testSetInvalidIndex();
    }

    private static void testSetValidIndex() {
        Vector<String> v = new Vector<>(2);
        v.add("zero");
        v.add("one");

        // Replace element at index 1 and check returned old value
        String old = v.set(1, "ONE");
        if (!"one".equals(old)) {
            throw new AssertionError("Expected old value 'one', got: " + old);
        }

        // Check that new value is set
        String current = v.get(1);
        if (!"ONE".equals(current)) {
            throw new AssertionError("Expected new value 'ONE', got: " + current);
        }
    }

    private static void testSetInvalidIndex() {
        Vector<String> v = new Vector<>();
        v.add("only");

        try {
            v.set(1, "fail"); // index 1 is out of bounds (size=1)
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }

        try {
            v.set(-1, "fail"); // negative index also invalid
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
