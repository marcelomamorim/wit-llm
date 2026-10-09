/* @test    @summary Test Vector.elementAt for valid and invalid indices    @run main VectorWitupTest_P8c5954fa7d6f */

import java.util.Vector;

public class VectorWitupTest_P8c5954fa7d6f {

    public static void main(String[] args) {
        testElementAtValidIndex();
        testElementAtInvalidIndex();
    }

    static void testElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        String result = vector.elementAt(1);
        if (!"b".equals(result)) {
            throw new AssertionError("Expected 'b' at index 1, but got: " + result);
        }
    }

    static void testElementAtInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        try {
            vector.elementAt(3);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 3");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
