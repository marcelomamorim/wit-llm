/* @test    @summary Test for Vector.setElementAt to verify exception handling and normal behavior    @run main VectorWitupTest_P35552a10303c */

import java.util.Vector;

public class VectorWitupTest_P35552a10303c {

    public static void main(String[] args) {
        testSetElementAtValidIndex();
        testSetElementAtInvalidIndex();
    }

    private static void testSetElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.setElementAt("z", 1);
            if (!"z".equals(vector.elementAt(1))) {
                throw new AssertionError("Expected element at index 1 to be 'z'");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid index: " + e);
        }
    }

    private static void testSetElementAtInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.setElementAt("z", 5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for invalid index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
