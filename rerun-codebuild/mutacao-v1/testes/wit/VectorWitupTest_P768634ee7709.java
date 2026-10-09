/* @test    @summary Test insertElementAt with valid index and index out of bounds    @run main VectorWitupTest_P768634ee7709 */

import java.util.Vector;

public class VectorWitupTest_P768634ee7709 {
    public static void main(String[] args) {
        testInsertElementAtValidIndex();
        testInsertElementAtIndexGreaterThanSize();
    }

    static void testInsertElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.insertElementAt("x", 1);
            if (!"a".equals(vector.get(0)) || !"x".equals(vector.get(1)) || !"b".equals(vector.get(2))) {
                throw new AssertionError("Unexpected vector contents after insertElementAt at valid index");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception at valid index: " + e);
        }
    }

    static void testInsertElementAtIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.insertElementAt("x", 5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
            String message = e.getMessage();
            if (message == null || !message.contains(">")) {
                throw new AssertionError("Exception message does not contain expected '>' character");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
