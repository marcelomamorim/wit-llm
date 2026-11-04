/*
 * @test
 * @summary Witup-generated tests for Arrays.copyOfRange(int[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testRegularRange();
        testEmptyRange();
        testRangeEndingAtLength();
        testReturnsNewArray();
        testReversedRangeThrows();
    }

    private static void testRegularRange() {
        int[] result = Arrays.copyOfRange(new int[]{1, 2, 3, 4}, 1, 3);
        check(Arrays.equals(result, new int[]{2, 3}));
    }

    private static void testEmptyRange() {
        int[] result = Arrays.copyOfRange(new int[]{1, 2, 3}, 1, 1);
        check(result.length == 0);
    }

    private static void testRangeEndingAtLength() {
        int[] result = Arrays.copyOfRange(new int[]{1, 2}, 2, 4);
        check(Arrays.equals(result, new int[]{0, 0}));
    }

    private static void testReturnsNewArray() {
        int[] original = new int[]{5, 6, 7};
        int[] result = Arrays.copyOfRange(original, 0, original.length);
        check(result != original);
        check(Arrays.equals(result, original));
    }

    private static void testReversedRangeThrows() {
        try {
            Arrays.copyOfRange(new int[]{1, 2, 3}, 2, 1);
            throw new RuntimeException("IllegalArgumentException expected");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Check failed");
        }
    }
}
