/* @test    @summary Test insertElementAt method of Vector for valid and invalid indices    @run main VectorWitupTest_P768634ee7709 */

import java.util.Vector;

public class VectorWitupTest_P768634ee7709 {
    public static void main(String[] args) {
        testInsertElementAtValidIndex();
        testInsertElementAtNegativeIndex();
        testInsertElementAtIndexGreaterThanSize();
    }

    private static void testInsertElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        try {
            vector.insertElementAt("x", 1);
            if (!"x".equals(vector.get(1))) {
                throw new AssertionError("Element not inserted at correct position");
            }
            if (vector.size() != 4) {
                throw new AssertionError("Vector size incorrect after insertion");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid index insertion: " + e);
        }
    }

    private static void testInsertElementAtNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.insertElementAt("x", -1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for negative index: " + e);
        }
    }

    private static void testInsertElementAtIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.insertElementAt("x", 5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index greater than size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for index greater than size: " + e);
        }
    }
}
