/* @test    @summary Test lastIndexOf method of Vector class    @run main VectorWitupTest_P98aac9999008 */

import java.util.Vector;

public class VectorWitupTest_P98aac9999008 {

    public static void main(String[] args) {
        testLastIndexOfWithNull();
        testLastIndexOfWithObject();
        testLastIndexOfWithIndexOutOfBounds();
    }

    static void testLastIndexOfWithNull() {
        Vector<String> vector = new Vector<>();
        vector.add(null);
        vector.add("a");
        vector.add(null);
        vector.add("b");
        vector.add(null);
        int index = vector.lastIndexOf(null, vector.size() - 1);
        assert index == 4 : "Expected index 4 for null, got " + index;
    }

    static void testLastIndexOfWithObject() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        vector.add("y");
        vector.add("z");
        vector.add("x");
        int index = vector.lastIndexOf("x", vector.size() - 1);
        assert index == 3 : "Expected index 3 for 'x', got " + index;
    }

    static void testLastIndexOfWithIndexOutOfBounds() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.lastIndexOf("a", vector.size());
            assert false : "Expected IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
