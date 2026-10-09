/* @test    @summary Test for Vector.get(int index) method, focusing on exception path for index out of bounds    @run main VectorWitupTest_Pecbc2c666239 */

import java.util.Vector;

public class VectorWitupTest_Pecbc2c666239 {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToSize();
        testGetWithIndexGreaterThanSize();
    }

    private static void testGetWithValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            String value = vector.get(1);
            if (!"b".equals(value)) {
                throw new AssertionError("Expected 'b', got: " + value);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid index: " + e);
        }
    }

    private static void testGetWithNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testGetWithIndexEqualToSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.get(1); // size is 1, index 1 is out of bounds
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testGetWithIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.get(2); // size is 1, index 2 is out of bounds
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index greater than size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
