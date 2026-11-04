/*
 * @test    @summary Test setElementAt method of Vector class
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testSetElementAtValidIndex();
        testSetElementAtInvalidIndex();
    }

    static void testSetElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.setElementAt("z", 1);
        assert "z".equals(vector.elementAt(1)) : "Element at index 1 should be 'z'";
    }

    static void testSetElementAtInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.setElementAt("x", 5);
            assert false : "Expected ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}