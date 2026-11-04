/*
 * @test
 * @summary WITUP-derived tests for Arrays.copyOfRange(float[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testRangeWithinBounds();
        testEmptyRange();
        testRangeExtendingBeyondOriginal();
        testFromGreaterThanToThrows();
        testNegativeLengthThrowsWhenFromEqualsToPlusOne();
    }

    private static void testRangeWithinBounds() {
        float[] original = {1.0f, 2.0f, 3.0f, 4.0f};
        float[] result = Arrays.copyOfRange(original, 1, 3);
        float[] expected = {2.0f, 3.0f};
        check(Arrays.equals(expected, result), "range within bounds");
    }

    private static void testEmptyRange() {
        float[] original = {1.0f, 2.0f, 3.0f};
        float[] result = Arrays.copyOfRange(original, 2, 2);
        check(result.length == 0, "empty range length");
    }

    private static void testRangeExtendingBeyondOriginal() {
        float[] original = {1.0f, 2.0f, 3.0f};
        float[] result = Arrays.copyOfRange(original, 2, 6);
        float[] expected = {3.0f, 0.0f, 0.0f, 0.0f};
        check(Arrays.equals(expected, result), "range extending beyond original is zero padded");
    }

    private static void testFromGreaterThanToThrows() {
        float[] original = {1.0f, 2.0f, 3.0f, 4.0f};
        boolean thrown = false;
        try {
            Arrays.copyOfRange(original, 3, 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "from > to must throw IllegalArgumentException");
    }

    private static void testNegativeLengthThrowsWhenFromEqualsToPlusOne() {
        float[] original = {1.0f, 2.0f, 3.0f};
        boolean thrown = false;
        try {
            Arrays.copyOfRange(original, 2, 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "newLength -1 must throw IllegalArgumentException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
