/* @test    @summary Test for Vector.elementAt(int index) method behavior and exception handling    @run main VectorWitupTest_P8dfb2722c524 */

import java.util.Vector;

public class VectorWitupTest_P8dfb2722c524 {
    public static void main(String[] args) {
        testElementAtValidIndex();
        testElementAtIndexEqualToSize();
        testElementAtNegativeIndex();
    }

    private static void testElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        String element = vector.elementAt(1);
        if (!"b".equals(element)) {
            throw new AssertionError("Expected 'b' at index 1, but got: " + element);
        }
    }

    private static void testElementAtIndexEqualToSize() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        vector.add("y");
        vector.add("z");
        try {
            vector.elementAt(3); // index == size, should throw
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testElementAtNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("foo");
        try {
            vector.elementAt(-1); // negative index, should throw
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
