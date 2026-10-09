/* @test    @summary Test for Vector.elementAt(int) method, focusing on exception path.    @run main VectorWitupTest_P8dfb2722c524 */

import java.util.Vector;

public class VectorWitupTest_P8dfb2722c524 {

    public static void main(String[] args) {
        testElementAtOutOfBounds();
        testElementAtValidIndex();
    }

    private static void testElementAtOutOfBounds() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        try {
            vector.elementAt(2); // index equal to size, should throw ArrayIndexOutOfBoundsException
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testElementAtValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        String element = vector.elementAt(1);
        if (!"b".equals(element)) {
            throw new AssertionError("Expected 'b', got: " + element);
        }
    }
}
