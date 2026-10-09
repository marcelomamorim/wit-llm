/* @test    @summary Test for Arrays.copyOfRange(short[], int, int) with valid inputs and exception case    @run main ArraysWitupTest_Pcc42be5659c4 */

import java.util.Arrays;

public class ArraysWitupTest_Pcc42be5659c4 {

    public static void main(String[] args) {
        testCopyOfRangeValid();
        testCopyOfRangeInvalid();
    }

    static void testCopyOfRangeValid() {
        short[] original = {1, 2, 3, 4, 5};
        short[] result = Arrays.copyOfRange(original, 1, 4);
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }
        if (result.length != 3) {
            throw new AssertionError("Expected length 3, got " + result.length);
        }
        if (result[0] != 2 || result[1] != 3 || result[2] != 4) {
            throw new AssertionError("Unexpected array contents: " + java.util.Arrays.toString(result));
        }
    }

    static void testCopyOfRangeInvalid() {
        short[] original = {1, 2, 3};
        try {
            // This should throw IllegalArgumentException because to - from < 0
            Arrays.copyOfRange(original, 3, 1);
            throw new AssertionError("Expected IllegalArgumentException for to < from");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        try {
            // This should throw IllegalArgumentException because to - from < 0
            Arrays.copyOfRange(original, 2, 1);
            throw new AssertionError("Expected IllegalArgumentException for to < from");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
