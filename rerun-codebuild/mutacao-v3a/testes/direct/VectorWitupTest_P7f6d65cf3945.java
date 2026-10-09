/* @test    @summary Test java.util.Vector.remove(int index) for correct removal behavior    @run main VectorWitupTest_P7f6d65cf3945 */

import java.util.Vector;

public class VectorWitupTest_P7f6d65cf3945 {
    public static void main(String[] args) {
        testRemoveAtValidIndex();
        testRemoveAtNegativeIndex();
        testRemoveAtIndexEqualToSize();
        testRemoveAtIndexGreaterThanSize();
    }

    public static void testRemoveAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        String removed = vector.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected to remove 'b', but got: " + removed);
        }
        if (vector.size() != 2) {
            throw new AssertionError("Expected size 2 after removal, but got: " + vector.size());
        }
        if (!vector.get(0).equals("a") || !vector.get(1).equals("c")) {
            throw new AssertionError("Remaining elements do not match expected after removal.");
        }
    }

    public static void testRemoveAtNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        try {
            vector.remove(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    public static void testRemoveAtIndexEqualToSize() {
        Vector<String> vector = new Vector<>();
        vector.add("y");
        vector.add("z");
        int index = vector.size();
        try {
            vector.remove(index);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    public static void testRemoveAtIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("m");
        int index = vector.size() + 1;
        try {
            vector.remove(index);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index greater than size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
