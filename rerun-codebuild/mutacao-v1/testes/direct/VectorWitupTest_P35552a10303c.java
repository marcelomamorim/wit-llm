/* @test    @summary Test setElementAt method of Vector for valid and invalid indices    @run main VectorWitupTest_P35552a10303c */

import java.util.Vector;

public class VectorWitupTest_P35552a10303c {
    public static void main(String[] args) {
        testSetElementAtValidIndex();
        testSetElementAtInvalidIndex();
    }

    static void testSetElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.setElementAt("z", 1);
        if (!"z".equals(vector.elementAt(1))) {
            throw new AssertionError("Expected 'z' at index 1, but found: " + vector.elementAt(1));
        }
    }

    static void testSetElementAtInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.setElementAt("x", 5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 5");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
