/*
 * @test
 * @summary Test Vector.setElementAt(E obj, int index) for correct behavior and exception
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testSetElementAtValidIndex();
        testSetElementAtIndexOutOfBounds();
    }

    private static void testSetElementAtValidIndex() {
        Vector<String> v = new Vector<>(3);
        v.add("a");
        v.add("b");
        v.add("c");

        // Replace element at index 1
        v.setElementAt("x", 1);

        if (!"x".equals(v.get(1))) {
            throw new AssertionError("setElementAt did not replace element correctly");
        }

        // Replace element at index 0
        v.setElementAt("y", 0);
        if (!"y".equals(v.get(0))) {
            throw new AssertionError("setElementAt did not replace element correctly at index 0");
        }

        // Replace element at last valid index
        v.setElementAt("z", 2);
        if (!"z".equals(v.get(2))) {
            throw new AssertionError("setElementAt did not replace element correctly at last index");
        }
    }

    private static void testSetElementAtIndexOutOfBounds() {
        Vector<String> v = new Vector<>(2);
        v.add("a");
        v.add("b");

        // index == elementCount (2) should throw ArrayIndexOutOfBoundsException
        try {
            v.setElementAt("x", 2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index == elementCount");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }

        // index > elementCount (e.g. 3) should throw ArrayIndexOutOfBoundsException
        try {
            v.setElementAt("y", 3);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index > elementCount");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }

        // negative index should throw ArrayIndexOutOfBoundsException
        try {
            v.setElementAt("z", -1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
