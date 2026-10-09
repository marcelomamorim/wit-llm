/* @test    @summary Test for Vector.lastIndexOf(Object o, int index) method.    @run main VectorWitupTest_P3498e7bb569b */

import java.util.Vector;

public class VectorWitupTest_P3498e7bb569b {

    public static void main(String[] args) {
        testLastIndexOfWithNullObject();
        testLastIndexOfWithNonNullObject();
        testLastIndexOfWithIndexOutOfBounds();
        testLastIndexOfWithElementNotFound();
        testLastIndexOfWithElementFound();
    }

    static void testLastIndexOfWithNullObject() {
        Vector<String> v = new Vector<>();
        v.add(null);
        v.add("a");
        v.add(null);
        int index = v.lastIndexOf(null, v.size() - 1);
        if (index != 2) {
            throw new AssertionError("Expected index 2 for null element, got " + index);
        }
    }

    static void testLastIndexOfWithNonNullObject() {
        Vector<String> v = new Vector<>();
        v.add("x");
        v.add("y");
        v.add("z");
        int index = v.lastIndexOf("y", v.size() - 1);
        if (index != 1) {
            throw new AssertionError("Expected index 1 for element 'y', got " + index);
        }
    }

    static void testLastIndexOfWithIndexOutOfBounds() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        try {
            v.lastIndexOf("a", v.size());
            throw new AssertionError("Expected IndexOutOfBoundsException for index >= size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testLastIndexOfWithElementNotFound() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        int index = v.lastIndexOf("d", v.size() - 1);
        if (index != -1) {
            throw new AssertionError("Expected -1 for element not found, got " + index);
        }
    }

    static void testLastIndexOfWithElementFound() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("a");
        int index = v.lastIndexOf("a", v.size() - 1);
        if (index != 2) {
            throw new AssertionError("Expected index 2 for last occurrence of 'a', got " + index);
        }
    }
}
