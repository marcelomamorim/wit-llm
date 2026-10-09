/*
 * @test
 * @summary Test for Vector.set method handling of index out of bounds
 * @run main VectorWitupTest_P3afd2c52f309
 */

import java.util.Vector;

public class VectorWitupTest_P3afd2c52f309 {
    public static void main(String[] args) {
        testSetWithValidIndex();
        testSetWithNegativeIndex();
        testSetWithIndexEqualToSize();
    }

    private static void testSetWithValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        String oldValue = vector.set(1, "c");
        if (!"b".equals(oldValue)) {
            throw new AssertionError("Expected old value 'b', but got: " + oldValue);
        }
        if (!"c".equals(vector.get(1))) {
            throw new AssertionError("Expected new value 'c' at index 1, but got: " + vector.get(1));
        }
    }

    private static void testSetWithNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        boolean thrown = false;
        try {
            vector.set(-1, "x");
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        }
    }

    private static void testSetWithIndexEqualToSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        boolean thrown = false;
        try {
            vector.set(vector.size(), "x");
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to size");
        }
    }
}
