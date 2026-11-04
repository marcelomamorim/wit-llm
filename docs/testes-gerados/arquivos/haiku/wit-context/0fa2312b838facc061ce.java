/*
 * @test
 * @summary Witup-derived behavioral tests for Arrays.copyOfRange(long[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFromGreaterThanToThrowsIAE();
        testFromEqualsToReturnsEmptyArray();
        testNormalRangeCopy();
        testToBeyondLengthPadsWithZeros();
        testNegativeFromThrowsAIOOBE();
        testFromBeyondLengthThrowsAIOOBE();
        testNullArrayThrowsNPE();
    }

    static void testFromGreaterThanToThrowsIAE() {
        try {
            Arrays.copyOfRange(new long[10], 5, 2);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException when from > to");
    }

    static void testFromEqualsToReturnsEmptyArray() {
        long[] r = Arrays.copyOfRange(new long[] {1L, 2L, 3L}, 1, 1);
        check(r.length == 0, "expected empty array when from == to");
    }

    static void testNormalRangeCopy() {
        long[] r = Arrays.copyOfRange(new long[] {1L, 2L, 3L, 4L, 5L}, 1, 4);
        check(Arrays.equals(r, new long[] {2L, 3L, 4L}), "unexpected normal range copy result");
    }

    static void testToBeyondLengthPadsWithZeros() {
        long[] r = Arrays.copyOfRange(new long[] {1L, 2L, 3L}, 1, 6);
        check(Arrays.equals(r, new long[] {2L, 3L, 0L, 0L, 0L}), "expected zero padding when to > length");
    }

    static void testNegativeFromThrowsAIOOBE() {
        try {
            Arrays.copyOfRange(new long[] {1L, 2L, 3L}, -1, 2);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for negative from");
    }

    static void testFromBeyondLengthThrowsAIOOBE() {
        try {
            Arrays.copyOfRange(new long[] {1L, 2L, 3L}, 5, 6);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for from > length");
    }

    static void testNullArrayThrowsNPE() {
        try {
            Arrays.copyOfRange((long[]) null, 0, 1);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null array");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
