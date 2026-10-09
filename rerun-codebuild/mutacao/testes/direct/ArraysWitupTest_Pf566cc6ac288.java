/* @test    @summary Test for Arrays.copyOfRange with valid and invalid inputs    @run main ArraysWitupTest_Pf566cc6ac288 */

import java.util.Arrays;

public class ArraysWitupTest_Pf566cc6ac288 {

    public static void main(String[] args) {
        testCopyOfRangeValid();
        testCopyOfRangeInvalid();
    }

    static void testCopyOfRangeValid() {
        short[] original = {1, 2, 3, 4, 5};
        short[] result = Arrays.copyOfRange(original, 1, 4);
        if (result.length != 3) {
            throw new AssertionError("Expected length 3, got " + result.length);
        }
        if (result[0] != 2 || result[1] != 3 || result[2] != 4) {
            throw new AssertionError("Unexpected array contents: " + Arrays.toString(result));
        }
    }

    static void testCopyOfRangeInvalid() {
        short[] original = {1, 2, 3};
        boolean thrown = false;
        try {
            Arrays.copyOfRange(original, 2, 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for from > to");
        }
    }
}
