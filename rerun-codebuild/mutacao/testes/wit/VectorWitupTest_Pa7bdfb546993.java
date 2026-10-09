/*
 * @test
 * @summary Test for Vector.setElementAt method, focusing on exception path.
 * @run main VectorWitupTest_Pa7bdfb546993
 */

import java.util.Vector;

public class VectorWitupTest_Pa7bdfb546993 {

    public static void main(String[] args) {
        testSetElementAtOutOfBounds();
        testSetElementAtValid();
    }

    private static void testSetElementAtOutOfBounds() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        try {
            vector.setElementAt("b", 1); // index 1, elementCount=1, should throw
            System.err.println("Failed: Expected ArrayIndexOutOfBoundsException not thrown");
            System.exit(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testSetElementAtValid() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.setElementAt("b", 0); // valid index, should succeed
        if (!"b".equals(vector.elementAt(0))) {
            System.err.println("Failed: Element not set correctly");
            System.exit(1);
        }
    }
}
