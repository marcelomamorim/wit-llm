/* @test    @summary Test for Vector.elementAt(int index) method behavior.    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testElementAtValidIndex();
        testElementAtIndexEqualsSize();
        testElementAtNegativeIndex();
    }

    static void testElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        String result = vector.elementAt(1);
        if (!"b".equals(result)) {
            throw new AssertionError("Expected 'b', but got: " + result);
        }
    }

    static void testElementAtIndexEqualsSize() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        vector.add("y");
        try {
            vector.elementAt(2); // index == size, should throw
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index equal to size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testElementAtNegativeIndex() {
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
