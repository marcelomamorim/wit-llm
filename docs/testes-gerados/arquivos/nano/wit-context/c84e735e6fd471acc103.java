/* @test    @summary Test for Vector.elementAt(int) method, focusing on exception path.    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testElementAtWithValidIndex();
        testElementAtWithInvalidIndex();
    }

    private static void testElementAtWithValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            String element = vector.elementAt(1);
            if (!"b".equals(element)) {
                throw new AssertionError("Expected 'b', got: " + element);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid index", e);
        }
    }

    private static void testElementAtWithInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.elementAt(1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
