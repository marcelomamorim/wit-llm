/*
 * @test
 * @summary Test for Arrays.copyOfRange(int[], int, int) handling of invalid 'to' parameter
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRangeWithNegativeNewLength();
        testCopyOfRangeWithFromGreaterThanTo();
    }

    private static void testCopyOfRangeWithNegativeNewLength() {
        int[] original = {1, 2, 3, 4, 5};
        int from = 1;
        int to = 0; // to - from = -1, should throw IllegalArgumentException
        try {
            Arrays.copyOfRange(original, from, to);
            System.err.println("Expected IllegalArgumentException for to < from");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testCopyOfRangeWithFromGreaterThanTo() {
        int[] original = {1, 2, 3, 4, 5};
        int from = 3;
        int to = 2; // to - from = -1, should throw IllegalArgumentException
        try {
            Arrays.copyOfRange(original, from, to);
            System.err.println("Expected IllegalArgumentException for from > to");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
