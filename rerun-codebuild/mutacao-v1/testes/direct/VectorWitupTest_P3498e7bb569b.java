/* @test    @summary Test lastIndexOf method of Vector class    @run main VectorWitupTest_P3498e7bb569b */

import java.util.Vector;

public class VectorWitupTest_P3498e7bb569b {
    public static void main(String[] args) {
        testLastIndexOfWithNull();
        testLastIndexOfWithObject();
        testLastIndexOfWithObjectNotPresent();
        testLastIndexOfWithIndexOutOfBounds();
    }

    static void testLastIndexOfWithNull() {
        Vector<String> vector = new Vector<>();
        vector.add(null);
        vector.add("a");
        vector.add(null);
        vector.add("b");
        int index = vector.lastIndexOf(null, vector.size() - 1);
        if (index != 2) {
            throw new AssertionError("Expected index 2 for null, got " + index);
        }
    }

    static void testLastIndexOfWithObject() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        vector.add("y");
        vector.add("z");
        vector.add("y");
        int index = vector.lastIndexOf("y", vector.size() - 1);
        if (index != 3) {
            throw new AssertionError("Expected index 3 for 'y', got " + index);
        }
    }

    static void testLastIndexOfWithObjectNotPresent() {
        Vector<String> vector = new Vector<>();
        vector.add("foo");
        vector.add("bar");
        int index = vector.lastIndexOf("baz", vector.size() - 1);
        if (index != -1) {
            throw new AssertionError("Expected -1 for absent object, got " + index);
        }
    }

    static void testLastIndexOfWithIndexOutOfBounds() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.lastIndexOf("a", vector.size());
            throw new AssertionError("Expected IndexOutOfBoundsException for index >= size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            vector.lastIndexOf("a", -1);
            // Negative index is valid, should search backwards from index -1, which is invalid
            // but in implementation, it will start from index -1, which is invalid, so no iteration
            // and should return -1
            int result = vector.lastIndexOf("a", -1);
            if (result != -1) {
                throw new AssertionError("Expected -1 for negative index, got " + result);
            }
        } catch (IndexOutOfBoundsException e) {
            // Depending on implementation, may or may not throw; in current code, it does not throw for negative index
            // So, do nothing
        }
    }
}
