/*
 * @test
 * @summary Test for Arrays.copyOfRange with valid and invalid inputs
 * @run main ArraysWitupTest_Pee74fe61ea79
 */

import java.util.Arrays;

public class ArraysWitupTest_Pee74fe61ea79 {

    public static void main(String[] args) {
        testCopyOfRangeValid();
        testCopyOfRangeNegativeLength();
    }

    private static void testCopyOfRangeValid() {
        short[] original = {1, 2, 3, 4, 5};
        short[] result = Arrays.copyOfRange(original, 1, 4);
        if (result.length != 3) {
            throw new AssertionError("Expected length 3, but got " + result.length);
        }
        if (result[0] != 2 || result[1] != 3 || result[2] != 4) {
            throw new AssertionError("Array contents mismatch: " + java.util.Arrays.toString(result));
        }
    }

    private static void testCopyOfRangeNegativeLength() {
        short[] original = {1, 2, 3, 4, 5};
        boolean thrown = false;
        try {
            Arrays.copyOfRange(original, 4, 2);
        } catch (IllegalArgumentException e) {
            thrown = true;
            if (!e.getMessage().contains("" + 4 + "  " + 2)) {
                throw new AssertionError("Exception message does not contain expected info: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        }
    }
}
