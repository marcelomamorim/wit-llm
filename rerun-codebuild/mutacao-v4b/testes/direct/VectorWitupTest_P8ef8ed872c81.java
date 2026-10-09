/* @test
   @summary Tests the lastIndexOf method of Vector for various scenarios.
   @run main VectorWitupTest_P8ef8ed872c81
 */
import java.util.Vector;
public class VectorWitupTest_P8ef8ed872c81 {
    public static void main(String[] args) {
        testLastIndexOfNullObject();
        testLastIndexOfExistingObject();
        testLastIndexOfNonExistingObject();
        testLastIndexOfWithNegativeIndex();
        testLastIndexOfWithIndexGreaterThanSize();
    }

    static void testLastIndexOfNullObject() {
        Vector<String> vector = new Vector<>();
        vector.add(null);
        vector.add("a");
        vector.add(null);
        int index = vector.lastIndexOf(null, vector.size() - 1);
        if (index != 2) {
            throw new AssertionError("Expected index 2 for null, got " + index);
        }
    }

    static void testLastIndexOfExistingObject() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        vector.add("y");
        vector.add("x");
        int index = vector.lastIndexOf("x", vector.size() - 1);
        if (index != 2) {
            throw new AssertionError("Expected index 2 for 'x', got " + index);
        }
    }

    static void testLastIndexOfNonExistingObject() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        int index = vector.lastIndexOf("c", vector.size() - 1);
        if (index != -1) {
            throw new AssertionError("Expected -1 for non-existing object, got " + index);
        }
    }

    static void testLastIndexOfWithNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.lastIndexOf("a", -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testLastIndexOfWithIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.lastIndexOf("a", 10);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
