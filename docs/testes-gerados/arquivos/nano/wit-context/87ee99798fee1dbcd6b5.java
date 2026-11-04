/*
 * @test
 * @summary Test for Arrays.copyOfRange to verify behavior with invalid 'to' index.
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRangeWithNegativeNewLength();
        testCopyOfRangeWithZeroNewLength();
        testCopyOfRangeWithValidIndices();
    }

    private static void testCopyOfRangeWithNegativeNewLength() {
        Integer[] original = {1, 2, 3, 4, 5};
        Class<Integer[]> type = Integer[].class;
        try {
            Arrays.copyOfRange(original, 1, 0, type);
            throw new AssertionError("Expected IllegalArgumentException for to < from");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testCopyOfRangeWithZeroNewLength() {
        Integer[] original = {1, 2, 3, 4, 5};
        Class<Integer[]> type = Integer[].class;
        Integer[] result = Arrays.copyOfRange(original, 2, 2, type);
        if (result.length != 0) {
            throw new AssertionError("Expected empty array for from == to");
        }
    }

    private static void testCopyOfRangeWithValidIndices() {
        Integer[] original = {1, 2, 3, 4, 5};
        Class<Integer[]> type = Integer[].class;
        Integer[] result = Arrays.copyOfRange(original, 1, 4, type);
        if (result.length != 3 || result[0] != 2 || result[1] != 3 || result[2] != 4) {
            throw new AssertionError("Unexpected array content: " + java.util.Arrays.toString(result));
        }
    }
}
