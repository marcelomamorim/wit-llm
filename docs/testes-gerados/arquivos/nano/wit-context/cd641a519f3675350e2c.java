/*
 * @test
 * @summary Tests for java.util.Vector insertElementAt method
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testInsertElementAtValidIndex();
        testInsertElementAtInvalidIndex();
    }

    private static void testInsertElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.insertElementAt("x", 1);
        assert "a".equals(vector.get(0)) : "Expected 'a' at index 0";
        assert "x".equals(vector.get(1)) : "Expected 'x' at index 1";
        assert "b".equals(vector.get(2)) : "Expected 'b' at index 2";
    }

    private static void testInsertElementAtInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.insertElementAt("x", 5);
            assert false : "Expected ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            assert e.getMessage() != null : "Exception message should not be null";
        }
    }
}
