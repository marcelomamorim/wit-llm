/* @test    @summary Test for Vector.remove(int) method.
 * @run main VectorWitupTest_P7f6d65cf3945
 */

import java.util.Vector;

public class VectorWitupTest_P7f6d65cf3945 {

    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveInvalidIndex();
    }

    private static void testRemoveValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        String removed = null;
        try {
            removed = vector.remove(1);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid index", e);
        }
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected removed element to be 'b', but was: " + removed);
        }
        if (vector.size() != 2) {
            throw new AssertionError("Expected vector size 2 after removal, but was: " + vector.size());
        }
        if (!vector.get(0).equals("a") || !vector.get(1).equals("c")) {
            throw new AssertionError("Vector elements after removal are incorrect: " + vector);
        }
    }

    private static void testRemoveInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        try {
            vector.remove(5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for invalid index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
        try {
            vector.remove(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
