/*
 * @test
 * @summary Tests Arrays.copyOfRange(byte[], int, int) observable behavior and its IllegalArgumentException path
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNegativeLengthThrowsIllegalArgumentException();
        testNormalRangeCopy();
        testEmptyRangeReturnsEmptyArray();
        testPaddingWhenToExceedsLength();
        testReturnsNewArrayInstance();
        testNullOriginalThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeLengthThrowsIllegalArgumentException() {
        try {
            Arrays.copyOfRange(new byte[4], 3, 1);
            throw new RuntimeException("expected IllegalArgumentException for to < from");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNormalRangeCopy() {
        byte[] original = {1, 2, 3, 4};
        byte[] result = Arrays.copyOfRange(original, 1, 3);
        check(Arrays.equals(result, new byte[] {2, 3}),
              "unexpected copy for range [1,3): " + Arrays.toString(result));
    }

    static void testEmptyRangeReturnsEmptyArray() {
        byte[] result = Arrays.copyOfRange(new byte[] {1, 2, 3, 4}, 2, 2);
        check(result.length == 0, "expected empty array for from == to");
    }

    static void testPaddingWhenToExceedsLength() {
        byte[] original = {1, 2, 3};
        byte[] result = Arrays.copyOfRange(original, 1, 5);
        check(Arrays.equals(result, new byte[] {2, 3, 0, 0}),
              "unexpected padded copy: " + Arrays.toString(result));
    }

    static void testReturnsNewArrayInstance() {
        byte[] original = {5, 6, 7};
        byte[] result = Arrays.copyOfRange(original, 0, 3);
        check(result != original, "copyOfRange must return a new array");
        check(Arrays.equals(result, original), "copy content differs from original");
        result[0] = 9;
        check(original[0] == 5, "modifying copy must not change original");
    }

    static void testNullOriginalThrowsNullPointerException() {
        try {
            Arrays.copyOfRange(null, 0, 1);
            throw new RuntimeException("expected NullPointerException for null original");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
