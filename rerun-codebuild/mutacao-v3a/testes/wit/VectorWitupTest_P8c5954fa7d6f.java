/* @test    @summary Test for Vector.elementAt(int) method
 * @run main VectorWitupTest_P8c5954fa7d6f
 */

import java.util.Vector;

public class VectorWitupTest_P8c5954fa7d6f {

    public static void main(String[] args) {
        testElementAtValidIndex();
        testElementAtIndexEqualsSize();
        testElementAtNegativeIndex();
        testElementAtIndexGreaterThanSize();
    }

    private static void testElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        String result = vector.elementAt(1);
        if (!"b".equals(result)) {
            throw new AssertionError("Expected 'b', got: " + result);
        }
    }

    private static void testElementAtIndexEqualsSize() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        vector.add("y");
        boolean thrown = false;
        try {
            vector.elementAt(vector.size());
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index == size()");
        }
    }

    private static void testElementAtNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("foo");
        boolean thrown = false;
        try {
            vector.elementAt(-1);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        }
    }

    private static void testElementAtIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("bar");
        boolean thrown = false;
        try {
            vector.elementAt(2);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index > size()");
        }
    }
}
