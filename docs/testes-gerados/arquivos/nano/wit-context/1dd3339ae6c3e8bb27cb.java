/* @test    @summary Test for Vector.add(int, E) method to verify exception handling and normal behavior    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testAddWithValidIndex();
        testAddWithNegativeIndex();
        testAddWithIndexGreaterThanSize();
    }

    private static void testAddWithValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.add(1, "x");
            if (!"x".equals(vector.get(1))) {
                throw new AssertionError("Expected element at index 1 to be 'x'");
            }
            if (vector.size() != 3) {
                throw new AssertionError("Expected size 3 after insertion");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid add: " + e);
        }
    }

    private static void testAddWithNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.add(-1, "x");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testAddWithIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.add(5, "x");
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index greater than size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
