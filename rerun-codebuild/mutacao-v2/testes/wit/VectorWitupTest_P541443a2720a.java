/*
 * @test
 * @summary Test java.util.Vector.get(int) for valid and invalid indices
 * @run main VectorWitupTest_P541443a2720a
 */

import java.util.Vector;

public class VectorWitupTest_P541443a2720a {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("zero");
        vector.add("one");
        vector.add("two");

        // Test valid indices
        if (!"zero".equals(vector.get(0))) {
            throw new AssertionError("Expected element at index 0 to be 'zero'");
        }
        if (!"one".equals(vector.get(1))) {
            throw new AssertionError("Expected element at index 1 to be 'one'");
        }
        if (!"two".equals(vector.get(2))) {
            throw new AssertionError("Expected element at index 2 to be 'two'");
        }

        // Test invalid index: index == elementCount (3) should throw ArrayIndexOutOfBoundsException
        try {
            vector.get(3);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 3");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }

        // Test invalid index: negative index should throw ArrayIndexOutOfBoundsException
        try {
            vector.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
