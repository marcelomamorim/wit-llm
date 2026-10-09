/* @test    @summary Test insertElementAt method of Vector for correct insertion and exception handling    @run main VectorWitupTest_P5639e591077b */

import java.util.Vector;

public class VectorWitupTest_P5639e591077b {

    public static void main(String[] args) {
        testInsertElementAtNormal();
        testInsertElementAtOutOfBounds();
    }

    static void testInsertElementAtNormal() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.insertElementAt("x", 1);
        assert "a".equals(vector.get(0)) : "First element should be 'a'";
        assert "x".equals(vector.get(1)) : "Second element should be 'x'";
        assert "b".equals(vector.get(2)) : "Third element should be 'b'";
        assert vector.size() == 3 : "Size should be 3";
    }

    static void testInsertElementAtOutOfBounds() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.insertElementAt("x", 5);
            assert false : "Expected ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
