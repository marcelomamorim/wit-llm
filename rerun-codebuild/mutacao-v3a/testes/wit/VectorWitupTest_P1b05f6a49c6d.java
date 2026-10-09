/*
 * @test
 * @summary Tests for java.util.Vector.lastIndexOf(Object o, int index)
 * @run main VectorWitupTest_P1b05f6a49c6d
 */

import java.util.Vector;

public class VectorWitupTest_P1b05f6a49c6d {
    public static void main(String[] args) {
        testLastIndexOfWithNull();
        testLastIndexOfWithNonNull();
        testLastIndexOfWithIndexOutOfBounds();
        testLastIndexOfElementNotFound();
    }

    static void testLastIndexOfWithNull() {
        Vector<String> v = new Vector<>();
        v.add(null);
        v.add("a");
        v.add(null);
        int index = v.lastIndexOf(null, v.size() - 1);
        if (index != 2) {
            throw new AssertionError("Expected index 2, got " + index);
        }
    }

    static void testLastIndexOfWithNonNull() {
        Vector<String> v = new Vector<>();
        v.add("x");
        v.add("y");
        v.add("x");
        int index = v.lastIndexOf("x", v.size() - 1);
        if (index != 2) {
            throw new AssertionError("Expected index 2, got " + index);
        }
    }

    static void testLastIndexOfWithIndexOutOfBounds() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.lastIndexOf("a", v.size());
            throw new AssertionError("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testLastIndexOfElementNotFound() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        int index = v.lastIndexOf("c", v.size() - 1);
        if (index != -1) {
            throw new AssertionError("Expected -1, got " + index);
        }
    }
}
