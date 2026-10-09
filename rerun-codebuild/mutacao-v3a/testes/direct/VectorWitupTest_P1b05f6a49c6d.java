/* @test    @summary Test lastIndexOf with various inputs    @run main VectorWitupTest_P1b05f6a49c6d */

import java.util.Vector;

public class VectorWitupTest_P1b05f6a49c6d {

    public static void main(String[] args) {
        testLastIndexOfNullAndNonNull();
        testLastIndexOfEmptyVector();
        testLastIndexOfWithValidObject();
        testLastIndexOfObjectNotPresent();
        testLastIndexOfIndexOutOfBounds();
    }

    static void testLastIndexOfNullAndNonNull() {
        Vector<String> v = new Vector<>();
        v.add(null);
        v.add("a");
        v.add(null);
        v.add("b");
        v.add(null);
        int indexNull = v.lastIndexOf(null, v.size() - 1);
        int indexA = v.lastIndexOf("a", v.size() - 1);
        int indexB = v.lastIndexOf("b", v.size() - 1);
        assert indexNull == 4 : "Expected 4, got " + indexNull;
        assert indexA == 1 : "Expected 1, got " + indexA;
        assert indexB == 3 : "Expected 3, got " + indexB;
    }

    static void testLastIndexOfEmptyVector() {
        Vector<Integer> v = new Vector<>();
        int index = v.lastIndexOf(1, 0);
        assert index == -1 : "Expected -1, got " + index;
    }

    static void testLastIndexOfWithValidObject() {
        Vector<String> v = new Vector<>();
        v.add("x");
        v.add("y");
        v.add("z");
        v.add("x");
        int index = v.lastIndexOf("x", v.size() - 1);
        assert index == 3 : "Expected 3, got " + index;
    }

    static void testLastIndexOfObjectNotPresent() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        int index = v.lastIndexOf("d", v.size() - 1);
        assert index == -1 : "Expected -1, got " + index;
    }

    static void testLastIndexOfIndexOutOfBounds() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.lastIndexOf("a", v.size());
            assert false : "Expected IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            v.lastIndexOf("a", -1);
            // Should not throw, but will return -1 since index < 0
            int result = v.lastIndexOf("a", -1);
            assert result == -1 : "Expected -1, got " + result;
        } catch (IndexOutOfBoundsException e) {
            assert false : "Did not expect exception";
        }
    }
}
