/* @test    @summary Test removeElementAt method of java.util.Vector    @run main VectorWitupTest_P70396cbf4eaf */

import java.util.Vector;

public class VectorWitupTest_P70396cbf4eaf {
    public static void main(String[] args) {
        testRemoveElementAtValidIndex();
        testRemoveElementAtNegativeIndex();
        testRemoveElementAtIndexEqualToSize();
        testRemoveElementAtIndexGreaterThanSize();
    }

    private static void testRemoveElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        vector.removeElementAt(1);
        if (vector.size() != 2) {
            throw new AssertionError("Expected size 2, but got " + vector.size());
        }
        if (!vector.get(0).equals("a") || !vector.get(1).equals("c")) {
            throw new AssertionError("Elements not as expected after removal");
        }
    }

    private static void testRemoveElementAtNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        try {
            vector.removeElementAt(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRemoveElementAtIndexEqualToSize() {
        Vector<String> vector = new Vector<>();
        vector.add("y");
        vector.add("z");
        int size = vector.size();
        try {
            vector.removeElementAt(size);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRemoveElementAtIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("m");
        try {
            vector.removeElementAt(10);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index greater than size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
