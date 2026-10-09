/* @test    @summary Test Vector.set method for valid and invalid indices    @run main VectorWitupTest_Pd74d100dfc28 */

import java.util.Vector;

public class VectorWitupTest_Pd74d100dfc28 {

    public static void main(String[] args) {
        testSetValidIndex();
        testSetInvalidIndex();
    }

    private static void testSetValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        String oldValue = vector.set(1, "new_b");
        if (!"b".equals(oldValue)) {
            throw new AssertionError("Expected old value 'b', but got: " + oldValue);
        }
        if (!"new_b".equals(vector.get(1))) {
            throw new AssertionError("Expected new value 'new_b' at index 1, but got: " + vector.get(1));
        }
    }

    private static void testSetInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.set(5, "x");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 5");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        try {
            vector.set(-1, "x");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
