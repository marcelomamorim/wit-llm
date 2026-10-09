/*
 * @test
 * @summary Test Vector.insertElementAt(E obj, int index) for normal and exceptional behavior
 * @run main VectorWitupTest_P37824c4ef029
 */

import java.util.Vector;

public class VectorWitupTest_P37824c4ef029 {
    public static void main(String[] args) {
        testInsertElementAtNormal();
        testInsertElementAtIndexOutOfBounds();
    }

    private static void testInsertElementAtNormal() {
        Vector<String> v = new Vector<>(2);
        v.add("a");
        v.add("b");
        // Insert at index 1, should shift "b" to index 2
        v.insertElementAt("x", 1);
        if (!"a".equals(v.get(0))) {
            throw new AssertionError("Expected 'a' at index 0");
        }
        if (!"x".equals(v.get(1))) {
            throw new AssertionError("Expected 'x' at index 1");
        }
        if (!"b".equals(v.get(2))) {
            throw new AssertionError("Expected 'b' at index 2");
        }
        if (v.size() != 3) {
            throw new AssertionError("Expected size 3 after insertion");
        }
    }

    private static void testInsertElementAtIndexOutOfBounds() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        // index > elementCount should throw ArrayIndexOutOfBoundsException
        try {
            v.insertElementAt("x", 3); // elementCount is 2, index 3 > 2
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index > elementCount");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("3 > 2")) {
                throw new AssertionError("Exception message does not contain expected text: " + msg);
            }
        }
        // index == elementCount is allowed (inserts at end)
        try {
            v.insertElementAt("y", 2); // elementCount is 2, index 2 == elementCount
        } catch (Exception e) {
            throw new AssertionError("Did not expect exception for index == elementCount", e);
        }
        if (!"y".equals(v.get(2))) {
            throw new AssertionError("Expected 'y' at index 2 after insertion at elementCount");
        }
    }
}
