/* @test    @summary Test for Vector.get(int index) method to ensure correct element retrieval and exception handling.    @run main VectorWitupTest_Pd230341287ee */

import java.util.Vector;

public class VectorWitupTest_Pd230341287ee {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithInvalidIndex();
    }

    private static void testGetWithValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        String element = vector.get(1);
        if (!"b".equals(element)) {
            throw new AssertionError("Expected 'b' at index 1, but got: " + element);
        }
    }

    private static void testGetWithInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        try {
            vector.get(5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 5");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            vector.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
