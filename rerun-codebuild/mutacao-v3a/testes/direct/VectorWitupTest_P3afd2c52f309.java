/* @test    @summary Test for Vector.set method to ensure correct element replacement and exception handling.    @run main VectorWitupTest_P3afd2c52f309 */

import java.util.Vector;

public class VectorWitupTest_P3afd2c52f309 {

    public static void main(String[] args) {
        testSetReplacesElement();
        testSetWithIndexEqualToSizeThrows();
    }

    private static void testSetReplacesElement() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        String old = vector.set(1, "c");
        if (!"b".equals(old)) {
            throw new AssertionError("Expected old value 'b', but got: " + old);
        }
        if (!"c".equals(vector.get(1))) {
            throw new AssertionError("Element at index 1 should be 'c', but was: " + vector.get(1));
        }
    }

    private static void testSetWithIndexEqualToSizeThrows() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.set(1, "b");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index >= size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
