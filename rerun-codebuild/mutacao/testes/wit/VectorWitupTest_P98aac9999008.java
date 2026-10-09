/*
 * @test
 * @summary Tests for java.util.Vector.lastIndexOf(Object, int)
 * @run main VectorWitupTest_P98aac9999008
 */

import java.util.Vector;

public class VectorWitupTest_P98aac9999008 {

    public static void main(String[] args) {
        testLastIndexOfWithNullElement();
        testLastIndexOfWithNonNullElement();
        testLastIndexOfWithIndexOutOfBounds();
        testLastIndexOfWithElementNotFound();
    }

    static void testLastIndexOfWithNullElement() {
        Vector<String> vector = new Vector<>();
        vector.add(null);
        vector.add("a");
        vector.add(null);
        int index = vector.lastIndexOf(null, vector.size() - 1);
        if (index != 2) {
            throw new AssertionError("Expected index 2, but got " + index);
        }
    }

    static void testLastIndexOfWithNonNullElement() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        vector.add("y");
        vector.add("z");
        int index = vector.lastIndexOf("y", vector.size() - 1);
        if (index != 1) {
            throw new AssertionError("Expected index 1, but got " + index);
        }
    }

    static void testLastIndexOfWithIndexOutOfBounds() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.lastIndexOf("a", vector.size());
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    static void testLastIndexOfWithElementNotFound() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        int index = vector.lastIndexOf("c", vector.size() - 1);
        if (index != -1) {
            throw new AssertionError("Expected -1, but got " + index);
        }
    }
}
