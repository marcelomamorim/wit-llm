/* @test    @summary Test the get(int index) method of java.util.Vector for valid and invalid indices    @run main VectorWitupTest_Pecbc2c666239 */

import java.util.Vector;

public class VectorWitupTest_Pecbc2c666239 {
    public static void main(String[] args) {
        testGetValidIndex();
        testGetInvalidIndex();
    }

    private static void testGetValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        String result = vector.get(1);
        if (!"b".equals(result)) {
            throw new AssertionError("Expected 'b' at index 1, but got: " + result);
        }
    }

    private static void testGetInvalidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        try {
            vector.get(5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 5");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        try {
            vector.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
