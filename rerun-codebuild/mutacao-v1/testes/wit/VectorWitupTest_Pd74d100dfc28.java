/* @test    @summary Test for Vector.set method, focusing on exception behavior.    @run main VectorWitupTest_Pd74d100dfc28 */

import java.util.Vector;

public class VectorWitupTest_Pd74d100dfc28 {
    public static void main(String[] args) {
        testSetWithValidIndex();
        testSetWithIndexEqualToSize();
        testSetWithNegativeIndex();
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

    private static void testSetWithIndexEqualToSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.set(1, "b");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testSetWithNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.set(-1, "b");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
