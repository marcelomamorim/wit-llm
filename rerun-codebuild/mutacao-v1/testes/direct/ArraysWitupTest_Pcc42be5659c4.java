/* @test    @summary Test for Arrays.copyOfRange with valid and invalid inputs    @run main ArraysWitupTest_Pcc42be5659c4 */

import java.util.Arrays;

public class ArraysWitupTest_Pcc42be5659c4 {

    public static void main(String[] args) {
        testCopyOfRangeValid();
        testCopyOfRangeNegativeLength();
        testCopyOfRangeFromGreaterThanTo();
        testCopyOfRangeFromNegative();
        testCopyOfRangeToExceedsLength();
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

    static void testCopyOfRangeNegativeLength() {
        short[] original = {1, 2, 3};
        try {
            Arrays.copyOfRange(original, 2, 1);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testCopyOfRangeFromGreaterThanTo() {
        short[] original = {1, 2, 3};
        try {
            Arrays.copyOfRange(original, 3, 2);
            throw new AssertionError("Expected IllegalArgumentException for from > to");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testCopyOfRangeFromNegative() {
        short[] original = {1, 2, 3};
        try {
            Arrays.copyOfRange(original, -1, 2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative from");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCopyOfRangeToExceedsLength() {
        short[] original = {1, 2, 3};
        short[] result = Arrays.copyOfRange(original, 1, 10);
        if (result.length != 9) {
            throw new AssertionError("Expected length 9, got " + result.length);
        }
        if (result[0] != 2 || result[8] != 0) {
            throw new AssertionError("Unexpected array contents: " + Arrays.toString(result));
        }
    }
}
