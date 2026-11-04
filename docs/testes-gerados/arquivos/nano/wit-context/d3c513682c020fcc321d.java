/*
 * @test
 * @summary Tests for java.util.Vector.remove(int)
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveIndexOutOfBounds();
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
            throw new AssertionError("Expected vector size 2, but was: " + vector.size());
        }
        if (!vector.elementAt(0).equals("a") || !vector.elementAt(1).equals("c")) {
            throw new AssertionError("Vector elements mismatch after removal");
        }
    }

    private static void testRemoveIndexOutOfBounds() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        try {
            vector.remove(5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 5");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        try {
            vector.remove(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
