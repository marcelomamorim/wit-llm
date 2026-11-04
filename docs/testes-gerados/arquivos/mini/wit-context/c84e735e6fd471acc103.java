/*
 * @test
 * @summary Test Vector.elementAt(int) for valid and invalid indices
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testElementAtValidIndex();
        testElementAtInvalidIndex();
    }

    private static void testElementAtValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("zero");
        v.add("one");
        v.add("two");

        // Test valid indices
        if (!"zero".equals(v.elementAt(0))) {
            throw new AssertionError("Expected element at index 0 to be 'zero'");
        }
        if (!"one".equals(v.elementAt(1))) {
            throw new AssertionError("Expected element at index 1 to be 'one'");
        }
        if (!"two".equals(v.elementAt(2))) {
            throw new AssertionError("Expected element at index 2 to be 'two'");
        }
    }

    private static void testElementAtInvalidIndex() {
        Vector<String> v = new Vector<>();
        v.add("only");

        // Test index equal to size (should throw ArrayIndexOutOfBoundsException)
        try {
            v.elementAt(1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }

        // Test negative index (should throw ArrayIndexOutOfBoundsException)
        try {
            v.elementAt(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
    }
}
