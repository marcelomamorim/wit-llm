/*
 * @test
 * @summary Test for Arrays.copyOfRange with valid and invalid inputs
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRangeValid();
        testCopyOfRangeNegativeLength();
    }

    private static void testCopyOfRangeValid() {
        char[] original = {'a', 'b', 'c', 'd', 'e'};
        char[] result = Arrays.copyOfRange(original, 1, 4);
        if (result.length != 3) {
            throw new AssertionError("Expected length 3, got " + result.length);
        }
        if (result[0] != 'b' || result[1] != 'c' || result[2] != 'd') {
            throw new AssertionError("Unexpected array contents: " + java.util.Arrays.toString(result));
        }
    }

    private static void testCopyOfRangeNegativeLength() {
        char[] original = {'a', 'b', 'c'};
        try {
            Arrays.copyOfRange(original, 2, 0);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
