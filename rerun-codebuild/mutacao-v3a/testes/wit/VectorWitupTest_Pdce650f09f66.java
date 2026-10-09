/* @test    @summary Test for Vector.get method to verify exception behavior and normal access    @run main VectorWitupTest_Pdce650f09f66 */

import java.util.Vector;

public class VectorWitupTest_Pdce650f09f66 {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithInvalidIndex();
    }

    private static void testGetWithValidIndex() {
        Vector<Integer> vector = new Vector<>();
        vector.add(10);
        vector.add(20);
        try {
            int value = vector.get(1);
            if (value != 20) {
                throw new AssertionError("Expected 20 at index 1, but got " + value);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid index: " + e);
        }
    }

    private static void testGetWithInvalidIndex() {
        Vector<Integer> vector = new Vector<>();
        vector.add(10);
        int invalidIndex = 5;
        boolean exceptionThrown = false;
        try {
            vector.get(invalidIndex);
        } catch (ArrayIndexOutOfBoundsException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException, but got: " + e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index " + invalidIndex);
        }
    }
}
