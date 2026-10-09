/*
 * @test    @summary Test for Vector.setElementAt method to verify index bounds behavior.
 * @run main VectorWitupTest_P21039156dcc6
 */

import java.util.Vector;

public class VectorWitupTest_P21039156dcc6 {

    public static void main(String[] args) {
        testSetElementAtWithInvalidIndex();
        testSetElementAtWithValidIndex();
    }

    private static void testSetElementAtWithInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        boolean exceptionThrown = false;
        try {
            // index is equal to size, should throw ArrayIndexOutOfBoundsException
            vector.setElementAt("x", vector.size());
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index >= size");
        }
    }

    private static void testSetElementAtWithValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.setElementAt("x", 1);
        if (!"x".equals(vector.get(1))) {
            throw new AssertionError("Element at index 1 should be 'x'");
        }
    }
}
