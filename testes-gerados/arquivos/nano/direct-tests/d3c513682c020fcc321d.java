/* @test    @summary Test Vector.remove(int index) method for correct element removal and exception handling    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveInvalidIndex();
    }

    private static void testRemoveValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        String removed = vector.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected removed element to be 'b', but was: " + removed);
        }
        if (vector.size() != 2) {
            throw new AssertionError("Expected vector size to be 2 after removal, but was: " + vector.size());
        }
        if (!vector.get(0).equals("a") || !vector.get(1).equals("c")) {
            throw new AssertionError("Remaining elements are incorrect: " + vector);
        }
    }

    private static void testRemoveInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        try {
            vector.remove(5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 5");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
        try {
            vector.remove(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
