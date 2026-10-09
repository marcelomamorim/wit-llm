/* @test    @summary Test for Vector.remove(int index) method to verify exception handling and normal behavior    @run main VectorWitupTest_Pd68eda741473 */

import java.util.Vector;

public class VectorWitupTest_Pd68eda741473 {

    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveInvalidIndex();
    }

    private static void testRemoveValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        try {
            String removed = vector.remove(1);
            if (!"b".equals(removed)) {
                throw new AssertionError("Expected removed element to be 'b', but was: " + removed);
            }
            if (vector.size() != 2) {
                throw new AssertionError("Expected vector size 2 after removal, but was: " + vector.size());
            }
            if (!vector.get(0).equals("a") || !vector.get(1).equals("c")) {
                throw new AssertionError("Remaining elements do not match expected values.");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRemoveValidIndex: " + e);
        }
    }

    private static void testRemoveInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        try {
            vector.remove(5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 5, but no exception was thrown.");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
        try {
            vector.remove(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1, but no exception was thrown.");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
