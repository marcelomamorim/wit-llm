/* @test    @summary Test for Vector.get(int index) method, covering normal and out-of-bounds cases    @run main VectorWitupTest_Pd230341287ee */

import java.util.Vector;

public class VectorWitupTest_Pd230341287ee {

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
        String result = vector.get(1);
        if (!"b".equals(result)) {
            throw new AssertionError("Expected 'b', but got: " + result);
        }
    }

    private static void testGetWithNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetWithIndexEqualToSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.get(2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetWithIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.get(3);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index greater than size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
